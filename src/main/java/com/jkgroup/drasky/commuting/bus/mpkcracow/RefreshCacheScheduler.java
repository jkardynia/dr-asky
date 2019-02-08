package com.jkgroup.drasky.commuting.bus.mpkcracow;

import com.jkgroup.drasky.commuting.repository.BusRouteRepository;
import com.jkgroup.drasky.intent.AppConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefreshCacheScheduler {

    private static final String CRON_EVERY_DAY_AT_2 = "0 0 2 * * *";

    private CacheManager cacheManager;
    private MpkCracowConnector mpkCracowConnector;
    private BusRouteRepository busRouteRepository;

    public RefreshCacheScheduler(@Qualifier(AppConfiguration.NOT_EXPIRING_CACHE_BEAN_NAME) CacheManager cacheManager, MpkCracowConnector mpkCracowConnector, BusRouteRepository busRouteRepository){
        this.cacheManager = cacheManager;
        this.mpkCracowConnector = mpkCracowConnector;
        this.busRouteRepository = busRouteRepository;
    }

    @Scheduled(cron = "${bus-time-table.cracow.refresh-cache-cron:" + CRON_EVERY_DAY_AT_2 + "}", zone = BusCheckingCracowService.TIME_ZONE)
    public void refreshBusCracowCache(){
        cacheManager.getCache(MpkCracowConnector.CACHE_NAME).clear();
        busRouteRepository.findAll().forEach(route -> {
            mpkCracowConnector.gotoMpkBusPage(route.getLineNumber(), route.getDirection(), route.getStopName());
            log.info("Cache refreshed for {}", route);
        });
    }
}
