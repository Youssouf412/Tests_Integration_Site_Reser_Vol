package ca.uqam.mgl7230.tp2.model.passenger;

public class FirstClassPassenger extends Passenger {

    public FirstClassPassenger(final String passport,
                               final String name,
                               final int age,
                               final int millagePoints) {
        super(passport, name, age, millagePoints);
    }

    @Override
    public PassengerClass getType() {
        return PassengerClass.FIRST_CLASS;
    }
}
