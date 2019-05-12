package io.dynabiz.apigateway.controller;

import com.as.mapper.Mapper;
import com.as.web.security.core.accesstoken.AccessToken;
import com.timescodes.dawn.app.service.TokenService;
import com.timescodes.dawn.app.vos.dto.AccessTokenTransfer;
import com.timescodes.dawn.app.vos.dto.SignInTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/access/token")
public class TokenController {
    private TokenService tokenService;

    /**
     * 登陆操作
     * @param transfer 登录凭证
     */
    @PostMapping
    public AccessTokenTransfer signIn(SignInTransfer transfer){
        AccessToken token = tokenService.signIn(transfer);
        return Mapper.mapFrom(AccessTokenTransfer.class, token);
    }
    /**
     * Admin登陆操作
     * @param transfer 登录凭证
     */
    @PostMapping("/admin")
    public AccessTokenTransfer signInAdmin(SignInTransfer transfer){
        AccessToken token = tokenService.signInAdmin(transfer);
        return Mapper.mapFrom(AccessTokenTransfer.class, token);
    }

    /**
     * 注销操作
     */
    @DeleteMapping
    public void signOut(@RequestHeader("X-ACCESS-TOKEN") String accessToken) {
        tokenService.signOut(accessToken);
    }

    /**
     * 续签操作
     * @return
     */
    @PutMapping
    public AccessTokenTransfer refresh(@RequestHeader("X-REFRESH-TOKEN") String refreshToken) {
        AccessToken token =  tokenService.refresh(refreshToken);
        return Mapper.mapFrom(AccessTokenTransfer.class, token);
    }

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}
