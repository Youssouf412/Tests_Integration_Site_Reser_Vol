package ca.uqam.mgl7230.tp2.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationInitializerTest {

    @Test
    void callInit() {
        // Given
        ApplicationInitializer applicationInitializer = new ApplicationInitializer();

        // When
        ApplicationInitializer.init();

        // Then
        assertThat(applicationInitializer).isNotNull();
        assertThat(ApplicationInitializer.flightPassengerService()).isNotNull();
        assertThat(ApplicationInitializer.flightPromptService()).isNotNull();
        assertThat(ApplicationInitializer.passengerPromptService()).isNotNull();
        assertThat(ApplicationInitializer.bookingService()).isNotNull();
        assertThat(ApplicationInitializer.passengerService()).isNotNull();
        assertThat(ApplicationInitializer.savePassengerInFlight()).isNotNull();
        assertThat(ApplicationInitializer.scanner()).isNotNull();
        assertThat(ApplicationInitializer.flightCatalog()).isNotNull();
        assertThat(ApplicationInitializer.fileWriterProvider()).isNotNull();
    }

}