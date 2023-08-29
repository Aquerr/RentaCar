package io.github.aquerr.rentacar.web.rest;

import lombok.Value;

@Value(staticConstructor = "of")
public class RestErrorResponse {
    String code;
    String message;
}
