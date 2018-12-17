package com.jkgroup.drasky.commuting;

import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.model.ParameterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
                .map(it -> (com.jkgroup.drasky.intent.model.Duration) it) // todo fix creating intent.model.Duration from Object
                .map(it -> it.toJavaDuration())
                .orElse(Duration.ZERO);
    }

    public static String getDate(@RequestBody DialogFlowRequest request) {
        return Parameters.DATE.get(request)
                .map(it -> String.class.cast(it)) // todo create LocalDate
                .orElse(LocalDate.now().toString()); // todo get only date part
    }

    public static String getTime(@RequestBody DialogFlowRequest request) {
        return Parameters.TIME.get(request)
                .map(it -> String.class.cast(it)) // todo create LocalTime
                .orElse(LocalTime.now().toString()); // todo get only time part
    }

    private Optional<Object> get(DialogFlowRequest request){
        return Optional.ofNullable(request.getQueryResult().getParameters().get(this.name));
    }
}
