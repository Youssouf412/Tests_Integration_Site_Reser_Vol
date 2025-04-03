package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.Passenger;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerKeyConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private static final String PASSENGER_PASSPORT = "passengerPassport";
    private static final String PASSENGER_NAME = "passengerName";
    private static final Integer PASSENGER_AGE = 0;

    @InjectMocks
    private BookingService bookingService;
    @Mock
    private FlightPassengerService flightPassengerService;
    @Mock
    private Passenger firstClassPassenger;
    @Mock
    private Passenger businessClassPassenger;
    @Mock
    private Passenger economyClassPassenger;
    @Mock
    private PassengerService passengerService;
    @Mock
    private FlightInformation flightInformation;

    @Test
    void successfullyAddFirstClassPassengerDirectly() {
        // Given
        given(firstClassPassenger.getType()).willReturn(PassengerClass.FIRST_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(1);

        // When
        boolean isBooked = bookingService.isBooked(firstClassPassenger, flightInformation);

        // Then
        assertThat(isBooked).isTrue();
        verify(flightPassengerService, never()).numberOfBusinessClassSeatsAvailable();
        verify(flightPassengerService, never()).numberOfEconomyClassSeatsAvailable();
        verify(flightPassengerService).addPassenger(firstClassPassenger);
    }

    @Test
    void successfullyAddBusinessClassPassengerDirectly() {
        // Given
        given(businessClassPassenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(1);

        // When
        boolean isBooked = bookingService.isBooked(businessClassPassenger, flightInformation);

        // Then
        assertThat(isBooked).isTrue();
        verify(flightPassengerService, never()).numberOfFirstClassSeatsAvailable();
        verify(flightPassengerService, never()).numberOfEconomyClassSeatsAvailable();
        verify(flightPassengerService).addPassenger(businessClassPassenger);
    }

    @Test
    void successfullyAddEconomyClassPassengerDirectly() {
        // Given
        given(economyClassPassenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfEconomyClassSeatsAvailable()).willReturn(1);

        // When
        boolean isBooked = bookingService.isBooked(economyClassPassenger, flightInformation);

        // Then
        assertThat(isBooked).isTrue();
        verify(flightPassengerService, never()).numberOfFirstClassSeatsAvailable();
        verify(flightPassengerService, never()).numberOfBusinessClassSeatsAvailable();
        verify(flightPassengerService).addPassenger(economyClassPassenger);
    }

    @Test
    void tryFirstClassAndAddBusinessClassPassenger() {
        // Given
        given(firstClassPassenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(firstClassPassenger.getName()).willReturn(PASSENGER_NAME);
        given(firstClassPassenger.getType()).willReturn(PassengerClass.FIRST_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(0);
        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.BUSINESS_CLASS))).willReturn(businessClassPassenger);
        given(businessClassPassenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(1);

        // When
        boolean isBooked = bookingService.isBooked(firstClassPassenger, flightInformation);

        // Then
        assertThat(isBooked).isTrue();
        verify(flightPassengerService, never()).numberOfEconomyClassSeatsAvailable();
        verify(flightPassengerService).addPassenger(businessClassPassenger);
    }

    @Test
    void tryFirstClassAndThenBusinessClassAndFinallyAddEconomyPassenger() {
        // Given
        given(firstClassPassenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(firstClassPassenger.getName()).willReturn(PASSENGER_NAME);
        given(firstClassPassenger.getType()).willReturn(PassengerClass.FIRST_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(0);
        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.BUSINESS_CLASS))).willReturn(businessClassPassenger);
        given(businessClassPassenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(0);
        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.ECONOMY_CLASS))).willReturn(economyClassPassenger);
        given(flightPassengerService.numberOfEconomyClassSeatsAvailable()).willReturn(1);
        given(economyClassPassenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);

        // When
        boolean isBooked = bookingService.isBooked(firstClassPassenger, flightInformation);

        // Then
        assertThat(isBooked).isTrue();
        verify(flightPassengerService).addPassenger(economyClassPassenger);
    }

    @Test
    void tryFirstClassAndThenBusinessClassAndThenEconomyClassButFlightIsFull() {
        // Given
        given(firstClassPassenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(firstClassPassenger.getName()).willReturn(PASSENGER_NAME);
        given(firstClassPassenger.getType()).willReturn(PassengerClass.FIRST_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(0);
        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.BUSINESS_CLASS))).willReturn(businessClassPassenger);
        given(businessClassPassenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(0);
        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.ECONOMY_CLASS))).willReturn(economyClassPassenger);
        given(flightPassengerService.numberOfEconomyClassSeatsAvailable()).willReturn(0);
        given(economyClassPassenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);

        // When
        boolean isBooked = bookingService.isBooked(firstClassPassenger, flightInformation);

        // Then
        assertThat(isBooked).isFalse();
        verify(flightPassengerService, never()).addPassenger(economyClassPassenger);
    }

    @Test
    void addPassengerNeverCalled() {
        // Given
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(0);

        // When
        boolean isBooked = bookingService.isBooked(firstClassPassenger, flightInformation);

        // Then
        assertThat(isBooked).isFalse();
        verify(flightPassengerService, never()).numberOfFirstClassSeatsAvailable();
        verify(flightPassengerService, never()).numberOfBusinessClassSeatsAvailable();
        verify(flightPassengerService, never()).numberOfEconomyClassSeatsAvailable();
        verify(flightPassengerService, never()).addPassenger(firstClassPassenger);
    }

    private Map<PassengerKeyConstants, Object> getPassengerKeyConstantsObjectMap(final PassengerClass passengerClass) {
        Map<PassengerKeyConstants, Object> passengerDataMap = new HashMap<>();
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_PASSPORT, PASSENGER_PASSPORT);
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_NAME, PASSENGER_NAME);
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_AGE, PASSENGER_AGE);
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_CLASS, passengerClass);
        return passengerDataMap;
    }
}