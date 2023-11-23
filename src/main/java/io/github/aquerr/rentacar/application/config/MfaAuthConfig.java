package io.github.aquerr.rentacar.application.config;

import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.NtpTimeProvider;
import io.github.aquerr.rentacar.application.security.mfa.MfaCodeGenerator;
import io.github.aquerr.rentacar.application.security.mfa.MfaCodeVerifier;
import io.github.aquerr.rentacar.application.security.mfa.MfaRecoveryCodeGenerator;
import io.github.aquerr.rentacar.application.security.mfa.RecoveryCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration(proxyBeanMethods = false)
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
        return new MfaCodeVerifier(new DefaultCodeVerifier(new DefaultCodeGenerator(HashingAlgorithm.SHA1, codeLength), new NtpTimeProvider("pl.pool.ntp.org")));
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
