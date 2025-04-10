package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.exception.PassengerTypeNotFoundException;
import ca.uqam.mgl7230.tp2.model.passenger.Passenger;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerKeyConstants;
import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static ca.uqam.mgl7230.tp2.config.ApplicationInitializer.*;

public class ExecuteService {

    //Modification du code et ajout

    public static void execute() {
        try {
            FileWriter fileWriter = fileWriterProvider().createFile("passengerData.csv");
            fileWriter.flush();

            FlightInformation flightInformation = flightPromptService().getFlightInformation(scanner());
            if (flightInformation == null) {
                System.out.println("Flight not found. Please retry.");
                return;
            }

            String flightNumber = flightInformation.getFlightNumber();
            flightPassengerService().initializeFlightService(flightNumber);

            boolean shouldContinue = true;
            while (shouldContinue) {
                try {
                    Map<PassengerKeyConstants, Object> passengerData =
                            passengerPromptService().getPassengerData(scanner());

                    Passenger passenger = passengerService().createPassenger(flightInformation, passengerData);

                    Optional<Passenger> maybeBooked = bookingService().isBooked(passenger, flightInformation);

                    if (maybeBooked.isPresent()) {
                        savePassengerInFlight().save(fileWriter, maybeBooked.get(), flightNumber);
                        System.out.println("Passenger saved to CSV.");
                    } else {
                        System.out.println("Passenger booking failed.");
                    }
                } catch (PassengerTypeNotFoundException | IllegalArgumentException e) {
                    System.out.println("Passenger creation failed: " + e.getMessage());
                }
                System.out.println("Seats available: " + flightPassengerService().numberOfTotalSeatsAvailable());
                System.out.println("Continue adding passengers to this flight? yes or no");
                String continueChoice = scanner().nextLine();
                if ("no".equalsIgnoreCase(continueChoice)) {
                    shouldContinue = false;
                }
            }


            scanner().close();
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error manipulating file: " + e.getMessage());
        }
    }
}