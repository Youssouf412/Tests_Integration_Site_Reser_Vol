package ca.uqam.mgl7230.tp2.utils;

import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DistanceCalculatorTest {

    @InjectMocks
    private DistanceCalculator distanceCalculator;
    @Mock
    private FlightInformation flightInformation;

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
    void testCalculateDistanceWithMultiplier() {
        // Cas de test avec différents types de passagers
        assertEquals(2000, DistanceCalculator.calculateDistance(1000, "FIRST_CLASS"));
        assertEquals(1500, DistanceCalculator.calculateDistance(1000, "BUSINESS_CLASS"));
        assertEquals(1000, DistanceCalculator.calculateDistance(1000, "ECONOMY_CLASS"));}
}
