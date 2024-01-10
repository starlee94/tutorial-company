package com.tca.core.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import java.util.Arrays;

/**
 * @author star.lee
 */
public class DefaultFiltersBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory bf)
            throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) bf;

        Arrays.stream(beanFactory.getBeanNamesForType(Filter.class))
                .forEach(name -> {

                    BeanDefinition defFilter = BeanDefinitionBuilder
                            .genericBeanDefinition(FilterRegistrationBean.class)
                            .setScope(BeanDefinition.SCOPE_SINGLETON)
                            .addConstructorArgReference(name)
                            .addConstructorArgValue(new ServletRegistrationBean[]{})
                            .addPropertyValue("enabled", false)
                            .getBeanDefinition();

                    beanFactory.registerBeanDefinition(name + "FilterRegistrationBean",
                            defFilter);

                });

        Arrays.stream(beanFactory.getBeanNamesForType(SecurityFilterChain.class))
                .forEach(name -> {

                    BeanDefinition defSecurityChain = BeanDefinitionBuilder
                            .genericBeanDefinition(DelegatingFilterProxyRegistrationBean.class)
                            .setScope(BeanDefinition.SCOPE_SINGLETON)
                            .addConstructorArgReference(name)
                            .addConstructorArgValue(new ServletRegistrationBean[]{})
                            .addPropertyValue("enabled", false)
                            .getBeanDefinition();

                    beanFactory.registerBeanDefinition(name + "DelegatingFilterProxyRegistrationBean",
                            defSecurityChain);

                });
    }
}