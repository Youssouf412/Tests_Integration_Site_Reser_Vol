package ca.uqam.mgl7230.tp2.model.passenger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PassengerTest {

    private static final String PASSENGER_PASSPORT = "passengerPassport";
    private static final String PASSENGER_NAME = "passengerName";
    private static final Integer PASSENGER_AGE = 0;
    private static final Integer PASSENGER_MILLAGE = 0;

    @Test
    void getPassengerInfo() {
        // When
        Passenger passenger = new FirstClassPassenger(PASSENGER_PASSPORT, PASSENGER_NAME, PASSENGER_AGE, PASSENGER_MILLAGE);

        // Then
        assertThat(passenger.getPassport()).isEqualTo(PASSENGER_PASSPORT);
        assertThat(passenger.getName()).isEqualTo(PASSENGER_NAME);
        assertThat(passenger.getAge()).isEqualTo(PASSENGER_AGE);
        assertThat(passenger.getMillagePoints()).isEqualTo(PASSENGER_MILLAGE);
    }

    @Test
    void getTypeFirstClassPassenger() {
        // Given
        Passenger passenger = new FirstClassPassenger(PASSENGER_PASSPORT, PASSENGER_NAME, PASSENGER_AGE, PASSENGER_MILLAGE);
        PassengerClass expectedPassengerClass = PassengerClass.FIRST_CLASS;

        // When
        PassengerClass actualPassengerClass = passenger.getType();

        // Then
        assertThat(actualPassengerClass).isEqualTo(expectedPassengerClass);
    }

    @Test
    void getTypeBusinessClassPassenger() {
        // Given
        Passenger passenger = new BusinessClassPassenger(PASSENGER_PASSPORT, PASSENGER_NAME, PASSENGER_AGE, PASSENGER_MILLAGE);
        PassengerClass expectedPassengerClass = PassengerClass.BUSINESS_CLASS;

        // When
        PassengerClass actualPassengerClass = passenger.getType();

        // Then
        assertThat(actualPassengerClass).isEqualTo(expectedPassengerClass);
    }

    @Test
    void getTypeEconomyClassPassenger() {
        // Given
        Passenger passenger = new EconomyClassPassenger(PASSENGER_PASSPORT, PASSENGER_NAME, PASSENGER_AGE, PASSENGER_MILLAGE);
        PassengerClass expectedPassengerClass = PassengerClass.ECONOMY_CLASS;

        // When
        PassengerClass actualPassengerClass = passenger.getType();

        // Then
        assertThat(actualPassengerClass).isEqualTo(expectedPassengerClass);
    }
}