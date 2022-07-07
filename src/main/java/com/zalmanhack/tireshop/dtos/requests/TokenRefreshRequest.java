package com.zalmanhack.tireshop.dtos.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRefreshRequest {

    @NotBlank
    private String refreshToken;
}
