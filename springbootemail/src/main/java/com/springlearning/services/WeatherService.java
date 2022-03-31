package com.springlearning.services;


import com.springlearning.model.WeatherDto;

public interface WeatherService {

    WeatherDto getWeatherForecast( String location );
}
