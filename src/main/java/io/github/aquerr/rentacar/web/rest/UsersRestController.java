package io.github.aquerr.rentacar.web.rest;

import io.github.aquerr.rentacar.domain.user.UserService;
import io.github.aquerr.rentacar.domain.user.dto.UserRegistration;
import io.github.aquerr.rentacar.web.rest.request.UserRegistrationRequest;
import io.github.aquerr.rentacar.web.rest.response.UserRegistrationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UsersRestController
{
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> register(@RequestBody UserRegistrationRequest userRegistrationRequest)
    {
        userService.register(new UserRegistration(userRegistrationRequest.getUsername(), userRegistrationRequest.getEmail(), userRegistrationRequest.getPassword()));
        return ResponseEntity.ok().build();
    }
}
