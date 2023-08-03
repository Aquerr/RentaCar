package io.github.aquerr.rentacar.domain.user;

import io.github.aquerr.rentacar.domain.activation.AccountActivationService;
import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenDto;
import io.github.aquerr.rentacar.domain.activation.dto.ActivationTokenParams;
import io.github.aquerr.rentacar.domain.profile.ProfileService;
import io.github.aquerr.rentacar.domain.profile.dto.CreateProfileParameters;
import io.github.aquerr.rentacar.domain.user.converter.UserCredentialsConverter;
import io.github.aquerr.rentacar.domain.user.dto.UserCredentials;
import io.github.aquerr.rentacar.domain.user.exception.UsernameOrEmailAlreadyUsedException;
import io.github.aquerr.rentacar.domain.user.model.UserCredentialsEntity;
import io.github.aquerr.rentacar.domain.user.model.UserRegistration;
import io.github.aquerr.rentacar.repository.UserCredentialsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService {

    private final AccountActivationService accountActivationService;
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserCredentialsConverter userCredentialsConverter;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ActivationTokenDto register(UserRegistration userRegistration)
    {
        UserCredentialsEntity userCredentialsEntity = this.userCredentialsRepository.findByUsernameOrEmail(userRegistration.getUsername(), userRegistration.getEmail());
        if (userCredentialsEntity != null)
            throw new UsernameOrEmailAlreadyUsedException();

        UserCredentialsEntity rentaCarUserCredentialsEntity = UserCredentialsEntity.builder()
                .username(userRegistration.getUsername())
                .email(userRegistration.getEmail())
                .password(passwordEncoder.encode(userRegistration.getPassword()))
                .locked(false)
                .verified(false)
                .build();
        rentaCarUserCredentialsEntity = this.userCredentialsRepository.save(rentaCarUserCredentialsEntity);
        return accountActivationService.requestActivationToken(rentaCarUserCredentialsEntity);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserCredentials findByUsername(String username)
    {
        return userCredentialsConverter.convertToDto(userCredentialsRepository.findByUsername(username));
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
        ActivationTokenDto activationTokenDto = this.accountActivationService.getActivationToken(token);
        this.accountActivationService.activateToken(activationTokenDto);

        UserCredentialsEntity credentials = userCredentialsRepository.findById(activationTokenDto.getCredentialsId())
                .orElse(null);
        if (credentials != null)
        {
            credentials.setVerified(true);
            userCredentialsRepository.save(credentials);
            this.profileService.createNewProfile(CreateProfileParameters.builder()
                    .credentialsId(credentials.getId())
                    .email(credentials.getEmail())
                    .build());
        }
    }
}
