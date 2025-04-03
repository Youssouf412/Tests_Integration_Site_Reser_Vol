package ca.uqam.mgl7230.tp2.model.flight;

import ca.uqam.mgl7230.tp2.model.plane.PlaneType;

public class FlightInformation {

    private final String flightNumber;
    private final Double latSource;
    private final Double lonSource;
    private final Double latDestination;
    private final Double lonDestination;
    private final PlaneType planeType;

    public FlightInformation(final String flightNumber,
                             final Double latSource,
                             final Double lonSource,
                             final Double latDestination,
                             final Double lonDestination,
                             final PlaneType planeType) {
        this.flightNumber = flightNumber;
        this.latSource = latSource;
        this.lonSource = lonSource;
        this.latDestination = latDestination;
        this.lonDestination = lonDestination;
        this.planeType = planeType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Double getLatSource() {
        return latSource;
    }

    public Double getLonSource() {
        return lonSource;
    }

    public Double getLatDestination() {
        return latDestination;
    }

    public Double getLonDestination() {
        return lonDestination;
    }

    public PlaneType getPlaneType() {
        return planeType;
    }
}
