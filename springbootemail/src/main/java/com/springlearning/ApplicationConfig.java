package com.springlearning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class ApplicationConfig {
//    @Bean
//    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
//        FreeMarkerConfigurationFactoryBean fmConfigFactoryBean = new FreeMarkerConfigurationFactoryBean();
//        fmConfigFactoryBean.setTemplateLoaderPath("/templates/");
//        return fmConfigFactoryBean;
//    }

    @Bean
    public freemarker.template.Configuration getFreeMarkerConfiguration() {
        freemarker.template.Configuration config = new freemarker.template.Configuration(freemarker.template.Configuration.getVersion());
        config.setClassForTemplateLoading(this.getClass(), "/templates/");
        return config;
    }
}