package io.github.aquerr.rentacar.application.exception;

public class CouldNotSendMailException extends RuntimeException
{
    public CouldNotSendMailException(Throwable cause)
    {
        super(cause);
    }
}
