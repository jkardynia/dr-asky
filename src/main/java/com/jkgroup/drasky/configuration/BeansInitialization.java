package com.jkgroup.drasky.configuration;

import com.jkgroup.drasky.common.holidays.Holidays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class BeansInitialization {

    public final LocalDateTime FIXED_CLOCK_DATE_TIME = LocalDateTime.of(2018, 2, 13, 11, 55);

    @Bean
    public Holidays holidays(){
        return Holidays.getDefault();
    }

    @Bean
    @Profile("!mock")
    public Clock clock(){
        return Clock.systemDefaultZone();
    }

    @Bean
    @Profile("mock")
    public Clock mockClock(){
        return Clock.fixed(FIXED_CLOCK_DATE_TIME.toInstant(ZoneId.of("Europe/Warsaw").getRules().getOffset(Instant.now())),
                ZoneId.of("Europe/Warsaw"));
    }
}
