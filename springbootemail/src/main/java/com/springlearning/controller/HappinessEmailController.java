package com.springlearning.controller;


import com.springlearning.EmailSender;
import com.springlearning.model.User;
import com.springlearning.services.EstimatedHappinessService;
import com.springlearning.services.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HappinessEmailController {

    @Autowired
    UserService userService;

    @Autowired
    EstimatedHappinessService estimatedHappinessService;

    @Autowired
    EmailSender emailSender;

    @GetMapping("/start/{firstName}")
    public void checkHappiness(@PathVariable String firstName){
        User user = userService.getUser(firstName);
        double totalHappiness = estimatedHappinessService.getUserHappiness(user);
        if(totalHappiness < 0.8){
            emailSender.sendMessage(user);
        }
    }

    @GetMapping(value = "/updateHappiness")
    public void updateHappiness(@RequestParam @NonNull String emailAddress, @RequestParam @NonNull double happiness) {
        User user = userService.getUserByEmail(emailAddress);
//        user.builder().initialHappiness(happiness).build();
        //TODO update the user in question's happiness
    }
}
