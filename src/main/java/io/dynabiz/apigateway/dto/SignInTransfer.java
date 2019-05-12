package io.dynabiz.apigateway.dto;

import lombok.Data;

@Data
public class SignInTransfer {
    String email;
    String password;
    String appType;
    String appVersion;
}
