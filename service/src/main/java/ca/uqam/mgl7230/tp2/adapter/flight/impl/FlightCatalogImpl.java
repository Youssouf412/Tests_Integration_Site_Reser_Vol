package ca.uqam.mgl7230.tp2.adapter.flight.impl;

import ca.uqam.mgl7230.tp2.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.plane.PlaneType;

import java.util.HashMap;
import java.util.Map;

public class FlightCatalogImpl implements FlightCatalog {

    private final Map<String, FlightInformation> flightInformationMap = new HashMap<>();

    public FlightCatalogImpl() {
        flightInformationMap.put("UQAM001", new FlightInformation(
                "UQAM001", 45.508888, -73.561668, -23.533773, -46.625290,
                        PlaneType.BOEING));
        flightInformationMap.put("UQAM002", new FlightInformation(
                "UQAM002", 45.508888, -73.561668, 35.652832, 139.839478,
                PlaneType.AIRBUS));
        flightInformationMap.put("UQAM003", new FlightInformation(
                "UQAM003", 45.508888, -73.561668, 48.864716, 2.349014,
                PlaneType.BOEING));
        flightInformationMap.put("UQAM004", new FlightInformation(
                "UQAM004", 45.508888, -73.561668, 30.033333, 31.233334,
                PlaneType.BOMBARDIER));
        flightInformationMap.put("UQAM005", new FlightInformation(
                "UQAM005", 45.508888, -73.561668, 31.628674, -7.992047,
                PlaneType.EMBRAER));
        flightInformationMap.put("UQAM006", new FlightInformation(
                "UQAM006", 45.508888, -73.561668, 41.015137, 28.979530,
                PlaneType.AIRBUS));
    }

    @Override
    public FlightInformation getFlightInformation(final String flightNumber) {
        return flightInformationMap.get(flightNumber);
    }
}
