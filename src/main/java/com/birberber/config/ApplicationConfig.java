package com.birberber.config;

import com.birberber.constants.Constants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ApplicationConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(Constants.MESSAGESOURCE_BASENAME);
        messageSource.setDefaultEncoding(Constants.DEFAULT_ENCODING);
        return messageSource;
    }
}
