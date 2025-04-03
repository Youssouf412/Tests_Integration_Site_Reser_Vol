package ca.uqam.mgl7230.tp2.adapter.persist;

import ca.uqam.mgl7230.tp2.model.passenger.Passenger;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SavePassengerInFlightTest {

    private static final String FLIGHT_NUMBER = "flightNumber";
    private static final String PASSENGER_PASSPORT = "passengerPassport";
    private static final String PASSENGER_NAME = "passengerName";
    private static final Integer PASSENGER_AGE = 0;
    private static final PassengerClass PASSENGER_CLASS = PassengerClass.ECONOMY_CLASS;
    private static final Integer PASSENGER_MILLAGE = 0;
    private static final String FILE_NAME = "test.csv";

    @InjectMocks
    private SavePassengerInFlight savePassengerInFlight;
    @Mock
    private Passenger passenger;

    @AfterEach
    void tearDown() {
        File file = new File(FILE_NAME);
        boolean delete = file.delete();
        if(!delete) {
            fail();
        }
    }

    @Test
    void save() throws IOException {
        // Given
        FileWriter fileWriter = new FileWriter(FILE_NAME);
        given(passenger.getPassport()).willReturn(PASSENGER_PASSPORT);
        given(passenger.getName()).willReturn(PASSENGER_NAME);
        given(passenger.getAge()).willReturn(PASSENGER_AGE);
        given(passenger.getType()).willReturn(PASSENGER_CLASS);
        given(passenger.getMillagePoints()).willReturn(PASSENGER_MILLAGE);
        String expectedValue = "flightNumber,passengerPassport,passengerName,0,ECONOMY_CLASS,0";

        // When
        savePassengerInFlight.save(fileWriter, passenger, FLIGHT_NUMBER);
        fileWriter.close();

        // Then
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            StringBuilder actualValue = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                actualValue.append(line);
            }
            assertThat(actualValue).contains(expectedValue);
        } catch (IOException e) {
            fail();
        }
    }
}