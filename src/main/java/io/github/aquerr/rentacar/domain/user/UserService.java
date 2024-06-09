package io.github.aquerr.rentacar.domain.user;

import io.github.aquerr.rentacar.application.lang.RequestLocaleExtractor;
import io.github.aquerr.rentacar.application.security.challengetoken.dto.ChallengeToken;
import io.github.aquerr.rentacar.application.security.exception.AccessDeniedException;
import io.github.aquerr.rentacar.domain.activation.AccountActivationService;
import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenParams;
import io.github.aquerr.rentacar.domain.profile.model.UserProfileEntity;
import io.github.aquerr.rentacar.domain.user.converter.UserCredentialsConverter;
import io.github.aquerr.rentacar.domain.user.dto.UserCredentials;
import io.github.aquerr.rentacar.domain.user.exception.UsernameOrEmailAlreadyUsedException;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.user.dto.UserRegistrationParams;
import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import io.github.aquerr.rentacar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService {

    private final AccountActivationService accountActivationService;
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserRepository userRepository;
    private final UserCredentialsConverter userCredentialsConverter;
    private final PasswordEncoder passwordEncoder;
    private final RequestLocaleExtractor requestLocaleExtractor;

    @Transactional
    public void register(UserRegistrationParams userRegistrationParams)
    {
        UserCredentialsEntity existingUserCreds = this.userCredentialsRepository.findByUsernameOrEmail(userRegistrationParams.getUsername(), userRegistrationParams.getEmail());
        if (existingUserCreds != null)
            throw new UsernameOrEmailAlreadyUsedException();

        UserCredentialsEntity userCredentialsEntity = UserCredentialsEntity.builder()
                .username(userRegistrationParams.getUsername())
                .email(userRegistrationParams.getEmail())
                .password(passwordEncoder.encode(userRegistrationParams.getPassword()))
                .locked(false)
                .activated(false)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .credentials(userCredentialsEntity)
                .profile(UserProfileEntity.builder()
                        .contactEmail(userRegistrationParams.getEmail())
                        .build())
                .build();

        userEntity = userRepository.save(userEntity);

        accountActivationService.requestActivationToken(userEntity.getId(),
                userCredentialsEntity.getEmail(),
                requestLocaleExtractor.getPreferredLangCode()
        );
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserCredentials findByUsername(String username)
    {
        return userCredentialsConverter.convertToDto(userCredentialsRepository.findByUsername(username));
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserCredentials findByEmail(String email)
    {
        return userCredentialsConverter.convertToDto(userCredentialsRepository.findByEmail(email));
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserCredentials findById(Long id)
    {
        return userCredentialsRepository.findById(id)
                .map(this.userCredentialsConverter::convertToDto)
                .orElse(null);
    }

    @Transactional
    public void activateAccount(ActivationTokenParams activationTokenParams)
    {
        String token = activationTokenParams.getToken();
        ChallengeToken challengeToken = this.accountActivationService.getActivationToken(token);
        this.accountActivationService.activate(challengeToken);
    }

    public void resendActivationEmail(io.github.aquerr.rentacar.application.security.UserCredentials.UsernameOrEmail login)
    {
        UserCredentialsEntity userCredentialsEntity = this.userCredentialsRepository.findByUsernameOrEmail(login.getLogin(), login.getLogin());
        if (userCredentialsEntity == null)
        {
            throw new UsernameNotFoundException("User does not exist!");
        }
        else if (userCredentialsEntity.isActivated())
        {
            // Better not to inform the client about activated account.
            throw new AccessDeniedException();
        }

        accountActivationService.requestActivationToken(userCredentialsEntity.getUserId(),
                userCredentialsEntity.getEmail(),
                requestLocaleExtractor.getPreferredLangCode()
        );
    }
}
