package ca.uqam.mgl7230.tp2.adapter.plane;

import ca.uqam.mgl7230.tp2.adapter.plane.impl.PlaneCatalogImpl;
import ca.uqam.mgl7230.tp2.model.plane.PlaneType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PlaneCatalogImplTest {

    @ParameterizedTest
    @CsvSource({"BOEING,2,5,12,19", "AIRBUS,1,1,9,11", "EMBRAER,0,1,4,5", "BOMBARDIER,0,2,5,7"})
    void getNumberOfTotalSeats(PlaneType planeType,
                               int numberOfFirstClass,
                               int numberOfBusinessClass,
                               int numberOfEconomyClass,
                               int numberOfTotalSeats) {
        // Given
        PlaneCatalogImpl planeCatalog = new PlaneCatalogImpl();

        // When
        int numberSeatsFirstClass = planeCatalog.getNumberSeatsFirstClass(planeType);
        int numberSeatsBusinessClass = planeCatalog.getNumberSeatsBusinessClass(planeType);
        int numberSeatsEconomyClass = planeCatalog.getNumberSeatsEconomyClass(planeType);
        int numberOfTotalSeatsAvailable = planeCatalog.getNumberOfTotalSeats(planeType);

        // Then
        assertThat(numberOfFirstClass).isEqualTo(numberSeatsFirstClass);
        assertThat(numberSeatsBusinessClass).isEqualTo(numberOfBusinessClass);
        assertThat(numberSeatsEconomyClass).isEqualTo(numberOfEconomyClass);
        assertThat(numberOfTotalSeats).isEqualTo(numberOfTotalSeatsAvailable);
    }
}