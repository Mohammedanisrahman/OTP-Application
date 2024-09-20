package com.RahmanWare.twilio.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto {
    private String phoneNumber;
    private String username;
    private String oneTimePassword;
}
