package com.springlearning;

public enum HappinessMeasure {
    _1(0, 0.1),
    _2(0.1, 0.2),
    _3(0.2, 0.3),
    _4(0.3, 0.4),
    _5(0.4, 0.5),
    _6(0.5, 0.6),
    _7(0.6,0.7),
    _8(0.7,0.8),
    _9(0.8,0.9),
    _10(0.9,1.0);

    private final double normalizedLowerThresholdIncl;
    private final double normalizedUpperThresholdExcl;

    HappinessMeasure(double lowerThreshold, double upperThreshold) {
        this.normalizedLowerThresholdIncl = lowerThreshold;
        this.normalizedUpperThresholdExcl = upperThreshold;

    }
}
