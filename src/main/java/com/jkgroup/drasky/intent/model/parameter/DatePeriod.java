package com.jkgroup.drasky.intent.model.parameter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode
public class DatePeriod {
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}
