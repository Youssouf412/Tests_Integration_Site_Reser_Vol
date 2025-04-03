package ca.uqam.mgl7230.tp2.model.passenger;

public abstract class Passenger {

    private final String passport;
    private final String name;
    private final int age;
    private final int millagePoints;

    public Passenger(final String passport,
                     final String name,
                     final int age,
                     final int millagePoints) {
        this.passport = passport;
        this.name = name;
        this.age = age;
        this.millagePoints = millagePoints;
    }

    public abstract PassengerClass getType();

    public String getPassport() {
        return passport;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getMillagePoints() {
        return millagePoints;
    }
}
