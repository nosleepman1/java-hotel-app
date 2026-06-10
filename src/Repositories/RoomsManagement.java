package Repositories;

import Models.Room;
import java.util.*;

public class RoomsManagement {

    private Map<String, Room> rooms;

    public RoomsManagement() {
        rooms = new HashMap<>();
    }

    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number " + room.getRoomNumber() + " already exists.");
        }
        rooms.put(room.getRoomNumber(), room);
    }

    public Room getRoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public List<Room> getRooms() {
        return new ArrayList<>(rooms.values());
    }

    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for (Room r : rooms.values()) {
            if (r.isAvailable()) {
                available.add(r);
            }
        }
        return available;
    }

    public List<Room> getRoomsByType(String type) {
        List<Room> result = new ArrayList<>();
        for (Room r : rooms.values()) {
            if (r.getRoomType().equalsIgnoreCase(type)) {
                result.add(r);
            }
        }
        return result;
    }

    public double calculateAveragePrice() {
        if (rooms.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Room r : rooms.values()) {
            sum += r.getPricePerNight();
        }
        return sum / rooms.size();
    }

    public List<Room> sortByPrice() {
        List<Room> sorted = new ArrayList<>(rooms.values());
        sorted.sort(Comparator.comparingDouble(Room::getPricePerNight));
        return sorted;
    }
}
