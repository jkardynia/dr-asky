package com.jkgroup.drasky.holiday;

import com.jkgroup.drasky.common.holidays.Holiday;
import com.jkgroup.drasky.common.holidays.Holidays;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import com.jkgroup.drasky.intent.model.parameter.type.SysDatePeriod;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static com.jkgroup.drasky.holiday.HolidaysCheckAction.Parameters.month;

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
        Month month = month(request.getQueryResult().getParameters())
                .orElse(LocalDate.now().getMonth());

        List<Holiday> holidaysInMonth = holidays.getAllIn(Locale.forLanguageTag("pl-PL"), month);

        return DialogFlowResponse
                .builder()
                .fulfillmentText("There is " + holidaysInMonth.size() + " in this month")
                .build();
    }

    public static class Parameters {
        private static final String MONTH_PARAMETER = "month";

        public static Optional<Month> month(Map<String, Object> params){
            return new SysDatePeriod().getValue(MONTH_PARAMETER, params)
                    .map(it -> it.getStartDate().getMonth());
        }
    }
}
