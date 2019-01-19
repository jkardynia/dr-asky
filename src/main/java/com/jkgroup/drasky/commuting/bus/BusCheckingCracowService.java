package com.jkgroup.drasky.commuting.bus;

import com.jkgroup.drasky.common.holidays.Holidays;
import com.jkgroup.drasky.commuting.repository.BusRoute;
import com.jkgroup.drasky.intent.CalendarUtil;
import com.jkgroup.drasky.intent.model.IntentClientException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BusCheckingCracowService {

    private Client client;
    private Holidays holidays;

    private static final Locale LOCALE = Locale.forLanguageTag("pl-PL");

    private static final String HOUR_COLUMN_NAME = "Hour";
    private static final String WORKDAY_COLUMN_NAME = "Workday";
    private static final String SATURDAY_COLUMN_NAME = "Saturdays";
    private static final String HOLIDAY_COLUMN_NAME = "Holidays";

    @Autowired
    public BusCheckingCracowService(@Value("${bus-time-table.cracow.base-url}") String baseUrl, Holidays holidays){
        client = new Client(baseUrl);
        this.holidays = holidays;
    }

    public BusInfo findBus(List<BusRoute> busRoutes, LocalDateTime dateTimeAfter){
        BusRoute busRoute = busRoutes.get(0); // todo support many routes
        String directionId = getDirectionId(busRoute);
        String stopId = getStopId(busRoute, directionId);

        // Time tables with columns like below are not supported:
        // Pon/Wt, Wt/Śr, Śr/Czw
        // Czw/Pt	Pt/Sob-Sob/Nd	Nd/Pon

        TimeTable timeTable = getTimeTable(busRoute, directionId, stopId);
        LocalTime nextArrivalTime = getNextArrivalTime(dateTimeAfter, timeTable)
                .orElseThrow(() -> new IntentClientException("Cannot find next bus today", "Cannot find next bus today")); //todo should search next day instead;

        return new BusInfo(busRoute.getLineNumber(), dateTimeAfter.with(nextArrivalTime));
    }

    private Optional<LocalTime> getNextArrivalTime(LocalDateTime dateTimeAfter, TimeTable timeTable) {
        if(DayOfWeek.SUNDAY.equals(DayOfWeek.from(dateTimeAfter)) || holidays.isHoliday(LOCALE, dateTimeAfter.toLocalDate())){
            return getForHoliday(dateTimeAfter, timeTable);
        }else if(DayOfWeek.SATURDAY.equals(DayOfWeek.from(dateTimeAfter))){
            return getForSaturday(dateTimeAfter, timeTable);
        }else if(CalendarUtil.isWeekDay(dateTimeAfter)){
            return getForWorkDay(dateTimeAfter, timeTable);
        }

        throw new IntentClientException("Something went wrong. Day " + DayOfWeek.from(dateTimeAfter) + " not supported.", "Something went wrong. Day " + DayOfWeek.from(dateTimeAfter) + " not supported.");
    }

    private Optional<LocalTime> getForWorkDay(LocalDateTime dateTimeAfter, TimeTable timeTable) {
        return timeTable.getWorkday().stream()
                .filter(time -> time.isAfter(dateTimeAfter.toLocalTime()))
                .findFirst();
    }

    private Optional<LocalTime> getForSaturday(LocalDateTime dateTimeAfter, TimeTable timeTable) {
        return timeTable.getSaturday().stream()
                .filter(time -> time.isAfter(dateTimeAfter.toLocalTime()))
                .findFirst();
    }

    private Optional<LocalTime> getForHoliday(LocalDateTime dateTimeAfter, TimeTable timeTable) {
        return timeTable.getHoliday().stream()
                .filter(time -> time.isAfter(dateTimeAfter.toLocalTime()))
                .findFirst();
    }

    private String getDirectionId(BusRoute busRoute) {
        String href = client.getTimeTable(busRoute.getLineNumber(), "1")
            .select("a:contains("+ busRoute.getDirection() +")")
            .attr("href"); // default line page to load names and numbers

        String[] hrefParts = href.split("linia=");

        String[] lineAndDirection = hrefParts[1].split("__");
        return lineAndDirection[1];
    }

    private String getStopId(BusRoute busRoute, String directionId) {
        String href = client.getTimeTable(busRoute.getLineNumber(), directionId)
                .select("a:contains("+ busRoute.getStopName() +")")
                .attr("href"); // default line page to load names and numbers

        String[] hrefParts = href.split("linia=");

        String[] lineAndDirectionAndStop = hrefParts[1].split("__");
        return lineAndDirectionAndStop[2];
    }

    // todo cache that, and schedule refreshing cache at 2 a.m.
    private TimeTable getTimeTable(BusRoute busRoute, String directionId, String stopId) {
        Document timeTableDocument = client.getTimeTable(busRoute.getLineNumber(), directionId, stopId);

        List<String> columnsNames = timeTableDocument.select("td:containsOwn(" + HOUR_COLUMN_NAME + ")").first().parent().children().eachText();

        List<Map<String, String>> times = timeTableDocument.select("td:containsOwn(" + HOUR_COLUMN_NAME + ")")
                .first() // flat from list to element
                .parent().parent() // select whole table
                .children() // select all rows
                .next() // skip header row
                .stream()
                .map(row -> row.children().eachText())
                .map(rowTexts -> {
                    Map<String, String> row = new HashMap<>();

                    for(int i =0; i < columnsNames.size(); i++){
                        String col = columnsNames.get(i);

                        if(i < rowTexts.size()) {
                            row.put(col, rowTexts.get(i));
                        }else {
                            row.put(col, "");
                        }
                    }

                    return row;
                })
                .collect(Collectors.toList());

        List<LocalTime> weekDay = getTimesInColumn(times, WORKDAY_COLUMN_NAME);
        List<LocalTime> saturdays = getTimesInColumn(times, SATURDAY_COLUMN_NAME);
        List<LocalTime> holidays = getTimesInColumn(times, HOLIDAY_COLUMN_NAME);

        return new TimeTable(busRoute.getLineNumber(),
                busRoute.getDirection(),
                busRoute.getStopName(),
                weekDay,
                saturdays,
                holidays);
    }

    private List<LocalTime> getTimesInColumn(List<Map<String, String>> times, String columnName) {
        return times.stream()
                .flatMap(it -> {
                    String hour = it.get(HOUR_COLUMN_NAME);
                    List<String> minutes = new ArrayList<>(Arrays.asList(it.get(columnName).split(" ")));

                    return minutes.stream()
                            .filter(min -> !min.isEmpty())
                            .map(min -> min.replaceAll("\\D+","")) // remove extra non digit symbols
                            .map(min -> LocalTime.of(Integer.valueOf(hour), Integer.valueOf(min)));
                })
                .collect(Collectors.toList());
    }
}

class Client {
    private String baseUrl;
    private static final String TIME_TABLE_PATH_PATTERN = "?linia={line_nr}__{direction_nr}__{stop_nr}";
    private static final String TIME_TABLE_DEFAULT_STOP_PATH_PATTERN = "?linia={line_nr}__{direction_nr}";

    public Client(String baseUrl){
        this.baseUrl = baseUrl;
    }

    private Document prepareConnection(Connection connection) throws IOException {
        return connection
                    .cookie("ROZKLADY_AB", "0")
                    .cookie("ROZKLADY_WIDTH", "200")
                    .cookie("ROZKLADY_JEZYK", "EN")
                    .get();
    }

    public Document getTimeTable(String busNumber, String directionNumber) {
        try{
            return prepareConnection(Jsoup.connect(new URL(new URL(baseUrl), TIME_TABLE_DEFAULT_STOP_PATH_PATTERN
                .replace("{line_nr}", busNumber)
                .replace("{direction_nr}", directionNumber)).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Document getTimeTable(String busNumber, String directionNumber, String stopNumber) {
        try{
            return prepareConnection(Jsoup.connect(new URL(new URL(baseUrl), TIME_TABLE_PATH_PATTERN
                    .replace("{line_nr}", busNumber)
                    .replace("{direction_nr}", directionNumber)
                    .replace("{stop_nr}", stopNumber)).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
