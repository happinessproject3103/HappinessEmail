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

    public List<User> getAllUsers(){
        InputStream is = getClass().getClassLoader().getResourceAsStream("users.csv");

        try {
            try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
                List<String[]> r = reader.readAll();
                r = r.subList(1, r.size());
                return r.stream().map(values -> User.builder()
                        .firstName(values[0])
                        .lastName(values[1])
                        .emailAddress(values[2])
                        .initialHappiness(Double.parseDouble(values[3]))
                        .build()).collect(Collectors.toList());
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
}
