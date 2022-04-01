package com.springlearning;

import com.springlearning.model.EMail;
import com.springlearning.model.User;
import com.springlearning.services.MailSenderSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EmailSender {
    @Autowired
    MailSenderSpring mailSenderSpring;

    @RequestMapping("/sendmail")
    public void sendMessage() {
        EMail email = new EMail();
        email.setTo("projecthapiness5@gmail.com");
        email.setFrom("projecthapiness5@gmail.com");
        email.setSubject("Happiness Project");
        email.setContent("Sending mail");
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", "Happiness");
        model.put("lastName", "Prj");
        model.put("emailAddress", "projecthapiness5@gmail.com");
        email.setModel(model);
        mailSenderSpring.sendEmailWithTemplate(email);
    }

    public void sendMessage(User user) {
        EMail email = new EMail();
        email.setTo(user.getEmailAddress());
        email.setFrom("projecthapiness5@gmail.com");
        email.setSubject("Happiness Project");
        email.setContent("Sending mail");
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", user.getFirstName());
        model.put("lastName",  user.getLastName());
        model.put("user", user);
        email.setModel(model);
        mailSenderSpring.sendEmailWithTemplate(email);
    }

    @RequestMapping("/sendmail-attachment")
    public void sendMessageWithAttachment() throws IOException, MessagingException {
        EMail email = new EMail();
        email.setTo("projecthapiness5@gmail.com");
        email.setFrom("projecthapiness5@gmail.com");
        email.setSubject("Happiness Project");
        email.setContent("Sending mail");
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", "Happiness");
        model.put("lastName", "Prj");
        email.setModel(model);
        mailSenderSpring.sendEmailWithAttachment(email);
    }

}
