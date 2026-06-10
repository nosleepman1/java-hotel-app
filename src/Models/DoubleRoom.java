package Models;

public class DoubleRoom extends Room {

    private boolean seaView;

    public DoubleRoom(String roomNumber, int floor, double pricePerNight, boolean available, boolean seaView) {
        super(roomNumber, floor, pricePerNight, 2, available);
        this.seaView = seaView;
    }

    @Override
    public String getRoomType() {
        return "Double";
    }

    @Override
    public double calculateTotalPrice(int nbNuits) {
        if (nbNuits < 1) {
            throw new IllegalArgumentException("Number of nights must be at least 1.");
        }
        double basePrice = pricePerNight * nbNuits;
        if (seaView) {
            return basePrice * 1.2;
        }
        return basePrice;
    }

    public boolean isSeaView() {
        return seaView;
    }

    public void setSeaView(boolean seaView) {
        this.seaView = seaView;
    }

    @Override
    public String toString() {
        return String.format("%s - Type: Double - Sea View: %b", super.toString(), seaView);
    }
}
