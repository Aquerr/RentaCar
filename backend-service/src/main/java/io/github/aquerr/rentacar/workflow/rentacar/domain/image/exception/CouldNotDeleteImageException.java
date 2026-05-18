package io.github.aquerr.rentacar.workflow.rentacar.domain.image.exception;

import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.workflow.rentacar.domain.ApiExceptionCode;
import org.springframework.http.HttpStatus;

@ApiException(code = ApiExceptionCode.COULD_NOT_DELETE_IMAGE, status = HttpStatus.INTERNAL_SERVER_ERROR, messageKey = "image.error.could-not-delete-image")
public class CouldNotDeleteImageException extends RuntimeException
{

}
