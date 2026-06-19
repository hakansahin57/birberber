package com.birberber.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDemoData(BulkDataSeeder bulkDataSeeder) {
        return args -> {
            bulkDataSeeder.run();
            BulkDataSeeder.logAdminCredentials();
        };
    }
}
