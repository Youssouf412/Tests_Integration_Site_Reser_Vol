package ca.uqam.mgl7230.tp2.service.prompt;

import ca.uqam.mgl7230.tp2.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;

import java.util.Scanner;

public class FlightPromptService {

    private final FlightCatalog flightCatalog;

    public FlightPromptService(final FlightCatalog flightCatalog) {
        this.flightCatalog = flightCatalog;
    }

    public FlightInformation getFlightInformation(final Scanner scanner) {
        System.out.println("Enter flight number: ");
        String flightNumber = scanner.nextLine();
        FlightInformation flight = flightCatalog.getFlightInformation(flightNumber);
        if (flight == null) {
            System.out.println("No flight found with this code, try again...");
        }
        return flight;
    }
}
