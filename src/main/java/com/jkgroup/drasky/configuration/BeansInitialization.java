package com.jkgroup.drasky.configuration;

import com.jkgroup.drasky.common.holidays.Holidays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansInitialization {

    @Bean
    public Holidays holidays(){
        return Holidays.getDefault();
    }
}
