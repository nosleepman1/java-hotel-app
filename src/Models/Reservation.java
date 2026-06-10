package Models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {

    private String reservationNumber;
    private Client client;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfNights;
    private boolean active;

    public Reservation(String reservationNumber, Client client, Room room, LocalDate startDate, LocalDate endDate) {
        if (reservationNumber == null || reservationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation number cannot be empty.");
        }
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        this.reservationNumber = reservationNumber;
        this.client = client;
        this.room = room;
        setDates(startDate, endDate);
        this.active = true;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public Client getClient() {
        return client;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null.");
        }
        long nights = ChronoUnit.DAYS.between(startDate, endDate);
        if (nights < 1) {
            throw new IllegalArgumentException("Reservation duration must be at least 1 night (end date must be after start date).");
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfNights = (int) nights;
    }

    public double calculateTotalPrice() {
        return room.calculateTotalPrice(numberOfNights);
    }

    @Override
    public String toString() {
        return String.format("Reservation %s - Client: %s (%s) - Room: %s - Period: %s to %s (%d nights) - Cost: %.2f - Active: %b",
                reservationNumber, client.getName(), client.getClientNumber(), room.getRoomNumber(),
                startDate, endDate, numberOfNights, calculateTotalPrice(), active);
    }
}
