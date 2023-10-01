package com.university.apigateway.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AuthResponse {

    private String userId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expireAt;
    private List<String> authorities;
}
