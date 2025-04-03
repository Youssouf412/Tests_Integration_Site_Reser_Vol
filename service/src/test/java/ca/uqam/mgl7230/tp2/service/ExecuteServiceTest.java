package ca.uqam.mgl7230.tp2.service;

import ca.uqam.mgl7230.tp2.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp2.adapter.persist.SavePassengerInFlight;
import ca.uqam.mgl7230.tp2.config.ApplicationInitializer;
import ca.uqam.mgl7230.tp2.config.FileWriterProvider;
import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.Passenger;
import ca.uqam.mgl7230.tp2.service.prompt.FlightPromptService;
import ca.uqam.mgl7230.tp2.service.prompt.PassengerPromptService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExecuteServiceTest {

    private static final String FLIGHT_NUMBER = "flightNumber";
    private MockedStatic<ApplicationInitializer> initializeMock;

    @Mock
    private FileWriterProvider fileWriterProvider;
    @Mock
    private FlightPromptService flightPromptService;
    @Mock
    private FlightPassengerService flightPassengerService;
    @Mock
    private PassengerPromptService passengerPromptService;
    @Mock
    private PassengerService passengerService;
    @Mock
    private FlightCatalog flightCatalog;
    @Mock
    private SavePassengerInFlight savePassengerInFlight;
    @Mock
    private BookingService bookingService;
    @Mock
    private Scanner scanner;
    @Mock
    private FileWriter fileWriter;
    @Mock
    private FlightInformation flightInformation;
    @Mock
    private Passenger passenger;

    @BeforeEach
    void setup() throws IOException {
        // instantiation just to complete code coverage
        ExecuteService executeService = new ExecuteService();
        assertThat(executeService).isNotNull();
        given(fileWriterProvider.createFile("passengerData.csv")).willReturn(fileWriter);
        doNothing().when(fileWriter).flush();
        given(flightPromptService.getFlightInformation(scanner)).willReturn(flightInformation);
        given(flightInformation.getFlightNumber()).willReturn(FLIGHT_NUMBER);
        doNothing().when(flightPassengerService).initializeFlightService(FLIGHT_NUMBER);
        Map passengerData = mock(Map.class);
        given(passengerPromptService.getPassengerData(scanner)).willReturn(passengerData);
        given(passengerService.createPassenger(flightInformation, passengerData)).willReturn(passenger);
        given(flightCatalog.getFlightInformation(FLIGHT_NUMBER)).willReturn(flightInformation);
        given(bookingService.isBooked(passenger, flightInformation)).willReturn(true);

        initializeMock = mockStatic(ApplicationInitializer.class);
        initializeMock.when(ApplicationInitializer::fileWriterProvider).thenReturn(fileWriterProvider);
        initializeMock.when(ApplicationInitializer::flightPromptService).thenReturn(flightPromptService);
        initializeMock.when(ApplicationInitializer::flightPassengerService).thenReturn(flightPassengerService);
        initializeMock.when(ApplicationInitializer::passengerPromptService).thenReturn(passengerPromptService);
        initializeMock.when(ApplicationInitializer::passengerService).thenReturn(passengerService);
        initializeMock.when(ApplicationInitializer::flightCatalog).thenReturn(flightCatalog);
        initializeMock.when(ApplicationInitializer::bookingService).thenReturn(bookingService);
        initializeMock.when(ApplicationInitializer::savePassengerInFlight).thenReturn(savePassengerInFlight);
        initializeMock.when(ApplicationInitializer::scanner).thenReturn(scanner);
    }

    @AfterEach
    void tearDown() {
        initializeMock.close();
    }

    @Test
    void answerNoLeaveTheLoop() throws IOException {
        // Given
        doNothing().when(savePassengerInFlight).save(fileWriter, passenger, FLIGHT_NUMBER);
        given(scanner.nextLine()).willReturn("no");
        doNothing().when(scanner).close();
        doNothing().when(fileWriter).close();

        // When
        ExecuteService.execute();

        // Then
        verify(fileWriter).close();
        verify(scanner).close();
    }

    @Test
    void answerYesOnceAndNoToLeaveTheLoop() throws IOException {
        // Given
        doNothing().when(savePassengerInFlight).save(fileWriter, passenger, FLIGHT_NUMBER);
        given(scanner.nextLine()).willReturn("yes").willReturn("no");
        doNothing().when(scanner).close();
        doNothing().when(fileWriter).close();

        // When
        ExecuteService.execute();

        // Then
        verify(fileWriter).close();
        verify(scanner).close();
    }

    @Test
    void doesNotSavePassengerWhenIsBookedFalse() throws IOException {
        // Given
        given(bookingService.isBooked(passenger, flightInformation)).willReturn(false);
        given(scanner.nextLine()).willReturn("yes");
        doNothing().when(scanner).close();
        doNothing().when(fileWriter).close();

        // When
        ExecuteService.execute();

        // Then
        verify(savePassengerInFlight, never()).save(fileWriter, passenger, FLIGHT_NUMBER);
        verify(fileWriter).close();
        verify(scanner).close();
    }

    @Test
    void throwException() throws IOException {
        // Given
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        given(scanner.nextLine()).willReturn("no");
        doNothing().when(scanner).close();
        doThrow(IOException.class).when(fileWriter).close();
        System.setOut(new PrintStream(outputStreamCaptor));

        // When
        ExecuteService.execute();

        // Then
        assertThat(outputStreamCaptor.toString()).contains("Error manipulating file");
    }
}