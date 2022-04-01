package com.springlearning.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class User {
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private double happiness;
    private final Map<String, Integer> likes = new HashMap<>();
}
