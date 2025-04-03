package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.*;
import ca.uqam.mgl7230.tp2.utils.DistanceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    private static final String PASSENGER_PASSPORT = "passengerPassport";
    private static final String PASSENGER_NAME = "passengerName";
    private static final Integer PASSENGER_AGE = 0;

    @InjectMocks
    private PassengerService passengerService;
    @Mock
    private DistanceCalculator distanceCalculator;
    @Mock
    private FlightInformation flightInformation;
    @Mock
    private Map<PassengerKeyConstants, Object> passengerKeyConstantsObjectMap;

    @BeforeEach
    void setup() {
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_PASSPORT)).willReturn(PASSENGER_PASSPORT);
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_NAME)).willReturn(PASSENGER_NAME);
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_AGE)).willReturn(PASSENGER_AGE);
    }

    @Test
    void createFirstClassPassenger() {
        // Given
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_CLASS)).willReturn(PassengerClass.FIRST_CLASS);
        given(distanceCalculator.calculate(flightInformation)).willReturn(0);

        // When
        Passenger passenger = passengerService.createPassenger(flightInformation, passengerKeyConstantsObjectMap);

        // Then
        assertThat(passenger).isInstanceOf(FirstClassPassenger.class);
    }

    @Test
    void createBusinessClassPassenger() {
        // Given
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_CLASS)).willReturn(PassengerClass.BUSINESS_CLASS);
        given(distanceCalculator.calculate(flightInformation)).willReturn(0);

        // When
        Passenger passenger = passengerService.createPassenger(flightInformation, passengerKeyConstantsObjectMap);

        // Then
        assertThat(passenger).isInstanceOf(BusinessClassPassenger.class);
    }

    @Test
    void createEconomyClassPassenger() {
        // Given
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_CLASS)).willReturn(PassengerClass.ECONOMY_CLASS);
        given(distanceCalculator.calculate(flightInformation)).willReturn(0);

        // When
        Passenger passenger = passengerService.createPassenger(flightInformation, passengerKeyConstantsObjectMap);

        // Then
        assertThat(passenger).isInstanceOf(EconomyClassPassenger.class);
    }

    @Test
    void createPassengerIsNull() {
        // Given
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_CLASS)).willReturn(mock(PassengerClass.class));

        // When
        Passenger passenger = passengerService.createPassenger(flightInformation, passengerKeyConstantsObjectMap);

        // Then
        assertThat(passenger).isNull();
    }

}