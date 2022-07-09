package com.zalmanhack.tireshop.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {

    private String type = "Bearer";
    private String token;
    private String refreshToken;
    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private List<String> roles;

    //TODO Сделать с получением UserDetailsImpl и разгрузить вызывающий метод
    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, boolean enabled, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
    }
}
