package com.jkgroup.drasky.holiday;

import com.jkgroup.drasky.common.holidays.Holiday;
import com.jkgroup.drasky.common.holidays.HolidayException;
import com.jkgroup.drasky.common.holidays.Holidays;
import com.jkgroup.drasky.intent.IntentAction;
import com.jkgroup.drasky.intent.TemplateGenerator;
import com.jkgroup.drasky.intent.dto.DialogFlowRequest;
import com.jkgroup.drasky.intent.dto.DialogFlowResponse;
import com.jkgroup.drasky.intent.model.Action;
import com.jkgroup.drasky.intent.model.IntentClientException;
import com.jkgroup.drasky.intent.model.IntentException;
import com.jkgroup.drasky.intent.model.parameter.type.SysDatePeriod;
import com.jkgroup.drasky.intent.repository.Profile;
import com.jkgroup.drasky.intent.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static com.jkgroup.drasky.holiday.HolidaysCheckAction.Parameters.month;

@IntentAction
public class HolidaysCheckAction implements Action {
    private final static String NAME = "holidays-check";

    private Holidays holidays;
    private ProfileRepository profileRepository;
    private String defaultProfile;
    private TemplateGenerator templateGenerator;

    @Autowired
    public HolidaysCheckAction(Holidays holidays,
                               ProfileRepository profileRepository,
                               @Value("${dr-asky.default-profile}") String defaultProfile,
                               TemplateGenerator templateGenerator){
        this.holidays = holidays;
        this.profileRepository = profileRepository;
        this.defaultProfile = defaultProfile;
        this.templateGenerator = templateGenerator;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public DialogFlowResponse execute(DialogFlowRequest request) {
        Month month = month(request.getQueryResult().getParameters())
                .orElse(LocalDate.now().getMonth());

        List<Holiday> holidaysInMonth = getHolidays(month, Year.now());

        return DialogFlowResponse
                .builder()
                .fulfillmentText(getFulfillmentText(holidaysInMonth, month))
                .build();
    }

    private String getFulfillmentText(List<Holiday> holidaysInMonth, Month month) {

        Context context = new Context(Locale.ENGLISH);
        context.setVariable("month", month);
        context.setVariable("currentYear", Year.now().getValue());
        context.setVariable("holidays", holidaysInMonth);

        return templateGenerator.parse("ssml-templates/holidays-check.ssml", context);
    }

    private List<Holiday> getHolidays(Month month, Year year){
        Profile profile = getDefaultProfile();
        Locale locale = Locale.forLanguageTag(profile.getLocale());

        try{
            return holidays.getAllIn(locale, YearMonth.of(year.getValue(), month));

        }catch (HolidayException e){
            throw IntentClientException.holidaysNotSupported(e.getMessage(), locale.getCountry());
        }
    }

    private Profile getDefaultProfile() {
        return profileRepository.findOneByUsername(defaultProfile)
                .orElseThrow(() -> IntentException.profileLocationNotSet(defaultProfile));
    }

    public static class Parameters {
        private static final String MONTH_PARAMETER = "month";

        public static Optional<Month> month(Map<String, Object> params){
            return new SysDatePeriod().getValue(MONTH_PARAMETER, params)
                    .map(it -> it.getStartDate().getMonth());
        }
    }
}
