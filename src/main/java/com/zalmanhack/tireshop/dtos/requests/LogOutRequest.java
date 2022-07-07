package com.zalmanhack.tireshop.dtos.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LogOutRequest {
    @NotNull
    private Long userId;
}
