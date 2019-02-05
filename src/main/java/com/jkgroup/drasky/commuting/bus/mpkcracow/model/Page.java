package com.jkgroup.drasky.commuting.bus.mpkcracow.model;

import com.google.common.collect.Lists;
import com.jkgroup.drasky.commuting.bus.mpkcracow.parser.Column;
import com.jkgroup.drasky.commuting.bus.mpkcracow.parser.MpkBusPageParser;
import com.jkgroup.drasky.commuting.bus.mpkcracow.parser.TimeColumns;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Optional;

public class Page {
    private final Document document;
    private final String pageBusNumber;
    private final Direction pageDirection;
    private final BusStop pageStop;


    private TimeTable timeTable;
    private List<Direction> directions = Lists.newArrayList();
    private List<BusStop> busStops = Lists.newArrayList();

    public Page(Document document, String busNumber, Direction direction, BusStop stop){
        this.document = document.clone();
        this.pageBusNumber = busNumber;
        this.pageDirection = direction;
        this.pageStop = stop;
    }

    public TimeTable getTimeTable(){
        if(timeTable == null){
            TimeColumns columns = MpkBusPageParser.extractTimeTableColumns(document);
            timeTable = new TimeTable(pageBusNumber, pageDirection, pageStop, columns.get(Column.WORK_DAY),
                    columns.get(Column.SATURDAY),
                    columns.get(Column.HOLIDAY));
        }

        return timeTable;
    }

    public Optional<Direction> getDirectionByName(String name){
        if(!directions.stream().filter(it -> it.getName().equals(name)).findAny().isPresent()){
            Optional<String> dirId = MpkBusPageParser.getDirectionId(document, name);

            if(dirId.isPresent()){
                directions.add(new Direction(dirId.get(), name));
            }
        }

        return directions.stream()
                .filter(it -> it.getName().equals(name))
                .findFirst();
    }

    public Optional<BusStop> getBusStopByName(String name){
        if(!busStops.stream().filter(it -> it.getName().equals(name)).findAny().isPresent()){
            Optional<String> busStopId = MpkBusPageParser.getStopId(document, name);

            if(busStopId.isPresent()){
                busStops.add(new BusStop(busStopId.get(), name));
            }
        }

        return busStops.stream()
                .filter(it -> it.getName().equals(name))
                .findFirst();
    }
}
