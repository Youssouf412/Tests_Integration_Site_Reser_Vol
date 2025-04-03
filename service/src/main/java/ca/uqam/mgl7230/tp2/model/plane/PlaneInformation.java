package ca.uqam.mgl7230.tp2.model.plane;

public class PlaneInformation {

    private final int numberSeatsFirstClass;
    private final int numberSeatsBusinessClass;
    private final int numberSeatsEconomyClass;
    private final PlaneType planeType;

    public PlaneInformation(final PlaneType planeType,
                            final int numberSeatsFirstClass,
                            final int numberSeatsBusinessClass,
                            final int numberSeatsEconomyClass) {
        this.numberSeatsFirstClass = numberSeatsFirstClass;
        this.numberSeatsBusinessClass = numberSeatsBusinessClass;
        this.numberSeatsEconomyClass = numberSeatsEconomyClass;
        this.planeType = planeType;
    }

    public int getNumberSeatsFirstClass() {
        return numberSeatsFirstClass;
    }

    public int getNumberSeatsBusinessClass() {
        return numberSeatsBusinessClass;
    }

    public int getNumberSeatsEconomyClass() {
        return numberSeatsEconomyClass;
    }

    public PlaneType getPlaneType() {
        return planeType;
    }
}
