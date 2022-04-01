package com.springlearning.services;

import com.springlearning.model.User;
import com.springlearning.model.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimatedHappinessService {

    @Autowired
    OpenWeatherService weatherService;

    @Autowired
    OutlookMockBusynessService busynessService;

    public double getUserHappiness(User user){
        WeatherDto weatherDto = weatherService.getWeatherForecast("bristol");
        double busyness = busynessService.getHoursInMeetings();

        return  weatherDto.getSunny() * 0.3  + weatherDto.getTemperature() * 0.2 + weatherDto.getWindSpeed() * 0.1 + busyness * 0.4 ;
    }
}
