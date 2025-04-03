package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp2.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp2.model.passenger.Passenger;

public class FlightPassengerService {

    private final PlaneCatalog planeCatalog;
    private final FlightCatalog flightCatalog;

    private int totalAmountFirstClassSeats;
    private int totalAmountBusinessClassSeats;
    private int totalAmountEconomyClassSeats;

    public FlightPassengerService(final PlaneCatalog planeCatalog,
                                  final FlightCatalog flightCatalog) {
        this.planeCatalog = planeCatalog;
        this.flightCatalog = flightCatalog;
    }

    public void initializeFlightService(String flightNumber) {
        this.totalAmountFirstClassSeats = planeCatalog.getNumberSeatsFirstClass(
                flightCatalog.getFlightInformation(flightNumber).getPlaneType());
        this.totalAmountBusinessClassSeats = planeCatalog.getNumberSeatsBusinessClass(
                flightCatalog.getFlightInformation(flightNumber).getPlaneType());
        this.totalAmountEconomyClassSeats = planeCatalog.getNumberSeatsEconomyClass(
                flightCatalog.getFlightInformation(flightNumber).getPlaneType());
    }

    public void addPassenger(Passenger passenger) {
        switch (passenger.getType()) {
            case FIRST_CLASS -> {
                if (totalAmountFirstClassSeats != 0) {
                    totalAmountFirstClassSeats = totalAmountFirstClassSeats - 1;
                }
            }
            case BUSINESS_CLASS -> {
                if (totalAmountBusinessClassSeats != 0) {
                    totalAmountBusinessClassSeats = totalAmountBusinessClassSeats - 1;
                }
            }
            default -> {
                if (totalAmountEconomyClassSeats != 0) {
                    totalAmountEconomyClassSeats = totalAmountEconomyClassSeats - 1;
                }
            }
        }
    }

    public int numberOfFirstClassSeatsAvailable() {
        return totalAmountFirstClassSeats;
    }

    public int numberOfBusinessClassSeatsAvailable() {
        return totalAmountBusinessClassSeats;
    }

    public int numberOfEconomyClassSeatsAvailable() {
        return totalAmountEconomyClassSeats;
    }

    public int numberOfTotalSeatsAvailable() {
        return totalAmountFirstClassSeats + totalAmountBusinessClassSeats + totalAmountEconomyClassSeats;
    }

}
