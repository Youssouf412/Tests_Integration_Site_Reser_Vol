package ca.uqam.mgl7230.tp2.utils;

import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.Passenger;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp2.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DistanceCalculatorTest {


    @InjectMocks
    private DistanceCalculator distanceCalculator;
    @Mock
    private FlightInformation flightInformation;
    @Mock
    private Passenger passenger;




    @Test
    void getDistanceReturnRealDistance() {
        // Given
        given(flightInformation.getLatSource()).willReturn(123.00);
        given(flightInformation.getLonSource()).willReturn(123.00);
        given(flightInformation.getLatDestination()).willReturn(123.00);
        given(flightInformation.getLonDestination()).willReturn(123.00);

        // When
        double actualDistance = distanceCalculator.calculate(flightInformation);

        // Then
        assertThat(actualDistance).isEqualTo(0.0);
    }

    @Test
    void getDistanceReturnDistanceFirstClass_NewData() {
        // Given
        given(flightInformation.getLatSource()).willReturn(40.7128); // New York
        given(flightInformation.getLonSource()).willReturn(-74.0060);
        given(flightInformation.getLatDestination()).willReturn(34.0522); // Los Angeles
        given(flightInformation.getLonDestination()).willReturn(-118.2437);

        given(passenger.getType()).willReturn(PassengerClass.FIRST_CLASS);

        // When
        int distance = distanceCalculator.calculate(flightInformation);
        int distanceWithMultiplier = distanceCalculator.calculateWithMultiplier(flightInformation, passenger);

        // Then
        assertThat(distanceWithMultiplier).isEqualTo((int)(distance * 2.0));
    }

    @Test
    void getDistanceReturnDistanceBusinessClass_NewData() {
        // Given
        given(flightInformation.getLatSource()).willReturn(40.7128); // New York
        given(flightInformation.getLonSource()).willReturn(-74.0060);
        given(flightInformation.getLatDestination()).willReturn(34.0522); // Los Angeles
        given(flightInformation.getLonDestination()).willReturn(-118.2437);

        given(passenger.getType()).willReturn(PassengerClass.BUSINESS_CLASS);

        // When
        int distance = distanceCalculator.calculate(flightInformation);
        int distanceWithMultiplier = distanceCalculator.calculateWithMultiplier(flightInformation, passenger);

        // Then
        assertThat(distanceWithMultiplier).isEqualTo((int)(distance * 1.5));
    }

    @Test
    void getDistanceReturnDistanceEconomyClass_NewData() {
        // Given
        given(flightInformation.getLatSource()).willReturn(40.7128); // New York
        given(flightInformation.getLonSource()).willReturn(-74.0060);
        given(flightInformation.getLatDestination()).willReturn(34.0522); // Los Angeles
        given(flightInformation.getLonDestination()).willReturn(-118.2437);

        given(passenger.getType()).willReturn(PassengerClass.ECONOMY_CLASS);

        // When
        int distance = distanceCalculator.calculate(flightInformation);
        int distanceWithMultiplier = distanceCalculator.calculateWithMultiplier(flightInformation, passenger);

        // Then
        assertThat(distanceWithMultiplier).isEqualTo((int)(distance * 1.0));
    }

}
