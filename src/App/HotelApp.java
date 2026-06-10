package App;

import Models.*;
import Repositories.*;
import Exceptions.*;

import java.time.LocalDate;
import java.util.List;

public class HotelApp {

    private final RoomsManagement roomsManagement;
    private final ClientsManagement clientsManagement;
    private final ReservationsManagement reservationsManagement;

    public HotelApp() {
        this.roomsManagement = new RoomsManagement();
        this.clientsManagement = new ClientsManagement();
        this.reservationsManagement = new ReservationsManagement(clientsManagement, roomsManagement);
        prepopulateData();
    }

    public void registerRoom(Room room) {
        roomsManagement.addRoom(room);
    }

    public void registerClient(Client client) {
        clientsManagement.addClient(client);
    }

    public Reservation createReservation(String clientNumber, Room room, LocalDate startDate, LocalDate endDate) 
            throws ClientNotFoundException, RoomUnavailableException, ReservationLimitException {
        return reservationsManagement.reserve(clientNumber, room, startDate, endDate);
    }

    public void cancelReservation(String reservationNumber) throws ReservationNotFoundException {
        reservationsManagement.cancelReservation(reservationNumber);
    }

    public List<Room> getAvailableRooms() {
        return roomsManagement.getAvailableRooms();
    }

    public List<Reservation> getClientReservations(String clientNumber) {
        return reservationsManagement.getClientReservations(clientNumber);
    }

    public List<Room> getRoomsByType(String type) {
        return roomsManagement.getRoomsByType(type);
    }

    public double getAverageRoomPrice() {
        return roomsManagement.calculateAveragePrice();
    }

    public double getTotalRevenue() {
        return reservationsManagement.totalRevenue();
    }

    public Room getRoom(String roomNumber) {
        return roomsManagement.getRoom(roomNumber);
    }

    public Client getClient(String clientNumber) {
        return clientsManagement.getClient(clientNumber);
    }

    private void prepopulateData() {
        try {
            roomsManagement.addRoom(new SimpleRoom("101", 1, 55.0, true, true));
            roomsManagement.addRoom(new SimpleRoom("102", 1, 60.0, true, false));
            roomsManagement.addRoom(new DoubleRoom("201", 2, 85.0, true, true));
            roomsManagement.addRoom(new DoubleRoom("202", 2, 75.0, true, false));
            roomsManagement.addRoom(new Suite("301", 3, 150.0, 4, true, 3));

            clientsManagement.addClient(new Client("C-001", "Alice Dupont", "alice.dupont@gmail.com", "0612345678", 24));
            clientsManagement.addClient(new Client("C-002", "Jean Martin", "jean.martin@yahoo.fr", "0788776655", 35));
            clientsManagement.addClient(new Client("C-003", "Marie Curie", "marie.curie@sciences.org", "0122334455", 48));

            reservationsManagement.reserve("C-001", roomsManagement.getRoom("201"), LocalDate.now(), LocalDate.now().plusDays(3));
        } catch (Exception e) {
            System.err.println("Erreur d'initialisation des données : " + e.getMessage());
        }
    }
}
