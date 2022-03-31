package com.springlearning.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class User {
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final double initialHappiness;
}
