package com.quasar.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author emmanuel
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Value("${quasar.challenge.version}")
	private String version;
	
	@Value("${spring.profiles.active}")
	private String profile;
	
	@Bean
	public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        	      .apiInfo(apiInfo())
        	      .securityContexts(Arrays.asList(securityContext()))
        	      .securitySchemes(Arrays.asList(apiKey()))
        	      .select()
        	      .apis(RequestHandlerSelectors.any())
        	      .paths(PathSelectors.any())
        	      .build();
    }
	
    public ApiInfo apiEndpointsInfo() {
        return new ApiInfoBuilder().title("Admission challenge to Mercado Libre, by CEMT Profile:" + profile)
                .description("Testing UI for API")
                .version(version)
                .build();
    }
    
    private ApiKey apiKey() { 
        return new ApiKey("JWT", "Authorization", "header"); 
    }
    
    private SecurityContext securityContext() { 
        return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
    } 

    private List<SecurityReference> defaultAuth() { 
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
        authorizationScopes[0] = authorizationScope; 
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo("Quasar Challenge","Admission challenge to Mercado Libre, by CEMT Profile:" + profile, version, "", null, "", "", Collections.emptyList());
    }
}
