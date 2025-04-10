package ca.uqam.mgl7230.tp2.model.passenger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PassengerClassTest {

    @Test
    void testPassengerClassEnum() {
        // Then
        assertThat(PassengerClass.FIRST_CLASS.name()).isEqualTo("FIRST_CLASS");
        assertThat(PassengerClass.BUSINESS_CLASS.name()).isEqualTo("BUSINESS_CLASS");
        assertThat(PassengerClass.ECONOMY_CLASS.name()).isEqualTo("ECONOMY_CLASS");
    }

}