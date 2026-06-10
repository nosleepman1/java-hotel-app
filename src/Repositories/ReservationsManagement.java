package Repositories;

import Models.Client;
import Models.Room;
import Models.Reservation;
import Exceptions.ClientNotFoundException;
import Exceptions.RoomUnavailableException;
import Exceptions.ReservationLimitException;
import Exceptions.ReservationNotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationsManagement {

    private Map<String, Reservation> reservations;
    private ClientsManagement clientsManagement;
    private RoomsManagement roomsManagement;
    private final AtomicInteger reservationCounter = new AtomicInteger(1);

    public ReservationsManagement(ClientsManagement clientsManagement, RoomsManagement roomsManagement) {
        this.reservations = new HashMap<>();
        this.clientsManagement = clientsManagement;
        this.roomsManagement = roomsManagement;
    }

    public Reservation reserve(String clientNumber, Room room, LocalDate startDate, LocalDate endDate) 
            throws ClientNotFoundException, RoomUnavailableException, ReservationLimitException {
        
        if (clientNumber == null || room == null) {
            throw new IllegalArgumentException("Client number and Room cannot be null.");
        }

        Client client = clientsManagement.getClient(clientNumber);
        if (client == null) {
            throw new ClientNotFoundException("Client with ID " + clientNumber + " does not exist.");
        }

        if (!room.isAvailable()) {
            throw new RoomUnavailableException("Room " + room.getRoomNumber() + " is already reserved.");
        }

        int activeCount = 0;
        for (Reservation res : reservations.values()) {
            if (res.getClient().getClientNumber().equals(clientNumber) && res.isActive()) {
                activeCount++;
            }
        }
        if (activeCount >= 3) {
            throw new ReservationLimitException("Client " + clientNumber + " already has 3 active reservations. Cannot reserve more.");
        }

        String reservationId = "RES-" + reservationCounter.getAndIncrement();
        Reservation reservation = new Reservation(reservationId, client, room, startDate, endDate);
        
        room.setAvailable(false);
        
        reservations.put(reservationId, reservation);
        return reservation;
    }

    public void cancelReservation(String reservationNumber) throws ReservationNotFoundException {
        if (reservationNumber == null) {
            throw new IllegalArgumentException("Reservation number cannot be null.");
        }
        
        Reservation reservation = reservations.get(reservationNumber);
        if (reservation == null || !reservation.isActive()) {
            throw new ReservationNotFoundException("Reservation " + reservationNumber + " does not exist or is already cancelled.");
        }

        reservation.setActive(false);
        reservation.getRoom().setAvailable(true);
    }

    public List<Reservation> getClientReservations(String clientNumber) {
        if (clientNumber == null) return new ArrayList<>();
        List<Reservation> clientRes = new ArrayList<>();
        for (Reservation res : reservations.values()) {
            if (res.getClient().getClientNumber().equals(clientNumber)) {
                clientRes.add(res);
            }
        }
        return clientRes;
    }

    public int totalReservationsCount() {
        return reservations.size();
    }

    public double totalRevenue() {
        double revenue = 0.0;
        for (Reservation res : reservations.values()) {
            if (res.isActive()) {
                revenue += res.calculateTotalPrice();
            }
        }
        return revenue;
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations.values());
    }
}
