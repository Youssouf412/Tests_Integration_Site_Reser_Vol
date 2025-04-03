package ca.uqam.mgl7230.tp2.adapter.flight;

import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;

public interface FlightCatalog {

    FlightInformation getFlightInformation(String flightNumber);
}
