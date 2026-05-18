package io.github.aquerr.rentacar.application.security.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.INTERNAL_SERVER_ERROR, code = ApiExceptionCode.COULD_NOT_GENERATE_QR_CODE, messageKey = "user.error.could-not-generate-qr-code")
public class CouldNotGenerateQrCodeException extends RuntimeException
{
    public CouldNotGenerateQrCodeException(Throwable cause)
    {
        super(cause);
    }
}
