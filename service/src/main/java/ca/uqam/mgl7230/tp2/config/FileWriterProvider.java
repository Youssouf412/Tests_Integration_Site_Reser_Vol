package ca.uqam.mgl7230.tp2.config;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterProvider {

    public FileWriter createFile(final String fileName) {
        try {
            return new FileWriter(fileName);
        } catch (IOException e) {
            System.out.println("Issue when creating file: " + e);
            return null;
        }
    }
}
