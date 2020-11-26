package com.dv.uni.commons.config.swagger;

import com.dv.universal.commons.basic.utils.StringUtils;
import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModelProperty;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wupeng
 * @date 2019/8/26 9:59
 * @desc 读取自定义忽略的dto属性并动态生成model
 */
@Configuration
@Order   //plugin加载顺序，默认是最后加载
@Deprecated
public class SwaggerModelReader implements ParameterBuilderPlugin {
    @Autowired
    private TypeResolver typeResolver;

    @Override
    public void apply(ParameterContext parameterContext) {
        ResolvedMethodParameter methodParameter = parameterContext.resolvedMethodParameter();
        Class originClass = parameterContext.resolvedMethodParameter()
                                            .getParameterType()
                                            .getErasedType();


        com.google.common.base.Optional<ApiIgp> optional = methodParameter.findAnnotation(ApiIgp.class);
        //        Optional<Apicp> annotation = methodParameter.findAnnotation(Apicp.class);


        if (optional.isPresent()) {
            Random random = new Random();
            String name = originClass.getSimpleName() + "Model" + StringUtils.salt(6, 62);  //model 名称
            String[] properties = optional.get()
                                          .value();
            try {
                parameterContext.getDocumentationContext()
                                .getAdditionalModels()
                                .add(typeResolver.resolve(createRefModelIgp(properties, originClass.getPackage() + "." + name, originClass)));  //像documentContext的Models中添加我们新生成的Class
            } catch (Exception e) {
                e.printStackTrace();
            }
            parameterContext.parameterBuilder()  //修改Map参数的ModelRef为我们动态生成的class
                            .parameterType("body")
                            .modelRef(new ModelRef(name))
                            .name(name);
        } else {
            com.google.common.base.Optional<Apicp> optional1 = methodParameter.findAnnotation(Apicp.class);
            if (optional1.isPresent()) {
                Random random = new Random();
                String name = originClass.getSimpleName() + "Model" + StringUtils.salt(6, 62);  //model 名称
                String[] properties = optional1.get()
                                               .value();

                List<String> list = Arrays.asList(properties);

                List<String> igpList = new ArrayList<>();

                Field[] declaredFields = originClass.getDeclaredFields();

                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    if (!list.contains(field.getName())) {
                        igpList.add(field.getName());
                    }
                    field.setAccessible(false);
                }

                try {
                    parameterContext.getDocumentationContext()
                                    .getAdditionalModels()
                                    .add(typeResolver.resolve(createRefModelIgp(igpList.toArray(new String[igpList.size()]), originClass.getPackage() + "." + name, originClass)));  //像documentContext的Models中添加我们新生成的Class
                } catch (Exception e) {
                    e.printStackTrace();
                }
                parameterContext.parameterBuilder()  //修改Map参数的ModelRef为我们动态生成的class
                                .parameterType("body")
                                .modelRef(new ModelRef(name))
                                .name(name);
            } else {
                if (methodParameter.getParameterType()
                                   .canCreateSubtype(Map.class) || methodParameter.getParameterType()
                                                                                  .canCreateSubtype(String.class)) { //判断是否需要修改对象ModelRef,这里我判断的是Map类型和String类型需要重新修改ModelRef对象
                    com.google.common.base.Optional<ApiJsonObject> optional2 = methodParameter.findAnnotation(ApiJsonObject.class);  //根据参数上的ApiJsonObject注解中的参数动态生成Class
                    if (optional2.isPresent()) {
                        String name = optional2.get()
                                               .name() + StringUtils.salt(6, 62);  //model 名称
                        ApiJsonProperty[] properties = optional2.get()
                                                                .value();

                        parameterContext.getDocumentationContext()
                                        .getAdditionalModels()
                                        .add(typeResolver.resolve(createRefModel(properties, name)));  //像documentContext的Models中添加我们新生成的Class

                        parameterContext.parameterBuilder()  //修改Map参数的ModelRef为我们动态生成的class
                                        .parameterType("body")
                                        .modelRef(new ModelRef(name))
                                        .name(name);
                    }


                }
            }
        }


    }

    private final static String basePackage = "com.fc.bank.in.swagger.model.";

    /**
     * 根据propertys中的值动态生成含有Swagger注解的javaBeen
     */
    private Class createRefModel(ApiJsonProperty[] propertys, String name) {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(basePackage + name);

        try {
            for (ApiJsonProperty property : propertys) {
                ctClass.addField(createField(property, ctClass));
            }
            return ctClass.toClass();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据property的值生成含有swagger apiModelProperty注解的属性
     */
    private CtField createField(ApiJsonProperty property, CtClass ctClass) throws NotFoundException, CannotCompileException {
        CtField ctField = new CtField(getFieldType(property.type()), property.key(), ctClass);
        ctField.setModifiers(Modifier.PUBLIC);

        ConstPool constPool = ctClass.getClassFile()
                                     .getConstPool();

        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation ann = new Annotation("io.swagger.annotations.ApiModelProperty", constPool);
        ann.addMemberValue("value", new StringMemberValue(property.description(), constPool));
        if (ctField.getType()
                   .subclassOf(ClassPool.getDefault()
                                        .get(String.class.getName())))
            ann.addMemberValue("example", new StringMemberValue(property.example(), constPool));
        if (ctField.getType()
                   .subclassOf(ClassPool.getDefault()
                                        .get(Integer.class.getName())))
            ann.addMemberValue("example", new IntegerMemberValue(Integer.parseInt(property.example()), constPool));

        attr.addAnnotation(ann);
        ctField.getFieldInfo()
               .addAttribute(attr);

        return ctField;
    }

    private CtClass getFieldType(String type) throws NotFoundException {
        CtClass fileType = null;
        switch (type) {
            case "string":
                fileType = ClassPool.getDefault()
                                    .get(String.class.getName());
                break;
            case "int":
                fileType = ClassPool.getDefault()
                                    .get(Integer.class.getName());
                break;
        }
        return fileType;
    }


    private Class createRefModelIgp(String[] propertys, String name, Class origin) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(name);
        try {
            Field[] fields = origin.getDeclaredFields();
            List<Field> fieldList = Arrays.asList(fields);
            List<String> ignorePropertys = Arrays.asList(propertys);
            List<String> ignoreList = new ArrayList<>();
            if (ignorePropertys != null && !ignorePropertys.isEmpty()) {
                ignoreList.addAll(ignorePropertys);
            }
            if (!ignoreList.contains("serialVersionUID"))
                ignoreList.add("serialVersionUID");
            for (int i = 0; i < ignoreList.size(); i++) {
                String l = ignoreList.get(i);
                if (l.startsWith("*") || l.endsWith("*")) {
                    ignoreList.remove(i);
                    i--;
                    if (l.startsWith("*") && l.endsWith("*")) {
                        fieldList.stream()
                                 .forEach(field -> {
                                     if (field.getName()
                                              .contains(l.replaceAll("\\*", ""))) {
                                         ignoreList.add(field.getName());
                                     }

                                 });
                    } else if (l.startsWith("*")) {
                        fieldList.stream()
                                 .forEach(field -> {
                                     if (field.getName()
                                              .endsWith(l.replaceAll("\\*", ""))) {
                                         ignoreList.add(field.getName());
                                     }
                                 });
                    } else if (l.endsWith("*")) {
                        fieldList.stream()
                                 .forEach(field -> {
                                     if (field.getName()
                                              .startsWith(l.replaceAll("\\*", ""))) {
                                         ignoreList.add(field.getName());
                                     }
                                 });
                    }
                }
            }
            ;
            List<Field> dealFileds = fieldList.stream()
                                              .filter(s -> !ignoreList.contains(s.getName()))
                                              .collect(Collectors.toList());
            for (Field field : dealFileds) {
                CtField ctField = new CtField(ClassPool.getDefault()
                                                       .get(field.getType()
                                                                 .getName()), field.getName(), ctClass);
                ctField.setModifiers(Modifier.PUBLIC);
                ApiModelProperty ampAnno = origin.getDeclaredField(field.getName())
                                                 .getAnnotation(ApiModelProperty.class);
                String attributes = Optional.ofNullable(ampAnno)
                                            .map(s -> s.value())
                                            .orElse("");
                if (StringUtils.notEmpty(attributes)) { //添加model属性说明
                    ConstPool constPool = ctClass.getClassFile()
                                                 .getConstPool();
                    AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                    Annotation ann = new Annotation(ApiModelProperty.class.getName(), constPool);
                    ann.addMemberValue("value", new StringMemberValue(attributes, constPool));
                    attr.addAnnotation(ann);
                    ctField.getFieldInfo()
                           .addAttribute(attr);
                }
                ctClass.addField(ctField);
            }
            return ctClass.toClass();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}