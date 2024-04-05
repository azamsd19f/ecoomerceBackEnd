package com.configs;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
		info = @Info(contact = @Contact(
				name="Azam",
				email="faisalazam1999@gmail.com",
				url=""),
		description="OpenApi documentation for spring",
		title="E Commerce App Test",
		version="1.0",
		license=@License(name="Licenece name"),
		termsOfService = "Terms of service"),
		servers = {@Server(
			description	="DEV ENV",
			url="http://localhost:8082"
			),
				@Server(
						description	="QA ENV",
						url="http://3.108.26.182:8082"
						)
		},
		security =  {
				@SecurityRequirement(name="bearerAuth")
		}
		)
@SecurityScheme
(
		name="bearerAuth",
		description="JWT Auth token",
		scheme="bearer",
		type=SecuritySchemeType.HTTP,
		bearerFormat="JWT",
		in = SecuritySchemeIn.HEADER)

public class SwaggerConfig {
	


}
