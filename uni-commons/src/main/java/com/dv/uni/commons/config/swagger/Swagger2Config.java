package com.dv.uni.commons.config.swagger;

import com.dv.uni.commons.utils.StringUtils;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@EnableConfigurationProperties(Swagger2ConfigProperties.class)
@Data
public class Swagger2Config {
    private static Swagger2ConfigProperties properties = new Swagger2ConfigProperties();

    static {
        ClassPathResource resource = new ClassPathResource("application.yml");
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(resource);
        Properties p = yamlPropertiesFactoryBean.getObject();
        List<Swagger2ConfigProperty> propertyList = properties.getProperties();
        if (propertyList==null){
            propertyList=new ArrayList<>();
        }
        boolean flag = true;
        int m = 0;
        while (flag) {
            flag = false;
            String basicPackage = p.getProperty("swagger2.properties[" + m + "].basic-package");
            String contact = p.getProperty("swagger2.properties[" + m + "].contact");
            String groupName = p.getProperty("swagger2.properties[" + m + "].group-name");
            String termsOfServiceUrl = p.getProperty("swagger2.properties[" + m + "].terms-of-service-url");
            String title = p.getProperty("swagger2.properties[" + m + "].title");
            String version = p.getProperty("swagger2.properties[" + m + "].version");

            List<ParameterProperty> parameters = new ArrayList<>();
            boolean flag1 = true;
            int n = 0;
            while (flag1) {
                flag1 = false;
                String description = p.getProperty("swagger2.properties[" + m + "].parameters[" + n + "].description");
                String modelRef = p.getProperty("swagger2.properties[" + m + "].parameters[" + n + "].model-ref");
                String name = p.getProperty("swagger2.properties[" + m + "].parameters[" + n + "].name");
                String parameterType = p.getProperty("swagger2.properties[" + m + "].parameters[" + n + "].parameter-type");
                String required = p.getProperty("swagger2.properties[" + m + "].parameters[" + n + "].required");
                if (!StringUtils.isEmpty(description) || !StringUtils.isEmpty(modelRef) || !StringUtils.isEmpty(name) || !StringUtils.isEmpty(parameterType) || !StringUtils.isEmpty(required)) {
                    flag1 = true;
                }
                if (flag1) {
                    ParameterProperty parameterProperty = new ParameterProperty();
                    parameterProperty.setDescription(description);
                    parameterProperty.setModelRef(modelRef);
                    parameterProperty.setName(name);
                    parameterProperty.setParameterType(parameterType);
                    parameterProperty.setRequired(StringUtils.isEmpty(required) ? false : Boolean.valueOf(required));
                    parameters.add(parameterProperty);
                }

                n++;
            }
            if (!StringUtils.isEmpty(basicPackage)
            ||!StringUtils.isEmpty(contact)
            ||!StringUtils.isEmpty(groupName)
            ||!StringUtils.isEmpty(termsOfServiceUrl)
            ||!StringUtils.isEmpty(title)
            ||!StringUtils.isEmpty(version)
            ||!parameters.isEmpty()){
                flag=true;
            }
            if (flag){
                Swagger2ConfigProperty property = new Swagger2ConfigProperty();
                property.setBasicPackage(basicPackage);
                property.setContact(contact);
                property.setGroupName(groupName);
                property.setTermsOfServiceUrl(termsOfServiceUrl);
                property.setTitle(title);
                property.setVersion(version);
                property.setParameters(parameters);
                propertyList.add(property);
            }
            m++;
        }
        properties.setProperties(propertyList);
    }

    @Bean
    public BeanDefinitionRegistryPostProcessor beanDefinitionRegistry(Environment environment) {
        return new BeanDefinitionRegistryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

            }

            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
                ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
                constructorArgumentValues.addGenericArgumentValue(DocumentationType.SWAGGER_2);
                if (properties.getProperties() != null && !properties.getProperties()
                                                                     .isEmpty()) {
                    properties.getProperties()
                              .forEach(property -> {
                                  RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Swagger2FactoryBean.class);
                                  rootBeanDefinition.getPropertyValues()
                                                    .addPropertyValue("property", property);
                                  beanDefinitionRegistry.registerBeanDefinition(property.getGroupName(), rootBeanDefinition);
                              });
                }
            }
        };
    }

}
