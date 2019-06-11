package io.dynabiz.apigateway;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SwaggerService {

    @Autowired
    EurekaClient eurekaClient;

    @Autowired
    private ZuulProperties properties;


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Sixminers APIs Document")
                .description("Documentation for all services")
                .build();
    }

    @Bean
    @Primary
    public SwaggerResourcesProvider swaggerResource(){
        return new SwaggerResourcesProvider(){

            @Override
            public List<SwaggerResource> get() {
                List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
                List resources = new ArrayList<>();
                for (Application application : applications){
                    if(application.getInstances().size() > 0){
                        ZuulProperties.ZuulRoute route = properties.getRoutes().get(application.getName());
                        if(route != null){
                            resources.add(swaggerResource(application.getName(),  "/" + route.getId() + "/v2/api-docs", "2.0"));
                        }
                    }

                }

                return resources;
            }


            private SwaggerResource swaggerResource(String name, String location, String version){
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setName(name);
                swaggerResource.setLocation(location);
                swaggerResource.setSwaggerVersion(version);
                return swaggerResource;
            }
        };
    }




}
