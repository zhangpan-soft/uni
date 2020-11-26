package com.dv.uni.commons.config.swagger;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/22 0022
 */
@Data
public class Swagger2FactoryBean implements FactoryBean<Docket> {
    private Swagger2ConfigProperty property;
    @Override
    public Docket getObject() throws Exception {
        return new Docket(DocumentationType.SWAGGER_2).groupName(property.getGroupName())
                                                      .apiInfo(new ApiInfoBuilder().title(property.getTitle())
                                                                                   .termsOfServiceUrl(property.getTermsOfServiceUrl())
                                                                                   .contact(property.getContact())
                                                                                   .version(property.getVersion())
                                                                                   .build())
                                                      .select()
                                                      .apis(RequestHandlerSelectors.basePackage(property.getBasicPackage()))
                                                      .paths(PathSelectors.any())
                                                      .build()
                                                      .globalOperationParameters(new ArrayList<Parameter>() {{
                                                          if (property.getParameters()!=null&&!property.getParameters().isEmpty()){
                                                              property.getParameters().forEach(parameterProperty -> {
                                                                  this.add(new ParameterBuilder().name(parameterProperty.getName())
                                                                                                 .description(parameterProperty.getDescription())
                                                                                                 .modelRef(new ModelRef(parameterProperty.getModelRef()))
                                                                                                 .parameterType(parameterProperty.getParameterType())
                                                                                                 .required(parameterProperty.getRequired())
                                                                                                 .build());
                                                              });

                                                          }
                                                      }});
    }

    @Override
    public Class<?> getObjectType() {
        return Docket.class;
    }
}
