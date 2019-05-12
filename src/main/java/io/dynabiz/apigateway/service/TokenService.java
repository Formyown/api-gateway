package io.dynabiz.apigateway.service;


import io.dynabiz.apigateway.dto.SecurityTransfer;
import io.dynabiz.apigateway.dto.SignInTransfer;
import io.dynabiz.apigateway.dto.SignInWithDetailTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {
    private AccessTokenManager accessTokenManager;
    private UserServiceClient userServiceClient;

    public AccessToken signIn(Stirng domain, SignInTransfer transfer){
        SignInWithDetailTransfer detailTransfer = new SignInWithDetailTransfer();
        detailTransfer.setAppType(transfer.getAppType());
        detailTransfer.setAppVersion(transfer.getAppVersion());
        detailTransfer.setEmail(transfer.getEmail());
        detailTransfer.setAgent(HttpUtil.getUserAgent());
        detailTransfer.setIp(HttpUtil.getClientIP());
        detailTransfer.setPassword(transfer.getPassword());
        SecurityTransfer security = userServiceClient.getSecurity(detailTransfer);
        Set<String> permissions = new HashSet<>();
        Collections.addAll(permissions, security.getPermissions());
        return accessTokenManager.assign(security.getUid(),security.getRole(), permissions,detailTransfer.getAppType(),detailTransfer.getAppVersion());
    }


    public void signOut(String accessToken) {
        accessTokenManager.cancel(accessToken);
    }
    public AccessToken refresh(String refreshToken){
        return accessTokenManager.refresh(refreshToken);
    }

    @Autowired
    public TokenService(AccessTokenManager accessTokenManager, UserServiceClient userServiceClient) {
        this.accessTokenManager = accessTokenManager;
        this.userServiceClient = userServiceClient;
    }
}
