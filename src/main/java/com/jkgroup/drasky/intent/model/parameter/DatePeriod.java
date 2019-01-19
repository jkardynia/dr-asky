package com.jkgroup.drasky.intent.model.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class DatePeriod {
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}
