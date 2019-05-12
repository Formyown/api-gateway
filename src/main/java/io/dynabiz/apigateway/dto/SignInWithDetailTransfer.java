package io.dynabiz.apigateway.dto;

import lombok.Data;

@Data
public class SignInWithDetailTransfer {
    String email;
    String password;
    String appType;
    String appVersion;
    String ip;
    String agent;
}
