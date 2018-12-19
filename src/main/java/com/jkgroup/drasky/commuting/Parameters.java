package com.jkgroup.drasky.commuting;

import com.jkgroup.drasky.intent.AppConfiguration;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.model.ParameterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum Parameters {
    DESTINATION("destination", ParameterType.SYS_ANY),
    DURATION("duration", ParameterType.SYS_DURATION),
    DATE("date", ParameterType.SYS_DATE),
    TIME("time", ParameterType.SYS_TIME);

    private String name;
    private ParameterType type;

    public static String getDestination(@RequestBody DialogFlowRequest request) {
        return Parameters.DESTINATION.get(request)
                .map(it -> String.class.cast(it) )
                .orElse(""); // should never happen as it is required param
    }

    public static Duration getDuration(@RequestBody DialogFlowRequest request) {
        return Parameters.DURATION.get(request)
                .map(it -> (Map<String, Object>) it)
                .map(it -> com.jkgroup.drasky.intent.model.Duration.of((Integer) it.get("amount"), it.get("unit").toString()))
                .map(it -> it.toJavaDuration())
                .orElse(Duration.ZERO);
    }

    public static LocalDate getDate(@RequestBody DialogFlowRequest request) {
        return Parameters.DATE.get(request)
                .map(it -> String.class.cast(it) )
                .filter(it -> !it.isEmpty())
                .map(it -> ZonedDateTime.parse(it, AppConfiguration.DATE_TIME_FORMATTER))
                .map(it -> it.toLocalDate())
                .orElse(LocalDate.now());
    }

    public static LocalTime getTime(@RequestBody DialogFlowRequest request) {
        return Parameters.TIME.get(request)
                .map(it -> String.class.cast(it) )
                .filter(it -> !it.isEmpty())
                .map(it -> ZonedDateTime.parse(it, AppConfiguration.DATE_TIME_FORMATTER))
                .map(it -> it.toLocalTime())
                .orElse(LocalTime.now());
    }

    private Optional<Object> get(DialogFlowRequest request){
        return Optional.ofNullable(request.getQueryResult().getParameters().get(this.name));
    }
}
