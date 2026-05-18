package io.github.aquerr.rentacar.application.security.mfa;

import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.util.Utils;
import io.github.aquerr.rentacar.application.security.exception.CouldNotGenerateQrCodeException;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class MfaCodeGenerator
{
    private final String issuer;
    private final SecretGenerator secretGenerator;
    private final QrGenerator qrGenerator;
    private final MfaRecoveryCodeGenerator recoveryCodeGenerator;
    private final int codeLength;
    private final int codeTimeSeconds;

    public Set<String> generateRecoveryCodes()
    {
        return recoveryCodeGenerator.generateCodes();
    }

    public String generateSecret()
    {
        return this.secretGenerator.generate();
    }

    public String generateQrCodeDataUri(String secret, String userIdentifier)
    {
        final QrData qrDat = new QrData.Builder()
                .label(userIdentifier)
                .secret(secret)
                .issuer(issuer)
                .algorithm(HashingAlgorithm.SHA1)
                .digits(codeLength)
                .period(codeTimeSeconds)
                .build();
        try
        {
            return Utils.getDataUriForImage(qrGenerator.generate(qrDat), qrGenerator.getImageMimeType());
        }
        catch (QrGenerationException e)
        {
            throw new CouldNotGenerateQrCodeException(e);
        }
    }

    public String generateChallenge()
    {
        return generateSecret();
    }
}
