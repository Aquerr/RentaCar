package io.github.aquerr.rentacar.web.rest.response;

import java.util.List;

public record JwtTokenResponse(String jwt, String username, List<String> authorities) {}