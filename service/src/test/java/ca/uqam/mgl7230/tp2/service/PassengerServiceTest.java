package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.exception.PassengerTypeNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    private static final String PASSENGER_PASSPORT = "passengerPassport";
    private static final String PASSENGER_NAME = "passengerName";
    private static final Integer VALID_PASSENGER_AGE = 120;

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
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_AGE)).willReturn(VALID_PASSENGER_AGE);
    }

    @Test
    void createFirstClassPassenger_shouldReturnPassengerWithCorrectPoints() {
        // Given
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_CLASS))
                .willReturn(PassengerClass.FIRST_CLASS);
        given(distanceCalculator.calculateWithMultiplier(eq(flightInformation), any(FirstClassPassenger.class)))
                .willReturn(2000);

        // When
        Passenger passenger = passengerService.createPassenger(flightInformation, passengerKeyConstantsObjectMap);

        // Then
        assertThat(passenger).isInstanceOf(FirstClassPassenger.class);
        assertThat(passenger.getMillagePoints()).isEqualTo(2000);
    }

    @Test
    void createBusinessClassPassenger_shouldReturnPassengerWithCorrectPoints() {
        // Given
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_CLASS))
                .willReturn(PassengerClass.BUSINESS_CLASS);
        given(distanceCalculator.calculateWithMultiplier(eq(flightInformation), any(BusinessClassPassenger.class)))
                .willReturn(1500);

        // When
        Passenger passenger = passengerService.createPassenger(flightInformation, passengerKeyConstantsObjectMap);

        // Then
        assertThat(passenger).isInstanceOf(BusinessClassPassenger.class);
        assertThat(passenger.getMillagePoints()).isEqualTo(1500);
    }

    @Test
    void createEconomyClassPassenger_shouldReturnPassengerWithCorrectPoints() {
        // Given
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_CLASS))
                .willReturn(PassengerClass.ECONOMY_CLASS);
        given(distanceCalculator.calculateWithMultiplier(eq(flightInformation), any(EconomyClassPassenger.class)))
                .willReturn(1000);

        // When
        Passenger passenger = passengerService.createPassenger(flightInformation, passengerKeyConstantsObjectMap);

        // Then
        assertThat(passenger).isInstanceOf(EconomyClassPassenger.class);
        assertThat(passenger.getMillagePoints()).isEqualTo(1000);
    }

    @Test
    void createPassenger_shouldThrowExceptionIfPassengerClassInvalid() {
        // Given
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_AGE)).willReturn(30);
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_NAME)).willReturn("Bob");
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_PASSPORT)).willReturn("ABC123");
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_CLASS))
                .willReturn("INVALID_CLASS"); // <-- Ceci va permettre de déclenchera l'exception

        // When / Then
        assertThatThrownBy(() ->
                passengerService.createPassenger(flightInformation, passengerKeyConstantsObjectMap)
        )
                .isInstanceOf(PassengerTypeNotFoundException.class)
                .hasMessageContaining("Passenger type not valid");
    }


    @Test
    void createPassenger_shouldThrowExceptionIfAgeIsInvalid() {
        // Given
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_AGE)).willReturn(1); // âge invalide
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_NAME)).willReturn("Alice");
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_PASSPORT)).willReturn("XYZ123");
        given(passengerKeyConstantsObjectMap.get(PassengerKeyConstants.PASSENGER_CLASS)).willReturn(PassengerClass.ECONOMY_CLASS);

        // When / Then
        assertThatThrownBy(() ->
                passengerService.createPassenger(flightInformation, passengerKeyConstantsObjectMap)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("age must be between");
    }

}