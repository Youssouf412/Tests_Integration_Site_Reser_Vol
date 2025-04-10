package ca.uqam.mgl7230.tp2.service.prompt;

import ca.uqam.mgl7230.tp2.exception.PassengerTypeNotFoundException;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp2.model.passenger.PassengerKeyConstants;

import java.util.Map;
import java.util.Scanner;

public class PassengerPromptService {

    public Map<PassengerKeyConstants, Object> getPassengerData(final Scanner scanner) {
        System.out.println("Enter Passenger passport: ");
        String passengerPassport = scanner.nextLine();
        System.out.println("Enter Passenger name: ");
        String passengerName = scanner.nextLine();
        System.out.println("Enter Passenger age: ");
        int passengerAge = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter a passenger type: write first for First Class, " +
                "business for Business Class or " +
                "economy for Economy Class");
        String passengerType = scanner.nextLine();

        PassengerClass passengerClass = getPassengerClass(passengerType);

        return Map.of(PassengerKeyConstants.PASSENGER_PASSPORT, passengerPassport,
                PassengerKeyConstants.PASSENGER_NAME, passengerName,
                PassengerKeyConstants.PASSENGER_AGE, passengerAge,
                PassengerKeyConstants.PASSENGER_CLASS, passengerClass);
    }

    private static PassengerClass getPassengerClass(final String passengerType) {
        switch (passengerType) {
            case "first" -> {
                return PassengerClass.FIRST_CLASS;
            }
            case "business" -> {
                return PassengerClass.BUSINESS_CLASS;
            }
            case "economy" -> {
                return PassengerClass.ECONOMY_CLASS;
            }
            default -> throw new PassengerTypeNotFoundException("");
        }
    }
}
