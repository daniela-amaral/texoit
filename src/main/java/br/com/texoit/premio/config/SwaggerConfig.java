package br.com.texoit.premio.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.texoit.premio"))
          .paths(PathSelectors.any())
          .build()
          .useDefaultResponseMessages(false)
          .globalResponseMessage(RequestMethod.GET, responseMessage())
          .apiInfo(apiInfo());
    }

	private List<ResponseMessage> responseMessage() {
	    return new ArrayList<ResponseMessage>() {{
	        add(new ResponseMessageBuilder()
	            .code(404)
	            .message("Não encontrado")
	            .build());
	    }};
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("API para obter intervalo entre prêmios")
	            .description("Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido")
	            .version("1.0.0")
	            .build();
	}
}