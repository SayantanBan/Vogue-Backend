package tk.sayantan.Vogue.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import tk.sayantan.Vogue.model.User;


@Component
public class MailConstructor {

    @Autowired
    private Environment env;

    @Autowired
    private TemplateEngine templateEngine;

    public SimpleMailMessage constructNewUserEmail(User user, String password) {
        String message = "\nPlease use the following credentials to log in and edit your personal information including your own password."
                + "\nUsername:" + user.getEmail() + "\nPassword:" + password;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Vogue - New User");
        email.setText(message);
        email.setFrom("vogueblogofficial@gmail.com");
        return email;
    }
}

