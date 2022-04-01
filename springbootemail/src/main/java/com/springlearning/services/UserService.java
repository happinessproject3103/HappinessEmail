package com.springlearning.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.springlearning.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private List<User> userCache = null;

    public List<User> getAllUsers(){
        if(userCache != null){
            return userCache;
        }

        InputStream is = getClass().getClassLoader().getResourceAsStream("users.csv");

        try {
            try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
                List<String[]> r = reader.readAll();
                r = r.subList(1, r.size());
                return r.stream().map(values -> {

                    User newUser = User.builder()
                            .emailAddress(values[0])
                            .firstName(values[1])
                            .lastName(values[2])
                            .happiness(Double.parseDouble(values[3]))
                            .build();
                    newUser.getLikes().put("animals", Integer.parseInt(values[4]));
                    newUser.getLikes().put("quotes", Integer.parseInt(values[5]));
                    newUser.getLikes().put("jokes", Integer.parseInt(values[6]));
                    newUser.getLikes().put("nature", Integer.parseInt(values[7]));
                    newUser.getLikes().put("recipes", Integer.parseInt(values[8]));
                    newUser.getLikes().put("articles", 0);

                    return newUser;

                }).collect(Collectors.toList());

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public User getUser(String name){
        return getAllUsers().stream().filter(user -> user.getFirstName().equals(name) ).findFirst().get();
    }

    public User getUserByEmail(String email){
        return getAllUsers().stream().filter(user -> user.getEmailAddress().equals(email) ).findFirst().get();
    }
}
