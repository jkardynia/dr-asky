package com.jkgroup.drasky.common.holidays;

import java.time.MonthDay;

public interface Holiday {

    MonthDay getMonthDay();
    String getName();
}
