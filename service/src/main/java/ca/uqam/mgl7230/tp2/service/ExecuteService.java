package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.model.passenger.Passenger;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerKeyConstants;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static ca.uqam.mgl7230.tp2.config.ApplicationInitializer.*;

public class ExecuteService {

    public static void execute() {
        try {
            FileWriter fileWriter = fileWriterProvider().createFile("passengerData.csv");
            fileWriter.flush();
            String flightNumber = flightPromptService().getFlightInformation(scanner()).getFlightNumber();
            flightPassengerService().initializeFlightService(flightNumber);
            boolean shouldContinue = true;
            while (shouldContinue) {
                Map<PassengerKeyConstants, Object> passengerData =
                        passengerPromptService().getPassengerData(scanner());
                Passenger passenger = passengerService().createPassenger(
                        flightCatalog().getFlightInformation(flightNumber), passengerData);
                boolean isBooked = bookingService().isBooked(passenger, flightCatalog().getFlightInformation(flightNumber));
                if (isBooked) {
                    savePassengerInFlight().save(fileWriter, passenger, flightNumber);
                }
                System.out.println("Seats available: " + flightPassengerService().numberOfTotalSeatsAvailable());
                System.out.println("Continue adding passengers to this flight? yes or no");
                String continueChoice = scanner().nextLine();
                if (!isBooked || "no".equalsIgnoreCase(continueChoice)) {
                    shouldContinue = false;
                    scanner().close();
                    fileWriter.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Error manipulating file: " + e.getMessage());
        }
    }
}
