package io.dynabiz.apigateway.filter;

import com.as.web.context.ServiceContextHolder;
import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

@Component
public class PostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "post";
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
        ServiceContextHolder.clear();
        return null;
    }

}
