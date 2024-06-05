package io.github.aquerr.rentacar.application.config;

import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.NtpTimeProvider;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import io.github.aquerr.rentacar.application.security.mfa.MfaCodeGenerator;
import io.github.aquerr.rentacar.application.security.mfa.MfaCodeVerifier;
import io.github.aquerr.rentacar.application.security.mfa.MfaRecoveryCodeGenerator;
import io.github.aquerr.rentacar.application.security.mfa.RecoveryCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class MfaAuthConfig
{
    @Value("${rentacar.security.mfa.totp.secret.length}")
    private int secretLength;
    @Value("${rentacar.security.mfa.totp.code.length}")
    private int codeLength;
    @Value("${rentacar.security.mfa.totp.code.time}")
    private int codeTimeSeconds;

    @Value("${rentacar.security.mfa.totp.recovery-codes.length}")
    private int recoveryCodeLength;
    @Value("${rentacar.security.mfa.totp.recovery-codes.groups}")
    private int recoveryCodeGroupsCount;
    @Value("${rentacar.security.mfa.totp.recovery-codes.count}")
    private int recoveryCodesCount;

    @Value("${rentacar.security.issuer}")
    private String issuer;

    @Bean
    public MfaCodeVerifier mfaCodeVerifier() throws UnknownHostException
    {
        TimeProvider timeProvider = null;
        try
        {
            timeProvider = new NtpTimeProvider("pl.pool.ntp.org");
        }
        catch (Exception exception)
        {
            log.warn("Using default system time provider for MFA.");
            timeProvider = new SystemTimeProvider();
        }

        return new MfaCodeVerifier(new DefaultCodeVerifier(new DefaultCodeGenerator(HashingAlgorithm.SHA1, codeLength), timeProvider));
    }

    @Bean
    public MfaRecoveryCodeGenerator mfaRecoveryCodeGenerator()
    {
        return new MfaRecoveryCodeGenerator(new RecoveryCodeGenerator(recoveryCodeLength, recoveryCodeGroupsCount), recoveryCodesCount);
    }

    @Bean
    public MfaCodeGenerator mfaCodeGenerator(MfaRecoveryCodeGenerator mfaRecoveryCodeGenerator)
    {
        return new MfaCodeGenerator(
                issuer,
                new DefaultSecretGenerator(secretLength),
                new ZxingPngQrGenerator(),
                mfaRecoveryCodeGenerator,
                codeLength,
                codeTimeSeconds);
    }
}
