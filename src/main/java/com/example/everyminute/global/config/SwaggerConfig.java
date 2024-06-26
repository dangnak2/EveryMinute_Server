package com.example.everyminute.global.config;


import com.example.everyminute.global.CustomPage;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.global.response.ResponseCustom;
import com.example.everyminute.user.entity.User;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {
    private static final String API_NAME = "EVERY_MINUTE_API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "EVERY_MINUTE_API_명세서";

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(CustomPage.class))
                )
                .additionalModels(
                        typeResolver.resolve(ResponseCustom.class)
                )
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(ZonedDateTime.class, String.class)
                .ignoredParameterTypes(User.class)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.example.everyminute"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(getApiDescription(getErrorList()))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
    }

    private String getErrorList() {
        StringBuilder errorList = new StringBuilder();
        BaseResponseCode[] exceptionLists = BaseResponseCode.values();
        for (BaseResponseCode exceptionList : exceptionLists) {
            errorList.append("<tr>");
            errorList.append("<td>").append(exceptionList.getCode()).append("</td>");
            errorList.append("<td>").append(exceptionList.getStatus()).append("</td>");
            errorList.append("<td>").append(exceptionList.getMessage()).append("</td>");
            errorList.append("</tr>");
        }
        return errorList.toString();
    }

    public String getApiDescription(String errorList) {
        String description = """
                    Everyminute API 명세서입니다.<br>
                    스웨거 한계로 인해 Response에 공통 response의 data에 대한 정의가 되어있습니다. <br>
                    <details>
                        <summary> ERROR LIST </summary>
                        <table>
                            <thead>
                            <tr>
                                <th>ERROR CODE</th>
                                <th>STATUS</th>
                                <th>ERROR MESSAGE</th>
                            </tr>
                            </thead>
                            <tbody>
                                """
                + errorList
                + """
                                    </tbody>
                                <table>
                            </details>
                            """;
        return description;
    }
}
