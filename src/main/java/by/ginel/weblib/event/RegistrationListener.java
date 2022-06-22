package by.ginel.weblib.event;

import by.ginel.weblib.dto.PersonGetDto;
import by.ginel.weblib.service.api.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final PersonService personService;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        PersonGetDto user = event.getUser();
        String token = UUID.randomUUID().toString();
        personService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/regitrationConfirm?token=" + token;
        String message = "To finish registration process click link below.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("ginel_aj_19@mf.grsu.by");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "http://localhost:3306" + confirmationUrl);
        mailSender.send(email);
    }
}
