package com.zalmanhack.tireshop.dtos.responses;

import lombok.Data;

@Data
public class TokenRefreshResponse {

    private String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
