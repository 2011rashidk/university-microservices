package com.university.apigateway.controller;

import com.university.apigateway.response.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/university/auth")
@Slf4j
public class AuthController {

    @GetMapping("login")
    public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient oAuth2AuthorizedClient,
                                              @AuthenticationPrincipal OidcUser oidcUser) {
        log.info(oidcUser.getEmail());
        AuthResponse authResponse = AuthResponse.builder()
                .userId(oidcUser.getEmail())
                .accessToken(oAuth2AuthorizedClient.getAccessToken().getTokenValue())
                .refreshToken(oAuth2AuthorizedClient.getRefreshToken().getTokenValue())
                .expireAt(oAuth2AuthorizedClient.getAccessToken().getExpiresAt().atZone(ZoneOffset.systemDefault()).toLocalDateTime())
                .authorities(oidcUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).build();
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
