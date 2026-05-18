package io.github.aquerr.rentacar.workflow.rentacar.application.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestErrorResponse {
    private String code;
    private String message;
}
