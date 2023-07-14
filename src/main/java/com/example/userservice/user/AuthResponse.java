package com.example.userservice.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String accessToken;

    private Long creationTime;

    private Long validity;

    private String tokenType;

}
