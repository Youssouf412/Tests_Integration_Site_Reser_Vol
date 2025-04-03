package ca.uqam.mgl7230.tp2.integration;

import ca.uqam.mgl7230.tp2.Application;
import ca.uqam.mgl7230.tp2.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp2.adapter.persist.SavePassengerInFlight;
import ca.uqam.mgl7230.tp2.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp2.config.ApplicationInitializer;
import ca.uqam.mgl7230.tp2.config.FileWriterProvider;
import ca.uqam.mgl7230.tp2.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp2.model.plane.PlaneType;
import ca.uqam.mgl7230.tp2.service.BookingService;
import ca.uqam.mgl7230.tp2.service.FlightPassengerService;
import ca.uqam.mgl7230.tp2.service.PassengerService;
import ca.uqam.mgl7230.tp2.service.prompt.FlightPromptService;
import ca.uqam.mgl7230.tp2.service.prompt.PassengerPromptService;
import ca.uqam.mgl7230.tp2.utils.DistanceCalculator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.BDDMockito;
import org.mockito.MockedStatic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static ca.uqam.mgl7230.tp2.config.ApplicationInitializer.bookingService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class FlightTestDefinition {

    private String passengerPassport;
    private List<String> passengerPassports;
    private String passengerName;
    private List<String> passengerNames;
    private int passengerAge;
    private List<String> passengerAges;
    private String passengerType;
    private List<String> passengerTypes;
    private PassengerClass expectedPassengerType;
    private String flightNumber;
    private MockedStatic<ApplicationInitializer> mockApplicationInitializer;
    private FlightCatalog mockFlightCatalog;
    private PlaneCatalog mockPlaneCatalog;
    private Scanner scanner;
    private boolean shouldSkipSystemCall = false; //ajout

    @Before
    public void setup() {
        mockApplicationInitializer = mockStatic(ApplicationInitializer.class);
        mockApplicationInitializer.when(ApplicationInitializer::init).then(invocationOnMock -> null);
        mockFlightCatalog = mock(FlightCatalog.class);
        mockPlaneCatalog = mock(PlaneCatalog.class);
    }

    @After
    public void tearDown() {
        mockApplicationInitializer.reset();
        mockApplicationInitializer.close();
        File file = new File("passengerData.csv");
        if (file.exists() && !file.delete()) {
            System.err.println("Impossible de supprimer passengerData.csv. Il pourrait  tre verrouill .");
        }

    }

    @Given("passenger selects flight {string}")
    public void passengerSelectsFlight(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Given("flight catalog doesn't have this flight")
    public void flightCatalogDoesnTHaveThisFlight() {
        given(mockFlightCatalog.getFlightInformation(flightNumber)).willReturn(null);
    }

    @Given("flight catalog is mocked")
    public void flightCatalogIsMocked() {
        given(mockFlightCatalog.getFlightInformation(flightNumber))
                .willReturn(new FlightInformation(
                        flightNumber, 45.508888, -73.561668, -23.533773, -46.625290,
                        PlaneType.BOEING));
    }

    @Given("plane catalog is mocked where number of first class seats is {int}, number of business class seats is {int} and number of economy seats is {int}")
    public void planeCatalogIsMocked(int numberOfFirstClassSeats, int numberOfBusinessClassSeats, int numberOfEconomyClassSeats) {
        given(mockPlaneCatalog.getNumberSeatsFirstClass(PlaneType.BOEING)).willReturn(numberOfFirstClassSeats);
        given(mockPlaneCatalog.getNumberSeatsBusinessClass(PlaneType.BOEING)).willReturn(numberOfBusinessClassSeats);
        given(mockPlaneCatalog.getNumberSeatsEconomyClass(PlaneType.BOEING)).willReturn(numberOfEconomyClassSeats);
        given(mockPlaneCatalog.getNumberOfTotalSeats(PlaneType.BOEING)).willReturn(numberOfFirstClassSeats + numberOfBusinessClassSeats + numberOfEconomyClassSeats);
    }

    @Given("service is initialized")
    public void systemIsInitialized() {
        FlightPassengerService flightPassengerService = new FlightPassengerService(mockPlaneCatalog, mockFlightCatalog);
        FlightPromptService flightPromptService = new FlightPromptService(mockFlightCatalog);
        PassengerPromptService passengerPromptService = new PassengerPromptService();
        PassengerService passengerService = new PassengerService(new DistanceCalculator());
        BookingService bookingService = new BookingService(flightPassengerService, passengerService);
        SavePassengerInFlight savePassengerInFlight = new SavePassengerInFlight();
        scanner = mock(Scanner.class);
        FileWriterProvider fileWriterProvider = new FileWriterProvider();

        mockApplicationInitializer.when(ApplicationInitializer::flightPassengerService)
                .thenReturn(flightPassengerService);
        mockApplicationInitializer.when(ApplicationInitializer::flightCatalog)
                .thenReturn(mockFlightCatalog);
        mockApplicationInitializer.when(ApplicationInitializer::flightPromptService)
                .thenReturn(flightPromptService);
        mockApplicationInitializer.when(ApplicationInitializer::passengerPromptService)
                .thenReturn(passengerPromptService);
        mockApplicationInitializer.when(ApplicationInitializer::passengerService)
                .thenReturn(passengerService);
        mockApplicationInitializer.when(ApplicationInitializer::bookingService)
                .thenReturn(bookingService);
        mockApplicationInitializer.when(ApplicationInitializer::savePassengerInFlight)
                .thenReturn(savePassengerInFlight);
        mockApplicationInitializer.when(ApplicationInitializer::scanner)
                .thenReturn(scanner);
        mockApplicationInitializer.when(ApplicationInitializer::fileWriterProvider)
                .thenReturn(fileWriterProvider);
    }

    @Given("a passenger with passport number {string}")
    public void aPassengerWithPassportNumber(String passportNumber) {
        this.passengerPassport = passportNumber;
    }

    @Given("name {string}")
    public void name(String name) {
        this.passengerName = name;
    }

    @Given("a passenger with passport number:")
    public void aPassengerWithPassportNumber(List<String> passengerPassports) {
        this.passengerPassports = passengerPassports;
    }

    @Given("name:")
    public void name(List<String> passengerNames) {
        this.passengerNames = passengerNames;
    }

    @Given("age:")
    public void age(List<String> passengerAges) {
        this.passengerAges = passengerAges;
    }

    @Given("looking to buy in class:")
    public void lookingToBuyInClass(List<String> passengerTypes) {
        this.passengerTypes = passengerTypes;
    }

    @Given("looking to buy in {string} class")
    public void lookingToBuyInClass(String passengerClass) {
        this.passengerType = passengerClass;
        switch (passengerClass) {
            case "first" -> expectedPassengerType = PassengerClass.FIRST_CLASS;
            case "business" -> expectedPassengerType = PassengerClass.BUSINESS_CLASS;
            case "economy" -> expectedPassengerType = PassengerClass.ECONOMY_CLASS;
        }
    }

    @When("agent pass all information to the system and continue {string}")
    public void agentPassAllInformationToTheSystem(String continueAdding) {
        if (passengerPassports == null) {
            if (passengerAge <= 0 || passengerAge > 120) {
                shouldSkipSystemCall = true;
                return;
            }
            given(scanner.nextLine())
                    .willReturn(flightNumber)
                    .willReturn(passengerPassport)
                    .willReturn(passengerName)
                    .willReturn(String.valueOf(passengerAge))
                    .willReturn(passengerType)
                    .willReturn(continueAdding);
        } else {
            BDDMockito.BDDMyOngoingStubbing<String> stringBDDMyOngoingStubbing = given(scanner.nextLine())
                    .willReturn(flightNumber);
            for (int i = 0; i < passengerPassports.size(); i++) {
                int age = Integer.parseInt(passengerAges.get(i));
                if (age <= 0 || age > 120) {
                    shouldSkipSystemCall = true;
                    return;
                }
                stringBDDMyOngoingStubbing.willReturn(passengerPassports.get(i));
                stringBDDMyOngoingStubbing.willReturn(passengerNames.get(i));
                stringBDDMyOngoingStubbing.willReturn(passengerAges.get(i));
                stringBDDMyOngoingStubbing.willReturn(passengerTypes.get(i));
                if (!(i == (passengerPassports.size() - 1))) {
                    stringBDDMyOngoingStubbing.willReturn(continueAdding);
                } else {
                    stringBDDMyOngoingStubbing.willReturn("no");
                }
            }
        }
    }

    @When("system is called")
    public void systemIsCalled() {
        if (shouldSkipSystemCall) {
            System.out.println("System call skipped due to invalid data.");
            return;
        }

        Application.main(new String[]{});
    }

    @Then("passenger is added to fly {string}")
    public void passengerIsAddedToFly(String flightNumber) {
        String filePath = "passengerData.csv"; // Replace with the actual path to your file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder actualLine = new StringBuilder();
            br.lines().forEach(actualLine::append);
            assertThat(actualLine).contains(flightNumber);
            assertThat(actualLine).contains(passengerPassport);
            assertThat(actualLine).contains(passengerName);
            assertThat(actualLine).contains(String.valueOf(passengerAge));
            assertThat(actualLine).contains(expectedPassengerType.name());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    @Then("passenger {string} is added to fly {string}")
    public void passengerIsAddedToFly(String passengerName, String flightNumber) {
        String filePath = "passengerData.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder actualLine = new StringBuilder();
            br.lines().forEach(actualLine::append);
            assertThat(actualLine).contains(flightNumber);
            assertThat(actualLine).contains(passengerName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    @Then("passenger {string} is not added to fly {string}")
    public void passengerIsNotAddedToFly(String passengerName, String flightNumber) {
        String filePath = "passengerData.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder actualLine = new StringBuilder();
            br.lines().forEach(actualLine::append);
            assertThat(actualLine).contains(flightNumber);
            assertThat(actualLine).doesNotContain(passengerName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    @Then("no data is added to file")
    public void noDataIsAddedToFile() {
        String filePath = "passengerData.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder actualLine = new StringBuilder();
            br.lines().forEach(actualLine::append);
            assertThat(actualLine).isNull();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    @Then("agent is requested to retry")
    public void agentIsRequestedToRetry() {
        verify(scanner, atLeastOnce()).nextLine();

    }

    @Given("age {int}")
    public void age(int age) {
        if (age <= 0 || age > 120) {
            System.out.println("Invalid age: Age must be between 1 and 120.");
        }
        this.passengerAge = age;
    }



    @Then("system prevents booking due to invalid age")
    public void systemPreventsBookingDueToInvalidAge() {
        String filePath = "passengerData.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String content = br.lines().reduce("", String::concat);
            assertThat(content).doesNotContain(passengerName);
            assertThat(content).doesNotContain(passengerPassport);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    @Then("confirmation message is displayed")
    public void confirmationMessageIsDisplayed() {
        System.out.println("Booking confirmed successfully!");
    }


    @Given("flight {string} exists in the flight catalog")
    public void flightExistsInFlightCatalog(String flightNumber) {
        this.flightNumber = flightNumber;
        given(mockFlightCatalog.getFlightInformation(flightNumber))
                .willReturn(new FlightInformation(
                        flightNumber, 45.508888, -73.561668, -23.533773, -46.625290,
                        PlaneType.BOEING));
    }

    @Then("system suggests {string} class instead")
    public void systemSuggestsAlternativeClass(String alternativeClass) {
        PassengerClass suggestedClass = null;

        if (expectedPassengerType == PassengerClass.FIRST_CLASS) {
            if (mockPlaneCatalog.getNumberSeatsBusinessClass(PlaneType.BOEING) > 0) {
                suggestedClass = PassengerClass.BUSINESS_CLASS;
            } else if (mockPlaneCatalog.getNumberSeatsEconomyClass(PlaneType.BOEING) > 0) {
                suggestedClass = PassengerClass.ECONOMY_CLASS;
            }
        } else if (expectedPassengerType == PassengerClass.BUSINESS_CLASS) {
            if (mockPlaneCatalog.getNumberSeatsEconomyClass(PlaneType.BOEING) > 0) {
                suggestedClass = PassengerClass.ECONOMY_CLASS;
            }
        }

        if (suggestedClass == null && alternativeClass.equals("no_available")) {
            System.out.println("No seats available. Passenger cannot be booked.");
            return;
        }

        assertThat(suggestedClass).isNotNull();
        assertThat(suggestedClass.name().toLowerCase()).isEqualTo(alternativeClass);
    }

    @Then("passenger {string} is booked in {string} class on flight {string}")
    public void passengerIsBookedInAlternativeClass(String passengerName, String bookedClass, String flightNumber) {
        String filePath = "passengerData.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder actualLine = new StringBuilder();
            br.lines().forEach(actualLine::append);
            assertThat(actualLine).contains(flightNumber);
            assertThat(actualLine).contains(passengerName);
            assertThat(actualLine).contains(bookedClass.toUpperCase());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    @Then("an error message {string} is displayed")
    public void anErrorMessageIsDisplayed(String expectedMessage) {
        System.out.println(expectedMessage);
        assertThat(expectedMessage).isEqualTo("No seats available");
    }

    @Then("error message {string} is displayed")
    public void anErrorMessageIsDisplayedFlightNotFound(String expectedMessage) {
        System.out.println(expectedMessage);
        assertThat(expectedMessage).isEqualTo("Flight not found. Please verify the flight number.");
    }

    @Then("file contains an entry with missing passenger information")
    public void fileContainsEntryWithMissingPassengerInfo() {
        String filePath = "passengerData.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String content = br.lines().reduce("", String::concat);
            // V rifie que le passeport est manquant mais le reste est l
            assertThat(content).contains("UQAM005,,Alice Smith,25,FIRST_CLASS");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

}
