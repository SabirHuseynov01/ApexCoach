package az.apexcoach.api.simulation;

public record TyreInfo(
        Double frontLeftPressure,
        Double frontRightPressure,
        Double rearLeftPressure,
        Double rearRightPressure,
        Double frontLeftTemperature,
        Double frontRightTemperature,
        Double rearLeftTemperature,
        Double rearRightTemperature,
        Double frontLeftWear,
        Double frontRightWear,
        Double rearLeftWear,
        Double rearRightWear
) {

    public static TyreInfo empty() {
        return new TyreInfo(
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
        );
    }
}