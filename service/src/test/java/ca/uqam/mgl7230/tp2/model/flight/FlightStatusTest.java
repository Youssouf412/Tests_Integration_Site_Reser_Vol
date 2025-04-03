package ca.uqam.mgl7230.tp2.model.flight;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightStatusTest {

    @Test
    void enumFlightStatusTest() {
        // Then
        assertThat(FlightStatus.FULL.name()).isEqualTo("FULL");
        assertThat(FlightStatus.CANCELED.name()).isEqualTo("CANCELED");
        assertThat(FlightStatus.OPEN.name()).isEqualTo("OPEN");
    }
}
