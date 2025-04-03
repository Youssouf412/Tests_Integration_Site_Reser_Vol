package ca.uqam.mgl7230.tp2.model.flight;

import ca.uqam.mgl7230.tp2.model.plane.PlaneType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FlightInformationTest {

    private static final String FLIGHT_NUMBER = "flightNumber";
    private static final Double LAT_SOURCE = 123.00;
    private static final Double LON_SOURCE = 123.00;
    private static final Double LAT_DESTINATION = 123.00;
    private static final Double LON_DESTINATION = 123.00;
    private static final PlaneType PLANE_TYPE = PlaneType.BOEING;

    @Test
    void testFlightInformationPojo() {
        // When
        FlightInformation flightInformation = new FlightInformation(FLIGHT_NUMBER,
                LAT_SOURCE, LON_SOURCE, LAT_DESTINATION, LON_DESTINATION, PLANE_TYPE);

        // Then
        assertThat(flightInformation.getFlightNumber()).isEqualTo(FLIGHT_NUMBER);
        assertThat(flightInformation.getLatSource()).isEqualTo(LAT_SOURCE);
        assertThat(flightInformation.getLonSource()).isEqualTo(LON_SOURCE);
        assertThat(flightInformation.getLatDestination()).isEqualTo(LAT_DESTINATION);
        assertThat(flightInformation.getLonDestination()).isEqualTo(LON_DESTINATION);
        assertThat(flightInformation.getPlaneType()).isEqualTo(PLANE_TYPE);
    }

}