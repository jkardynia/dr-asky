package com.jkgroup.drasky.holiday;

import com.jkgroup.drasky.common.holidays.Holidays;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import com.jkgroup.drasky.intent.model.parameter.ParameterResolver;
import com.jkgroup.drasky.intent.model.parameter.type.ParameterType;
import com.jkgroup.drasky.intent.model.parameter.type.SysDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Locale;

@IntentAction
public class HolidaysCheckAction implements Action {
    private final static String NAME = "holidays-check";

    private Holidays holidays;

    @Autowired
    public HolidaysCheckAction(Holidays holidays){
        this.holidays = holidays;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        LocalDate date = ParameterResolver.getSysDateValue(request, Parameters.DATE.getName()).orElse(LocalDate.now());

        int numberOfHolidays = holidays.getAllIn(Locale.forLanguageTag("pl-PL"), date.getMonth()).size();

        return DialogFlowResponse
                .builder()
                .fulfillmentText("There is " + numberOfHolidays + " in this month")
                .build();
    }

    @AllArgsConstructor
    @Getter
    public enum Parameters {
        DATE("date", SysDate.class);

        private String name;
        private Class<? extends ParameterType<?>> type;
    }
}
