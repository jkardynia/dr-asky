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
public class IsMerchantSundayAction implements Action {

    private String name;

    public IsMerchantSundayAction(@Value("${dr-asky.intent.merchant-sunday.action-name}") String actionName){
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
                .fulfillmentText("Next Sunday shops are " + (isMerchantSunday ? "open" : "closed"))
                .build();
    }

    private boolean isLastSundayInMonth() {
        final LocalDate nextSunday = LocalDate.now().with(nextOrSame(SUNDAY));
        final LocalDate monthLastSunday = LocalDate.now().with(lastDayOfMonth())
                .with(previousOrSame(SUNDAY));

        return nextSunday.equals(monthLastSunday);
    }
}
