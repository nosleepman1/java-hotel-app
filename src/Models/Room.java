package Models;

abstract public class Room {

    protected String roomNumber;
    protected int floor;
    protected double pricePerNight;
    protected int capacity;
    protected boolean available;

    public Room(String roomNumber, int floor, double pricePerNight, int capacity, boolean available) {
        setRoomNumber(roomNumber);
        setFloor(floor);
        setPricePerNight(pricePerNight);
        setCapacity(capacity);
        setAvailable(available);
    }

    public abstract String getRoomType();

    public abstract double calculateTotalPrice(int nbNuits);

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty.");
        }
        this.roomNumber = roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        if (floor < 0) {
            throw new IllegalArgumentException("Floor must be greater than or equal to 0.");
        }
        this.floor = floor;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price per night must be greater than 0.");
        }
        this.pricePerNight = pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than or equal to 1.");
        }
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format("Room %s (Floor %d) - Price: %.2f/night - Capacity: %d - Available: %b", 
                roomNumber, floor, pricePerNight, capacity, available);
    }
}
