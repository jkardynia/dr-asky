package com.jkgroup.drasky.common.holidays;

import java.time.MonthDay;

public interface Holiday {

    MonthDay getMothDay();
    String getName();
}
