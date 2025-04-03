package ca.uqam.mgl7230.tp2.adapter.flight;

import ca.uqam.mgl7230.tp2.adapter.flight.impl.FlightCatalogImpl;
import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.plane.PlaneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FlightCatalogImplTest {

    private Map<String, FlightInformation> flightInformationMap;

    @BeforeEach
    void setup() {
        flightInformationMap = new HashMap<>();
        flightInformationMap.put("UQAM001", new FlightInformation("UQAM001",
                45.508888, -73.561668, -23.533773, -46.625290,
                PlaneType.BOEING));
        flightInformationMap.put("UQAM002", new FlightInformation("UQAM002",
                45.508888, -73.561668, 35.652832, 139.839478,
                PlaneType.AIRBUS));
        flightInformationMap.put("UQAM003", new FlightInformation("UQAM003",
                45.508888, -73.561668, 48.864716, 2.349014,
                PlaneType.BOEING));
        flightInformationMap.put("UQAM004", new FlightInformation("UQAM004",
                45.508888, -73.561668, 30.033333, 31.233334,
                PlaneType.BOMBARDIER));
        flightInformationMap.put("UQAM005", new FlightInformation("UQAM005",
                45.508888, -73.561668, 31.628674, -7.992047,
                PlaneType.EMBRAER));
        flightInformationMap.put("UQAM006", new FlightInformation("UQAM006",
                45.508888, -73.561668, 41.015137, 28.979530,
                PlaneType.AIRBUS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"UQAM001", "UQAM002", "UQAM003", "UQAM004", "UQAM005", "UQAM006"})
    public void getFlightInformationTest(String flightNumber) {
        // Given
        FlightCatalogImpl flightCatalog = new FlightCatalogImpl();

        // When
        FlightInformation flightInformation = flightCatalog.getFlightInformation(flightNumber);

        // Then
        assertThat(flightInformation.getFlightNumber())
                .isEqualTo(flightInformationMap.get(flightNumber).getFlightNumber());
    }

}