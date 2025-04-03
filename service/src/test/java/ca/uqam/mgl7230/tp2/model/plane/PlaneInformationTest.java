package ca.uqam.mgl7230.tp2.model.plane;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlaneInformationTest {

    private static final PlaneType PLANE_TYPE = PlaneType.BOEING;
    private static final int SEATS_FIRST_CLASS = 1;
    private static final int SEATS_BUSINESS_CLASS = 2;
    private static final int SEATS_ECONOMY_CLASS = 3;

    @Test
    void planeInformationPojoTest() {
        // When
        PlaneInformation planeInformation = new PlaneInformation(PLANE_TYPE, SEATS_FIRST_CLASS, SEATS_BUSINESS_CLASS, SEATS_ECONOMY_CLASS);

        // Then
        assertThat(planeInformation.getPlaneType()).isEqualTo(PLANE_TYPE);
        assertThat(planeInformation.getNumberSeatsFirstClass()).isEqualTo(SEATS_FIRST_CLASS);
        assertThat(planeInformation.getNumberSeatsBusinessClass()).isEqualTo(SEATS_BUSINESS_CLASS);
        assertThat(planeInformation.getNumberSeatsEconomyClass()).isEqualTo(SEATS_ECONOMY_CLASS);
    }

}