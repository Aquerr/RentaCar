package io.github.aquerr.rentacar.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCredentials
{
    private Long id;
    private String username;
    private String email;
    private String password;
}
