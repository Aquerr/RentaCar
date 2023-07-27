package io.github.aquerr.rentacar.domain.profile.exception;

import io.github.aquerr.rentacar.domain.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.INTERNAL_SERVER_ERROR, messageKey = "profile.error.not-found")
public class ProfileNotFoundException extends RuntimeException
{

}
