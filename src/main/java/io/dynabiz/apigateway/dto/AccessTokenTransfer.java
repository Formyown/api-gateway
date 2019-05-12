package io.dynabiz.apigateway.dto;

import com.as.mapper.MappedData;
import com.as.mapper.annotation.Mapped;
import com.as.mapper.annotation.MappedConfig;
import com.as.web.security.core.accesstoken.AccessToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedConfig(targetClass = AccessToken.class)
public class AccessTokenTransfer extends MappedData<AccessTokenTransfer>{
    @Mapped
    private String accessToken;
    @Mapped
    private String refreshToken;
    @Mapped
    private long accessTokenExpireIn;
    @Mapped
    private long refreshTokenExpireIn;
    @Mapped
    private String role;
    @Mapped
    private Set<String> permissions;

}
