package com.springlearning.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final double initialHappiness;
}
