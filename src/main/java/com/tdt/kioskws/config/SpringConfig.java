package com.tdt.kioskws.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * SpringConfig
 */
@Configuration
@Component
public class SpringConfig {

    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {

        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("app_config.properties"));
        return configurer;
    }
}
