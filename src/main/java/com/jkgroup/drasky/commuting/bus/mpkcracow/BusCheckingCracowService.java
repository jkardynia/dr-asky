package com.jkgroup.drasky.commuting.bus.mpkcracow;

import com.jkgroup.drasky.common.holidays.Holidays;
import com.jkgroup.drasky.commuting.bus.BusClientException;
import com.jkgroup.drasky.commuting.bus.BusInfo;
import com.jkgroup.drasky.commuting.bus.mpkcracow.model.TimeTable;
import com.jkgroup.drasky.commuting.repository.BusRoute;
import com.jkgroup.drasky.intent.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class BusCheckingCracowService {

    private MpkCracowConnector mpkCracowConnector;
    private Holidays holidays;

    private static final Locale LOCALE = Locale.forLanguageTag("pl-PL");
    public static final String TIME_ZONE = "Europe/Warsaw";

    @Autowired
    public BusCheckingCracowService(MpkCracowConnector mpkCracowConnector, Holidays holidays){
        this.mpkCracowConnector = mpkCracowConnector;
        this.holidays = holidays;
    }

    public BusInfo findBus(List<BusRoute> busRoutes, LocalDateTime dateTimeAfter){
        return busRoutes.stream()
                .flatMap(getBusInfoForRoute(dateTimeAfter))
                .min(Comparator.comparing(BusInfo::getArrivalTime))
                .orElseThrow(BusClientException::busNotFound); //todo should search next day instead;
    }

    private Function<BusRoute, Stream<BusInfo>> getBusInfoForRoute(LocalDateTime dateTimeAfter) {
        return route -> getNextArrivalTime(dateTimeAfter, mpkCracowConnector.gotoMpkBusPage(route.getLineNumber(),
                                                                route.getDirection(),
                                                                route.getStopName())
                                                            .getTimeTable())
                    .map(nextArrivalTime -> new BusInfo(route.getLineNumber(), dateTimeAfter.with(nextArrivalTime)))
                    .map(busInfo -> Stream.of(busInfo))
                    .orElse(Stream.empty());
    }

    private Optional<LocalTime> getNextArrivalTime(LocalDateTime dateTimeAfter, TimeTable timeTable) {
        if(DayOfWeek.SUNDAY.equals(DayOfWeek.from(dateTimeAfter)) || holidays.isHoliday(LOCALE, dateTimeAfter.toLocalDate())){
            return getNextAfter(dateTimeAfter, timeTable.getHoliday());
        }else if(DayOfWeek.SATURDAY.equals(DayOfWeek.from(dateTimeAfter))){
            return getNextAfter(dateTimeAfter, timeTable.getSaturday());
        }else if(CalendarUtil.isWeekDay(dateTimeAfter)){
            return getNextAfter(dateTimeAfter, timeTable.getWorkday());
        }

        return Optional.empty();
    }

    private Optional<LocalTime> getNextAfter(LocalDateTime dateTimeAfter, List<LocalTime> times) {
        return times.stream()
                .filter(time -> time.isAfter(dateTimeAfter.toLocalTime()))
                .findFirst();
    }
}
