package Models;

public class SimpleRoom extends Room {

    private boolean simpleBed;

    public SimpleRoom(String roomNumber, int floor, double pricePerNight, boolean available, boolean simpleBed) {
        super(roomNumber, floor, pricePerNight, 1, available);
        this.simpleBed = simpleBed;
    }

    @Override
    public String getRoomType() {
        return "Simple";
    }

    @Override
    public double calculateTotalPrice(int nbNuits) {
        if (nbNuits < 1) {
            throw new IllegalArgumentException("Number of nights must be at least 1.");
        }
        return pricePerNight * nbNuits;
    }

    public boolean isSimpleBed() {
        return simpleBed;
    }

    public void setSimpleBed(boolean simpleBed) {
        this.simpleBed = simpleBed;
    }

    @Override
    public String toString() {
        return String.format("%s - Type: Simple - Simple Bed: %b", super.toString(), simpleBed);
    }
}
