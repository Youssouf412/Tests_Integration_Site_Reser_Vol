package ca.uqam.mgl7230.tp2.service.prompt;

import ca.uqam.mgl7230.tp2.exception.PassengerTypeNotFoundException;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerKeyConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PassengerPromptServiceTest {

    private static final String PASSENGER_PASSPORT = "passengerPassport";
    private static final String PASSENGER_NAME = "passengerName";
    private static final Integer PASSENGER_AGE = 0;

    @InjectMocks
    private PassengerPromptService passengerPromptService;
    @Mock
    private Scanner scanner;

    @Test
    void getPassengerDataForFirstClass() {
        // Given
        given(scanner.nextLine())
                .willReturn(PASSENGER_PASSPORT)
                .willReturn(PASSENGER_NAME)
                .willReturn(String.valueOf(PASSENGER_AGE))
                .willReturn("first");

        // When
        Map<PassengerKeyConstants, Object> passengerData = passengerPromptService.getPassengerData(scanner);

        // Then
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_PASSPORT)).isEqualTo(PASSENGER_PASSPORT);
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_NAME)).isEqualTo(PASSENGER_NAME);
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_AGE)).isEqualTo(PASSENGER_AGE);
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_CLASS)).isEqualTo(PassengerClass.FIRST_CLASS);
    }

    @Test
    void getPassengerDataForBusinessClass() {
        // Given
        given(scanner.nextLine())
                .willReturn(PASSENGER_PASSPORT)
                .willReturn(PASSENGER_NAME)
                .willReturn(String.valueOf(PASSENGER_AGE))
                .willReturn("business");

        // When
        Map<PassengerKeyConstants, Object> passengerData = passengerPromptService.getPassengerData(scanner);

        // Then
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_PASSPORT)).isEqualTo(PASSENGER_PASSPORT);
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_NAME)).isEqualTo(PASSENGER_NAME);
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_AGE)).isEqualTo(PASSENGER_AGE);
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_CLASS)).isEqualTo(PassengerClass.BUSINESS_CLASS);
    }

    @Test
    void getPassengerDataForEconomyClass() {
        // Given
        given(scanner.nextLine())
                .willReturn(PASSENGER_PASSPORT)
                .willReturn(PASSENGER_NAME)
                .willReturn(String.valueOf(PASSENGER_AGE))
                .willReturn("economy");

        // When
        Map<PassengerKeyConstants, Object> passengerData = passengerPromptService.getPassengerData(scanner);

        // Then
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_PASSPORT)).isEqualTo(PASSENGER_PASSPORT);
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_NAME)).isEqualTo(PASSENGER_NAME);
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_AGE)).isEqualTo(PASSENGER_AGE);
        assertThat(passengerData.get(PassengerKeyConstants.PASSENGER_CLASS)).isEqualTo(PassengerClass.ECONOMY_CLASS);
    }

    @Test
    void getPassengerDataThrowsException() {
        // Given
        given(scanner.nextLine())
                .willReturn(PASSENGER_PASSPORT)
                .willReturn(PASSENGER_NAME)
                .willReturn(String.valueOf(PASSENGER_AGE))
                .willReturn("error");

        // Then
        assertThrows(PassengerTypeNotFoundException.class, () -> passengerPromptService.getPassengerData(scanner));
    }

}