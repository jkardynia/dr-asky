package com.jkgroup.drasky.holiday;

import com.jkgroup.drasky.intent.CalendarUtil;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;

import java.time.LocalDate;

@IntentAction
public class IsMerchantSundayAction implements Action {

    private final static String NAME = "merchant-sunday";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        boolean isMerchantSunday = CalendarUtil.isLastSundayInMonth(LocalDate.now());

        return DialogFlowResponse
                .builder()
                .fulfillmentText("Next Sunday shops are " + (isMerchantSunday ? "open" : "closed"))
                .build();
    }
}
