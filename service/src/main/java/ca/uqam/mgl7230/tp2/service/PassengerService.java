package ca.uqam.mgl7230.tp2.service;
import ca.uqam.mgl7230.tp2.exception.PassengerTypeNotFoundException;
import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.*;
import ca.uqam.mgl7230.tp2.utils.DistanceCalculator;

import java.util.Map;

public class PassengerService {

    private final DistanceCalculator distanceCalculator;

    public PassengerService(final DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public Passenger createPassenger(final FlightInformation flightInformation,
                                     final Map<PassengerKeyConstants, Object> passengerData) {

        String passport = (String) passengerData.get(PassengerKeyConstants.PASSENGER_PASSPORT);
        String name = (String) passengerData.get(PassengerKeyConstants.PASSENGER_NAME);
        int age = (int) passengerData.get(PassengerKeyConstants.PASSENGER_AGE);
        Object passengerClassObj = passengerData.get(PassengerKeyConstants.PASSENGER_CLASS);

        // Ajout et modification du code

        if (passport == null || passport.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger passport must not be empty.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name must not be empty.");
        }

        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Passenger age must be between 1 and 120 years.");
        }

        if (!(passengerClassObj instanceof PassengerClass passengerClass)) {
            throw new PassengerTypeNotFoundException("Passenger type is not valid: " + passengerClassObj);
        }

        // Ajout calculer les points
        Passenger basePassenger = switch (passengerClass) {
            case FIRST_CLASS -> new FirstClassPassenger(passport, name, age, 0);
            case BUSINESS_CLASS -> new BusinessClassPassenger(passport, name, age, 0);
            case ECONOMY_CLASS -> new EconomyClassPassenger(passport, name, age, 0);
        };

        int points = distanceCalculator.calculateWithMultiplier(flightInformation, basePassenger);

        return switch (passengerClass) {
            case FIRST_CLASS -> new FirstClassPassenger(passport, name, age, points);
            case BUSINESS_CLASS -> new BusinessClassPassenger(passport, name, age, points);
            case ECONOMY_CLASS -> new EconomyClassPassenger(passport, name, age, points);
        };
    }


}
