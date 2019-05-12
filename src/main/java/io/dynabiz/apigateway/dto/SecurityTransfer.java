package io.dynabiz.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecurityTransfer {
    private long uid;
    private String role;
    private String[] permissions;
}
