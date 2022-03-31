package com.springlearning;

import com.springlearning.model.EMail;
import com.springlearning.services.MailSenderSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class EmailSender {
    @Autowired
    MailSenderSpring mailSenderSpring;

    @RequestMapping("/sendmail")
    public void sendMessage() {
        EMail email = new EMail();
        email.setTo("krishankantsinghal@gmail.com");
        email.setFrom("happinessproject@vistair.com");
        email.setSubject("Happiness Project");
        email.setContent("Sending mail");
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", "FirstName");
        model.put("lastName", "LastName");
        mailSenderSpring.sendEmailWithTemplate(email);
    }

}
