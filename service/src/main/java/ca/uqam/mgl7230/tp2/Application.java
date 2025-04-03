package ca.uqam.mgl7230.tp2;

import static ca.uqam.mgl7230.tp2.config.ApplicationInitializer.init;
import static ca.uqam.mgl7230.tp2.service.ExecuteService.execute;

public class Application {

    public static void main(String[] args) {
        System.out.println("Service started...");
        init();
        execute();
    }
}
