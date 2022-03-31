package com.springlearning.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherDto {
    private double temperature;
    private double windSpeed;
    private double sunny;
}
