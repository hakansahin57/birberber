package com.birberber.config;

import com.birberber.constants.BirBerberConstants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ApplicationConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(BirBerberConstants.BIRBERBER_MESSAGESOURCE_BASENAME);
        messageSource.setDefaultEncoding(BirBerberConstants.BIRBERBER_DEFAULT_ENCODING);
        return messageSource;
    }
}
