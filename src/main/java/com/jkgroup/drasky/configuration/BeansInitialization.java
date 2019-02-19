package com.jkgroup.drasky.configuration;

import com.jkgroup.drasky.common.holidays.Holidays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;

@Configuration
public class BeansInitialization {

    @Bean
    public Holidays holidays(){
        return Holidays.getDefault();
    }

    @Bean
    @Profile("!mock")
    public Clock clock(){
        return Clock.systemDefaultZone();
    }
}
