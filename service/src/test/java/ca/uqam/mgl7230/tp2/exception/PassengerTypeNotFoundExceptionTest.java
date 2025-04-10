package ca.uqam.mgl7230.tp2.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PassengerTypeNotFoundExceptionTest {

    @Test
    void testException() {
        // When
        PassengerTypeNotFoundException passengerTypeNotFoundException = new PassengerTypeNotFoundException("");

        // Then
        assertThat(passengerTypeNotFoundException).isInstanceOf(PassengerTypeNotFoundException.class);
    }

}