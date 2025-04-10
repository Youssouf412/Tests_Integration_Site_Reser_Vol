package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.Passenger;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerKeyConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookingService {

    private final FlightPassengerService flightPassengerService;
    private final PassengerService passengerService;

    public BookingService(final FlightPassengerService flightPassengerService,
                          final PassengerService passengerService) {
        this.flightPassengerService = flightPassengerService;
        this.passengerService = passengerService;
    }

    // Ajout et Modification du code

    public Optional<Passenger> isBooked(Passenger passenger, final FlightInformation flightInformation) {
        if (passenger == null) {
            return Optional.empty();
        }

        Map<PassengerKeyConstants, Object> passengerDataMap = getPassengerKeyConstantsObjectMap(passenger);
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_CLASS, passenger.getType()); // important !

        if (flightPassengerService.numberOfTotalSeatsAvailable() == 0) {
            System.out.println("Flight is Full...");
            return Optional.empty();
        }

        if (PassengerClass.FIRST_CLASS.equals(passenger.getType()) &&
                flightPassengerService.numberOfFirstClassSeatsAvailable() == 0) {
            System.out.println("First Class is Full. Trying Business...");
            passengerDataMap.put(PassengerKeyConstants.PASSENGER_CLASS, PassengerClass.BUSINESS_CLASS);
            passenger = passengerService.createPassenger(flightInformation, passengerDataMap);
        }

        if (PassengerClass.BUSINESS_CLASS.equals(passenger.getType()) &&
                flightPassengerService.numberOfBusinessClassSeatsAvailable() == 0) {
            System.out.println("Business Class is Full. Trying Economy...");
            passengerDataMap.put(PassengerKeyConstants.PASSENGER_CLASS, PassengerClass.ECONOMY_CLASS);
            passenger = passengerService.createPassenger(flightInformation, passengerDataMap);
        }

        if (PassengerClass.ECONOMY_CLASS.equals(passenger.getType()) &&
                flightPassengerService.numberOfEconomyClassSeatsAvailable() == 0) {
            System.out.println("Economy is Full. Try another type...");
            return Optional.empty();
        }

        if (passenger == null) {
            System.out.println("Passenger creation failed (validation failed).");
            return Optional.empty();
        }

        flightPassengerService.addPassenger(passenger);
        System.out.println("Passenger added successfully");
        return Optional.of(passenger);
    }

    private Map<PassengerKeyConstants, Object> getPassengerKeyConstantsObjectMap(final Passenger passenger) {
        Map<PassengerKeyConstants, Object> passengerDataMap = new HashMap<>();
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_PASSPORT, passenger.getPassport());
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_NAME, passenger.getName());
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_AGE, passenger.getAge());
        return passengerDataMap;
    }
}
