package Models;

public class Suite extends Room {

    private int numberOfRooms;

    public Suite(String roomNumber, int floor, double pricePerNight, int capacity, boolean available, int numberOfRooms) {
        super(roomNumber, floor, pricePerNight, capacity, available);
        setNumberOfRooms(numberOfRooms);
    }

    @Override
    public String getRoomType() {
        return "Suite";
    }

    @Override
    public void setCapacity(int capacity) {
        if (capacity < 2) {
            throw new IllegalArgumentException("Suite capacity must be at least 2.");
        }
        super.setCapacity(capacity);
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        if (numberOfRooms <= 1) {
            throw new IllegalArgumentException("Number of rooms in a Suite must be greater than 1.");
        }
        this.numberOfRooms = numberOfRooms;
    }

    @Override
    public double calculateTotalPrice(int nbNuits) {
        if (nbNuits < 1) {
            throw new IllegalArgumentException("Number of nights must be at least 1.");
        }
        return pricePerNight * nbNuits * numberOfRooms * 0.9;
    }

    @Override
    public String toString() {
        return String.format("%s - Type: Suite - Rooms: %d", super.toString(), numberOfRooms);
    }
}
