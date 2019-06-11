package io.dynabiz.apigateway.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.dynabiz.spring.web.security.core.accesstoken.AccessTokenManager;
import org.dynabiz.std.exception.TokenException;
import org.dynabiz.web.context.ServiceContextHolder;
import org.dynabiz.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreFilter extends ZuulFilter {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AccessTokenManager accessTokenManager;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        try{
            ServiceContextHolder.start();
            RequestContext context = RequestContext.getCurrentContext();
            String tokenVal = context.getRequest().getHeader("X-ACCESS-TOKEN");

            if(tokenVal != null){
                accessTokenManager.load(tokenVal);
            }
            context.addZuulRequestHeader("X-SERVICE-SESSION-ID", ServiceContextHolder.getSessionId());
            ServiceContextHolder.flush();
        }
        catch (Exception e){
                RequestContext ctx = RequestContext.getCurrentContext();
                ctx.remove("error.status_code");
                try {
                    ctx.setResponseBody(objectMapper.writeValueAsString(new GeneralResponse(TokenException.TOKEN_NOT_FOUND)));
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                }
                ctx.getResponse().setContentType("application/json");
                ctx.setResponseStatusCode(200); //Can set any error code as excepted
        }
        return null;
    }
}
