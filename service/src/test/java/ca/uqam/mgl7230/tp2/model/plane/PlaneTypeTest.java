package ca.uqam.mgl7230.tp2.model.plane;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlaneTypeTest {

    @Test
    void testPlaneTypeEnum() {
        // Then
        assertThat(PlaneType.BOEING.name()).isEqualTo("BOEING");
        assertThat(PlaneType.AIRBUS.name()).isEqualTo("AIRBUS");
        assertThat(PlaneType.EMBRAER.name()).isEqualTo("EMBRAER");
        assertThat(PlaneType.BOMBARDIER.name()).isEqualTo("BOMBARDIER");

    }

}