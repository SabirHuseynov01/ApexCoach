package az.apexcoach.api.simulation;

public record WeatherInfo(
        String condition,
        Double airTemperatureCelsius,
        Double trackTemperatureCelsius
) {}
