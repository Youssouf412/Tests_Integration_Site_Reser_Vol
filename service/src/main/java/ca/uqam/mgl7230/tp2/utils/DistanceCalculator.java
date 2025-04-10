package ca.uqam.mgl7230.tp2.utils;

import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.Passenger;

public class DistanceCalculator {

    private static final int EARTH_RADIUS_KM = 6371;

    public int calculate(FlightInformation info) {
        double lat1Rad = toRadians(info.getLatSource());
        double lon1Rad = toRadians(info.getLonSource());
        double lat2Rad = toRadians(info.getLatDestination());
        double lon2Rad = toRadians(info.getLonDestination());

        return (int) computeGreatCircleDistance(lat1Rad, lon1Rad, lat2Rad, lon2Rad);
    }

    public int calculateWithMultiplier(FlightInformation info, Passenger passenger) {
        int baseDistance = calculate(info);
        double multiplier = resolveMultiplier(passenger);
        return applyMultiplier(baseDistance, multiplier);
    }

    private double computeGreatCircleDistance(double lat1, double lon1, double lat2, double lon2) {
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
        return angle * EARTH_RADIUS_KM;
    }

    private double resolveMultiplier(Passenger passenger) {
        switch (passenger.getType()) {
            case FIRST_CLASS:
                return 2.0;
            case BUSINESS_CLASS:
                return 1.5;
            case ECONOMY_CLASS:
                return 1.0;
            default:
                throw new IllegalArgumentException("Type de passager inconnu");
        }
    }

    private int applyMultiplier(int distance, double multiplier) {
        return (int) (distance * multiplier);
    }

    private double toRadians(double degree) {
        return Math.toRadians(degree);
    }
}
