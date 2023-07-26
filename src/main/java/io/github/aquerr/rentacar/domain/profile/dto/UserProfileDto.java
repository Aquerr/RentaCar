package io.github.aquerr.rentacar.domain.profile.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
public class UserProfileDto
{
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private String city;
    private String zipCode;
    private String street;
    private Set<String> authorities;
}
