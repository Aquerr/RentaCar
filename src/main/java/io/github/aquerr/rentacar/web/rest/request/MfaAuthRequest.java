package io.github.aquerr.rentacar.web.rest.request;

import lombok.Value;

@Value
public class MfaAuthRequest
{
    String challenge;
    String code;
}
