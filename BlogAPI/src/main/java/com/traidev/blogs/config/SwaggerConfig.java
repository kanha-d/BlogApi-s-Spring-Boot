package com.traidev.blogs.config;



import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Authorization;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	public static final String AUTHRIZATION_HEADER = "Authorization";
	
	private ApiKey apiKeys() {
		
		return new ApiKey("JWT", AUTHRIZATION_HEADER, "Header");
	}
	
	private List<SecurityContext> securityContexts(){
		
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
		
	}
	
	private List<SecurityReference> sf(){
		
		AuthorizationScope scopes = new AuthorizationScope("global","accessEverything");
		
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scopes }));
	}
	
	
	@Bean
	public Docket api() {
		
	
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts()) 
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build(); 
	}
	
	
	public void CheckBin() {
		System.out.println("Hello this is Kanha Bean Does!");
	}
	
	public static final Contact DEFAULT_CONTACT
     = new Contact(
     "",
     "",
     "");
	 
	
	@SuppressWarnings("deprecation")
	private ApiInfo getInfo() {
	  return new ApiInfo("Bloging Application Backend","Demo project for Learning things",
	      "1.0", "Terms of Services", "Traidev Team", "EPL", "https://www.eclipse.org/legal/epl-2.0");
	}
	
	

}
