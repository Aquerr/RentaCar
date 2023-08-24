package io.github.aquerr.rentacar.application.security.mfa;

import dev.samstevens.totp.code.CodeVerifier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MfaCodeVerifier
{
    private final CodeVerifier codeVerifier;

    public boolean verify(String secret, String code)
    {
        return this.codeVerifier.isValidCode(secret, code);
    }
}
