import java.time.LocalDate;

public class LuxuryCar extends Car{

    private int luxId;
    private int ccm;
    private Gear gear;
    private boolean aircondition;
    private boolean speedPilot;
    private boolean leatherSeats;

    public LuxuryCar(int carId, String brand, String model, String fuelType,
                     String registrationNumber, LocalDate registrationYearMonth,
                     int drivinKm, int luxId, int ccm, Gear gear, boolean aircondition,
                     boolean speedPilot, boolean leatherSeats) {
        super(carId, brand, model, fuelType, registrationNumber, registrationYearMonth, drivinKm);
        this.luxId = luxId;
        this.ccm = ccm;
        this.gear = gear;
        this.aircondition = aircondition;
        this.speedPilot = speedPilot;
        this.leatherSeats = leatherSeats;
    }

    public LuxuryCar( int luxId, int ccm, Gear gear, boolean aircondition,
                     boolean speedPilot, boolean leatherSeats) {
        this.luxId = luxId;
        this.ccm = ccm;
        this.gear = gear;
        this.aircondition = aircondition;
        this.speedPilot = speedPilot;
        this.leatherSeats = leatherSeats;
    }

    public LuxuryCar() {

    }

    public int getLuxId() {
        return luxId;
    }

    public int getCcm() {
        return ccm;
    }

    public Gear getGear() {
        return gear;
    }

    public boolean isAircondition() {
        return aircondition;
    }

    public boolean isSpeedPilot() {
        return speedPilot;
    }

    public boolean isLeatherSeats() {
        return leatherSeats;
    }
}
