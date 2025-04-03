package ca.uqam.mgl7230.tp2.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class FileWriterProviderTest {

    private static final String FILE_NAME = "test.csv";

    @InjectMocks
    private FileWriterProvider fileWriterProvider;

    @Test
    void createFileReturnsFile() {
        // When
        FileWriter fileWriter = fileWriterProvider.createFile(FILE_NAME);

        // Then
        assertThat(fileWriter).isNotNull();
        File file = new File(FILE_NAME);
        boolean delete = file.delete();
        if(!delete) {
            fail();
        }
    }

    @Test
    void createFileReturnsNull() {
        // When
        FileWriter fileWriter = fileWriterProvider.createFile("..");

        // Then
        assertThat(fileWriter).isNull();
    }
}
