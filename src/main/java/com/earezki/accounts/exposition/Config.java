package com.earezki.accounts.exposition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class Config {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

}
