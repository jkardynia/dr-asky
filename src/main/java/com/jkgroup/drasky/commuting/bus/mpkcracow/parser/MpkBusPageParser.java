package com.jkgroup.drasky.commuting.bus.mpkcracow.parser;

import com.google.common.collect.Lists;
import org.jsoup.nodes.Document;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MpkBusPageParser {

    // Time tables with columns like below are not supported:
    // Pon/Wt, Wt/Śr, Śr/Czw
    // Czw/Pt	Pt/Sob-Sob/Nd	Nd/Pon
    public static TimeColumns extractTimeTableColumns(Document document){
        List<String> columnsNames = findAllColumns(document);

        List<Map<Column, String>> rows = getTableRows(document, columnsNames);

        HashMap<Column, List<LocalTime>> columns = new HashMap<>();
        columns.put(Column.WORK_DAY, getTimesInColumn(rows, Column.WORK_DAY));
        columns.put(Column.SATURDAY, getTimesInColumn(rows, Column.SATURDAY));
        columns.put(Column.HOLIDAY, getTimesInColumn(rows, Column.HOLIDAY));

        return new TimeColumns(columns);
    }

    private static List<String> findAllColumns(Document document) {
        try {
            return document.select("td:containsOwn(" + Column.HOUR.getColumnName() + ")").first().parent().children().eachText();
        }catch (NullPointerException e){
            return Lists.newArrayList();
        }
    }

    private static List<Map<Column, String>> getTableRows(Document document, List<String> columnsNames) {
        return document.select("td:containsOwn(" + Column.HOUR.getColumnName() + ")")
                .first() // flat from list to element
                .parent().parent() // select whole table
                .children() // select all rows
                .next() // skip header row
                .stream()
                .map(row -> row.children().eachText())
                .map(rowTexts -> getRow(columnsNames, rowTexts))
                .collect(Collectors.toList());
    }

    private static Map<Column, String> getRow(List<String> columnsNames, List<String> rowTexts) {
        Map<Column, String> row = new HashMap<>();

        for(int i =0; i < columnsNames.size(); i++){
            String col = columnsNames.get(i);

            if(i < rowTexts.size()) {
                row.put(Column.fromName(col), rowTexts.get(i));
            }else {
                row.put(Column.fromName(col), "");
            }
        }

        return row;
    }

    private static List<LocalTime> getTimesInColumn(List<Map<Column, String>> tableRows, Column column) {
        return tableRows.stream()
                .flatMap(it -> {
                    String hour = it.get(Column.HOUR);
                    List<String> minutes = new ArrayList<>(Arrays.asList(it.get(column).split(" ")));

                    return minutes.stream()
                            .filter(min -> !min.isEmpty())
                            .map(removeNonDigitsSymbols())
                            .map(min -> LocalTime.of(Integer.valueOf(hour), Integer.valueOf(min)));
                })
                .collect(Collectors.toList());
    }

    private static Function<String, String> removeNonDigitsSymbols() {
        return it -> it.replaceAll("\\D+","");
    }

    public static Optional<String> getDirectionId(Document document, String direction) {
        try {
            String href = document
                    .select("a:contains(" + direction + ")")
                    .attr("href");

            String[] hrefParts = href.split("linia=");

            String[] lineAndDirection = hrefParts[1].split("__");

            return Optional.ofNullable(lineAndDirection[1]); //// todo check if there will be null or ""
        }catch (NullPointerException e){
            return Optional.empty();
        }
    }

    public static Optional<String> getStopId(Document document, String stopName) {
        try {
            String href = document
                    .select("a:contains(" + stopName + ")")
                    .attr("href");

            String[] hrefParts = href.split("linia=");

            String[] lineAndDirectionAndStop = hrefParts[1].split("__");
            return Optional.ofNullable(lineAndDirectionAndStop[2]); // todo check if there will be null or ""
        }catch (NullPointerException e){
            return Optional.empty();
        }
    }
}
