package com.jkgroup.drasky.integrationTests

import com.jkgroup.drasky.commuting.bus.mpkcracow.BusUrlResolver
import com.jkgroup.drasky.health.service.airly.AirlyClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import spock.mock.DetachedMockFactory

import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Configuration
class BeansStubsConfiguration {
    public final LocalDateTime FIXED_CLOCK_DATE_TIME = LocalDateTime.of(2018, 2, 13, 11, 55);

    private DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

    @Bean
    @Profile("mock")
    public Clock mockClock(){
        return Clock.fixed(FIXED_CLOCK_DATE_TIME.toInstant(ZoneId.of("Europe/Warsaw").getRules().getOffset(Instant.now())),
                ZoneId.of("Europe/Warsaw"));
    }

    @Bean
    @Profile("mock")
    AirlyClient airlyClient() {
        return detachedMockFactory.Stub(AirlyClient)
    }

    @Bean
    @Profile("mock")
    @Primary
    BusUrlResolver busUrlResolver() {
        return detachedMockFactory.Stub(BusUrlResolver)
    }
}
