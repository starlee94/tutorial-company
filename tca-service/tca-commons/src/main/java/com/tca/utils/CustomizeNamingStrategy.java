package com.tca.utils;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.beans.Introspector;

public class CustomizeNamingStrategy extends AnnotationBeanNameGenerator {


    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = this.determineBeanNameFromAnnotation((AnnotatedBeanDefinition)definition);
            if (StringUtils.hasText(beanName)) {
                return beanName;
            }
        }
        String beanClassName = definition.getBeanClassName();
        Assert.state(beanClassName != null, "No bean class name set");
        return Introspector.decapitalize(beanClassName);
    }

}