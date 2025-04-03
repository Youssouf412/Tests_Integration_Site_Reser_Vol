package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp2.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.Passenger;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp2.model.plane.PlaneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static ca.uqam.mgl7230.tp2.model.passenger.PassengerClass.DEFAULT;
import static ca.uqam.mgl7230.tp2.model.passenger.PassengerClass.FIRST_CLASS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FlightPassengerServiceTest {

    private static final  String FLIGHT_NUMBER = "flightNumber";
    private static final PlaneType PLANE_TYPE = PlaneType.BOEING;

    private FlightPassengerService flightPassengerService;
    @Mock
    private PlaneCatalog planeCatalog;
    @Mock
    private FlightCatalog flightCatalog;
    @Mock
    private FlightInformation flightInformation;
    @Mock
    private Passenger passenger;

    @BeforeEach
    void setup() {
        given(flightInformation.getPlaneType()).willReturn(PLANE_TYPE);
        given(flightCatalog.getFlightInformation(FLIGHT_NUMBER)).willReturn(flightInformation);
    }

    @Test
    void addFirstClassPassenger() {
        // Given
        given(passenger.getType()).willReturn(FIRST_CLASS);
        given(planeCatalog.getNumberSeatsFirstClass(PLANE_TYPE)).willReturn(2);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog);
        flightPassengerService.initializeFlightService(FLIGHT_NUMBER);

        // When
        flightPassengerService.addPassenger(passenger);
        int numberOfFirstClassSeatsAvailable = flightPassengerService.numberOfFirstClassSeatsAvailable();

        // Then
        assertThat(numberOfFirstClassSeatsAvailable).isEqualTo(1);
    }

    @Test
    void addBusinessClassPassenger() {
        // Given
        given(passenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);
        given(planeCatalog.getNumberSeatsBusinessClass(PLANE_TYPE)).willReturn(2);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog);
        flightPassengerService.initializeFlightService(FLIGHT_NUMBER);

        // When
        flightPassengerService.addPassenger(passenger);
        int numberOfBusinessClassSeatsAvailable = flightPassengerService.numberOfBusinessClassSeatsAvailable();

        // Then
        assertThat(numberOfBusinessClassSeatsAvailable).isEqualTo(1);
    }

    @Test
    void addEconomyClassPassenger() {
        // Given
        given(passenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);
        given(planeCatalog.getNumberSeatsEconomyClass(PLANE_TYPE)).willReturn(2);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog);
        flightPassengerService.initializeFlightService(FLIGHT_NUMBER);

        // When
        flightPassengerService.addPassenger(passenger);
        int numberOfEconomyClassSeatsAvailable = flightPassengerService.numberOfEconomyClassSeatsAvailable();

        // Then
        assertThat(numberOfEconomyClassSeatsAvailable).isEqualTo(1);
    }

    @Test
    void addFirstClassPassengerButIsFull() {
        // Given
        given(passenger.getType()).willReturn(FIRST_CLASS);
        given(planeCatalog.getNumberSeatsFirstClass(PLANE_TYPE)).willReturn(0);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog);
        flightPassengerService.initializeFlightService(FLIGHT_NUMBER);

        // When
        flightPassengerService.addPassenger(passenger);
        int numberOfFirstClassSeatsAvailable = flightPassengerService.numberOfFirstClassSeatsAvailable();

        // Then
        assertThat(numberOfFirstClassSeatsAvailable).isEqualTo(0);
    }

    @Test
    void addBusinessClassPassengerButIsFull() {
        // Given
        given(passenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);
        given(planeCatalog.getNumberSeatsBusinessClass(PLANE_TYPE)).willReturn(0);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog);
        flightPassengerService.initializeFlightService(FLIGHT_NUMBER);

        // When
        flightPassengerService.addPassenger(passenger);
        int numberOfBusinessClassSeatsAvailable = flightPassengerService.numberOfBusinessClassSeatsAvailable();

        // Then
        assertThat(numberOfBusinessClassSeatsAvailable).isEqualTo(0);
    }

    @Test
    void addEconomyClassPassengerButIsFull() {
        // Given
        given(passenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);
        given(planeCatalog.getNumberSeatsEconomyClass(PLANE_TYPE)).willReturn(0);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog);
        flightPassengerService.initializeFlightService(FLIGHT_NUMBER);

        // When
        flightPassengerService.addPassenger(passenger);
        int numberOfEconomyClassSeatsAvailable = flightPassengerService.numberOfEconomyClassSeatsAvailable();

        // Then
        assertThat(numberOfEconomyClassSeatsAvailable).isEqualTo(0);
    }

    @Test
    void numberOfTotalSeatsAvailable() {
        // Given
        given(passenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);
        given(planeCatalog.getNumberSeatsFirstClass(PLANE_TYPE)).willReturn(2);
        given(planeCatalog.getNumberSeatsBusinessClass(PLANE_TYPE)).willReturn(2);
        given(planeCatalog.getNumberSeatsEconomyClass(PLANE_TYPE)).willReturn(2);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog);
        flightPassengerService.initializeFlightService(FLIGHT_NUMBER);

        // When
        flightPassengerService.addPassenger(passenger);
        int numberOfTotalSeatsAvailable = flightPassengerService.numberOfTotalSeatsAvailable();

        // Then
        assertThat(numberOfTotalSeatsAvailable).isEqualTo(5);
    }

    @Test
    void defaultSwitchTest() {
        // Given
        given(passenger.getType()).willReturn(DEFAULT);
        given(planeCatalog.getNumberSeatsFirstClass(PLANE_TYPE)).willReturn(2);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog);
        flightPassengerService.initializeFlightService(FLIGHT_NUMBER);

        // When
        flightPassengerService.addPassenger(passenger);
        int numberOfFirstClassSeatsAvailable = flightPassengerService.numberOfFirstClassSeatsAvailable();

        // Then
        assertThat(numberOfFirstClassSeatsAvailable).isEqualTo(2);
    }

}