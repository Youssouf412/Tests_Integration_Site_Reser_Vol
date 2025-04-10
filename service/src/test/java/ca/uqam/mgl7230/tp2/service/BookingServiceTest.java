package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        given(firstClassPassenger.getType()).willReturn(PassengerClass.FIRST_CLASS);
        given(firstClassPassenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(firstClassPassenger.getName()).willReturn(PASSENGER_NAME);
        given(firstClassPassenger.getAge()).willReturn(PASSENGER_AGE);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(1);

        Optional<Passenger> result = bookingService.isBooked(firstClassPassenger, flightInformation);

        assertThat(result).isPresent().contains(firstClassPassenger);
        verify(flightPassengerService).addPassenger(firstClassPassenger);
    }

    @Test
    void successfullyAddBusinessClassPassengerDirectly() {
        given(businessClassPassenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);
        given(businessClassPassenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(businessClassPassenger.getName()).willReturn(PASSENGER_NAME);
        given(businessClassPassenger.getAge()).willReturn(PASSENGER_AGE);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(1);

        Optional<Passenger> result = bookingService.isBooked(businessClassPassenger, flightInformation);

        assertThat(result).isPresent().contains(businessClassPassenger);
        verify(flightPassengerService).addPassenger(businessClassPassenger);
    }

    @Test
    void successfullyAddEconomyClassPassengerDirectly() {
        given(economyClassPassenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);
        given(economyClassPassenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(economyClassPassenger.getName()).willReturn(PASSENGER_NAME);
        given(economyClassPassenger.getAge()).willReturn(PASSENGER_AGE);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfEconomyClassSeatsAvailable()).willReturn(1);

        Optional<Passenger> result = bookingService.isBooked(economyClassPassenger, flightInformation);

        assertThat(result).isPresent().contains(economyClassPassenger);
        verify(flightPassengerService).addPassenger(economyClassPassenger);
    }

    @Test
    void tryFirstClassAndAddBusinessClassPassenger() {
        given(firstClassPassenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(firstClassPassenger.getName()).willReturn(PASSENGER_NAME);
        given(firstClassPassenger.getAge()).willReturn(PASSENGER_AGE);
        given(firstClassPassenger.getType()).willReturn(PassengerClass.FIRST_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(0);
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(1);
        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.BUSINESS_CLASS)))
                .willReturn(businessClassPassenger);
        given(businessClassPassenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);

        Optional<Passenger> result = bookingService.isBooked(firstClassPassenger, flightInformation);

        assertThat(result).isPresent().contains(businessClassPassenger);
        verify(flightPassengerService).addPassenger(businessClassPassenger);
    }

    @Test
    void tryFirstClassAndThenBusinessClassAndFinallyAddEconomyPassenger() {
        given(firstClassPassenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(firstClassPassenger.getName()).willReturn(PASSENGER_NAME);
        given(firstClassPassenger.getAge()).willReturn(PASSENGER_AGE);
        given(firstClassPassenger.getType()).willReturn(PassengerClass.FIRST_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(0);
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(0);
        given(flightPassengerService.numberOfEconomyClassSeatsAvailable()).willReturn(1);

        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.BUSINESS_CLASS)))
                .willReturn(businessClassPassenger);
        given(businessClassPassenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);

        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.ECONOMY_CLASS)))
                .willReturn(economyClassPassenger);
        given(economyClassPassenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);

        Optional<Passenger> result = bookingService.isBooked(firstClassPassenger, flightInformation);

        assertThat(result).isPresent().contains(economyClassPassenger);
        verify(flightPassengerService).addPassenger(economyClassPassenger);
    }

    @Test
    void tryAllClassesButFlightIsFull() {
        given(firstClassPassenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(firstClassPassenger.getName()).willReturn(PASSENGER_NAME);
        given(firstClassPassenger.getAge()).willReturn(PASSENGER_AGE);
        given(firstClassPassenger.getType()).willReturn(PassengerClass.FIRST_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(1);
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(0);
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(0);
        given(flightPassengerService.numberOfEconomyClassSeatsAvailable()).willReturn(0);

        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.BUSINESS_CLASS)))
                .willReturn(businessClassPassenger);
        given(businessClassPassenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);

        given(passengerService.createPassenger(flightInformation,
                getPassengerKeyConstantsObjectMap(PassengerClass.ECONOMY_CLASS)))
                .willReturn(economyClassPassenger);
        given(economyClassPassenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);

        Optional<Passenger> result = bookingService.isBooked(firstClassPassenger, flightInformation);

        assertThat(result).isEmpty();
        verify(flightPassengerService, never()).addPassenger(any());
    }

    @Test
    void flightIsCompletelyFull() {
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(0);

        Optional<Passenger> result = bookingService.isBooked(firstClassPassenger, flightInformation);

        assertThat(result).isEmpty();
        verify(flightPassengerService, never()).addPassenger(any());
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
