package ca.uqam.mgl7230.tp2.model.passenger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PassengerKeyConstantsTest {

    @Test
    void testPassengerKeyConstantsClassEnum() {
        // Then
        assertThat(PassengerKeyConstants.PASSENGER_PASSPORT.name()).isEqualTo("PASSENGER_PASSPORT");
        assertThat(PassengerKeyConstants.PASSENGER_NAME.name()).isEqualTo("PASSENGER_NAME");
        assertThat(PassengerKeyConstants.PASSENGER_AGE.name()).isEqualTo("PASSENGER_AGE");
        assertThat(PassengerKeyConstants.PASSENGER_CLASS.name()).isEqualTo("PASSENGER_CLASS");
    }

}