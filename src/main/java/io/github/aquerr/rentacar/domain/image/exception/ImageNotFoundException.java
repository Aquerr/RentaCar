package io.github.aquerr.rentacar.domain.image.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.NOT_FOUND, messageKey = "")
public class ImageNotFoundException extends RuntimeException
{

}
