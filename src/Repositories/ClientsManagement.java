package Repositories;

import Models.Client;
import java.util.*;

public class ClientsManagement {

    private Map<String, Client> clients;

    public ClientsManagement() {
        clients = new HashMap<>();
    }

    public void addClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
        if (clients.containsKey(client.getClientNumber())) {
            throw new IllegalArgumentException("Client number " + client.getClientNumber() + " already exists.");
        }
        clients.put(client.getClientNumber(), client);
    }

    public Client getClient(String clientNumber) {
        return clients.get(clientNumber);
    }

    public List<Client> findClientsByName(String word) {
        if (word == null) return new ArrayList<>();
        List<Client> result = new ArrayList<>();
        String searchWord = word.toLowerCase();
        for (Client c : clients.values()) {
            if (c.getName().toLowerCase().contains(searchWord)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Client> listAllClients() {
        return new ArrayList<>(clients.values());
    }
}
