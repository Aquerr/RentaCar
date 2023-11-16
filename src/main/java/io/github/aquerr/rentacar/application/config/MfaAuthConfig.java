package io.github.aquerr.rentacar.application.config;

import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.recovery.RecoveryCodeGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.NtpTimeProvider;
import io.github.aquerr.rentacar.application.security.mfa.MfaCodeGenerator;
import io.github.aquerr.rentacar.application.security.mfa.MfaCodeVerifier;
import io.github.aquerr.rentacar.application.security.mfa.MfaRecoveryCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration(proxyBeanMethods = false)
public class MfaAuthConfig
{
    @Value("${rentacar.security.mfa.totp.length}")
    private int totpLength;

    @Value("${rentacar.security.mfa.totp.recovery-codes.count}")
    private int recoveryCodesCount;

    @Value("${rentacar.security.issuer}")
    private String issuer;

    @Bean
    public MfaCodeVerifier mfaCodeVerifier() throws UnknownHostException
    {
        return new MfaCodeVerifier(new DefaultCodeVerifier(new DefaultCodeGenerator(HashingAlgorithm.SHA1, totpLength), new NtpTimeProvider("pl.pool.ntp.org")));
    }

    @Bean
    public MfaRecoveryCodeGenerator mfaRecoveryCodeGenerator()
    {
        return new MfaRecoveryCodeGenerator(new RecoveryCodeGenerator(), recoveryCodesCount);
    }

    @Bean
    public MfaCodeGenerator mfaCodeGenerator(MfaRecoveryCodeGenerator mfaRecoveryCodeGenerator)
    {
        return new MfaCodeGenerator(
                issuer,
                new DefaultSecretGenerator(totpLength),
                new ZxingPngQrGenerator(),
                mfaRecoveryCodeGenerator);
    }
}
