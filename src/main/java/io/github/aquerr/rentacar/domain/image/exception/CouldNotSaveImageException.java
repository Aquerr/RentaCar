package io.github.aquerr.rentacar.domain.image.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.INTERNAL_SERVER_ERROR, messageKey = "image.error.could-not-save-image")
public class CouldNotSaveImageException extends RuntimeException
{

}
