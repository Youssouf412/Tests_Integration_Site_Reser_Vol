package ca.uqam.mgl7230.tp2.service.prompt;

import ca.uqam.mgl7230.tp2.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FlightPromptServiceTest {

    private static final String FLIGHT_NUMBER = "flightNumber";
    @InjectMocks
    private FlightPromptService flightPromptService;
    @Mock
    private FlightCatalog flightCatalog;
    @Mock
    private FlightInformation expectedFlightInformation;
    @Mock
    private Scanner scanner;

    @Test
    void getFlightInformationReturnsFlightInformation() {
        // Given
        given(flightCatalog.getFlightInformation(FLIGHT_NUMBER)).willReturn(expectedFlightInformation);
        given(scanner.nextLine()).willReturn(FLIGHT_NUMBER);

        // When
        FlightInformation actualFlightInformation = flightPromptService.getFlightInformation(scanner);

        // Then
        assertThat(actualFlightInformation).isEqualTo(expectedFlightInformation);
    }

    @Test
    void getFlightInformationReturnsNull() {
        // Given
        given(flightCatalog.getFlightInformation(FLIGHT_NUMBER)).willReturn(null);
        given(scanner.nextLine()).willReturn(FLIGHT_NUMBER);

        // When
        FlightInformation actualFlightInformation = flightPromptService.getFlightInformation(scanner);

        // Then
        assertThat(actualFlightInformation).isNull();
    }

}