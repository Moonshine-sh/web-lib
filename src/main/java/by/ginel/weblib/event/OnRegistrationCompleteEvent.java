package by.ginel.weblib.event;

import by.ginel.weblib.dto.PersonGetDto;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final PersonGetDto user;

    public OnRegistrationCompleteEvent(PersonGetDto user, Locale locale, String appUrl) {
        super(user);

        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public PersonGetDto getUser() {
        return user;
    }
}
