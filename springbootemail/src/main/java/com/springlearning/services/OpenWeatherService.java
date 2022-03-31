package com.springlearning.services;

import com.springlearning.model.WeatherDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

@Service
public class OpenWeatherService implements WeatherService {

    @SneakyThrows
    @Override
    public WeatherDto getWeatherForecast(String location) {
        RestTemplate restTemplate = new RestTemplate();
        Map result = restTemplate.getForEntity(new URI("https://api.openweathermap.org/data/2.5/weather?lat=51.55046734768372&lon=-2.557908573703719&appid=a6fadb9aeaa412e5d25f1f50588d6960&units=metric"), Map.class).getBody();


        String sunny = ((Map) ((ArrayList) result.get("weather")).get(0)).get("main").toString();

        double sunnyNormalized = 0;
        switch (sunny) {
            case "Thunderstorm":
                sunnyNormalized = 0;
                break;
            case "Snow":
                sunnyNormalized = 0.2;
                break;
            case "Rain":
                sunnyNormalized = 0.4;
                break;
            case "Drizzle":
                sunnyNormalized = 0.6;
                break;
            case "Clouds":
                sunnyNormalized = 0.8;
                break;
            case "Clear":
                sunnyNormalized = 1;
                break;
            default:
                sunnyNormalized = 0;
        }

        return WeatherDto.builder().sunny(sunnyNormalized)
                .windSpeed(normalizeWindSpeed((Double) ((Map) result.get("wind")).get("speed")))
                .temperature(normalizeTemperature((Double) ((Map) result.get("main")).get("feels_like")))
                .build();
    }

    double normalizeWindSpeed(double windSpeedMs) {
        if (windSpeedMs < 1.5) {
            return 1;
        } else if (windSpeedMs < 3) {
            return 0.9;
        } else if (windSpeedMs < 3) {
            return 0.8;
        } else if (windSpeedMs < 5) {
            return 0.7;
        } else if (windSpeedMs < 8) {
            return 0.6;
        } else if (windSpeedMs < 10.5) {
            return 0.5;
        } else if (windSpeedMs < 13.5) {
            return 0.4;
        } else if (windSpeedMs < 16.5) {
            return 0.3;
        } else if (windSpeedMs < 20) {
            return 0.2;
        } else if (windSpeedMs < 23.5) {
            return 0.1;
        } else {
            return 0.0;
        }
    }

    double normalizeTemperature(double temperature) {
        double normalizedtTemperatur = temperature > 30 ? 30 : temperature;
        normalizedtTemperatur = normalizedtTemperatur < 0 ? 0 : normalizedtTemperatur;

        return normalizedtTemperatur / 30;
    }

}
