package com.jkgroup.drasky.holiday;

import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.*;

@IntentAction
public class IsMerchantSundayIntentAction implements Action {

    private String name;

    public IsMerchantSundayIntentAction(@Value("${dr-asky.intent.merchant-sunday.action-name}") String actionName){
        this.name = actionName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        boolean isMerchantSunday = isLastSundayInMonth();

        return DialogFlowResponse
                .builder()
                .fulfillmentText("On next Sunday shops are " + (isMerchantSunday ? "opened" : "closed"))
                .build();
    }

    private boolean isLastSundayInMonth() {
        final LocalDate nextSunday = LocalDate.now().with(next(SUNDAY));
        final LocalDate monthLastSunday = LocalDate.now().with(lastDayOfMonth())
                .with(previousOrSame(SUNDAY));

        return nextSunday.equals(monthLastSunday);
    }
}
