package com.springlearning.services;

import com.springlearning.model.WeatherDto;
import org.springframework.stereotype.Service;

@Service
public class MockWeatherService implements WeatherService {
    @Override
    public WeatherDto getWeatherForecast(String location) {
        return WeatherDto.builder().temperature(20).windSpeed(30).sunny(0.6).build();
    }
}
