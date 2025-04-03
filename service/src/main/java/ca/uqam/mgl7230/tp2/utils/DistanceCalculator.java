package ca.uqam.mgl7230.tp2.utils;

import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;

public class DistanceCalculator {

    public static int calculateDistance(int baseDistance, String passengerClass) {
        switch (passengerClass) {
            case "FIRST_CLASS":
                return baseDistance * 2;  // Multiplicateur de 2 pour la première classe
            case "BUSINESS_CLASS":
                return baseDistance * 3 / 2;  // Multiplicateur de 1.5 pour la classe affaires
            case "ECONOMY_CLASS":
                return baseDistance;  // Pas de changement pour la classe économique
            default:
                throw new IllegalArgumentException("Classe inconnue: " + passengerClass);
        }
    }


    public int calculate(FlightInformation flightInformation) {
        double lat1 = Math.toRadians(flightInformation.getLatSource());
        double lon1 = Math.toRadians(flightInformation.getLonSource());
        double lat2 = Math.toRadians(flightInformation.getLatDestination());
        double lon2 = Math.toRadians(flightInformation.getLonDestination());

        return (int) (Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                        Math.cos(lat1) * Math.cos(lat2) *
                                Math.cos(lon2 - lon1)) * 6371);
    }
}
