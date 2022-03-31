package com.springlearning.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OutlookMockBusynessService implements BusynessService {

    @Override
    public double getHoursInMeetings() {
        Random random = new Random();
        return random.nextDouble();
    }
}
