package Views;

import App.HotelApp;
import Models.*;
import Exceptions.*;

import java.time.LocalDate;
import java.util.List;

public class ConsoleView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private final HotelApp app;
    private final InputHelper inputHelper;

    public ConsoleView(HotelApp app) {
        this.app = app;
        this.inputHelper = new InputHelper();
    }

    public void start() {
        boolean running = true;

        System.out.println(ANSI_CYAN + "==================================================");
        System.out.println("  Bienvenue dans le Système de Gestion Hôtelière  ");
        System.out.println("==================================================" + ANSI_RESET);

        while (running) {
            printMenu();
            int choice = inputHelper.readInt("Saisissez votre choix (1-10) : ");
            System.out.println();

            switch (choice) {
                case 1:
                    promptAddRoom();
                    break;
                case 2:
                    promptAddClient();
                    break;
                case 3:
                    promptMakeReservation();
                    break;
                case 4:
                    promptCancelReservation();
                    break;
                case 5:
                    promptShowAvailableRooms();
                    break;
                case 6:
                    promptShowClientReservations();
                    break;
                case 7:
                    promptFindRoomsByType();
                    break;
                case 8:
                    promptCalculateAveragePrice();
                    break;
                case 9:
                    promptShowTotalRevenue();
                    break;
                case 10:
                    System.out.println(ANSI_CYAN + "Merci d'avoir utilisé notre application. Au revoir !" + ANSI_RESET);
                    running = false;
                    break;
                default:
                    System.out.println(ANSI_RED + "Choix invalide. Veuillez entrer un nombre entre 1 et 10." + ANSI_RESET);
            }
            System.out.println();
        }
        inputHelper.close();
    }

    private void printMenu() {
        System.out.println(ANSI_BLUE + "----------------------- MENU -----------------------" + ANSI_RESET);
        System.out.println("1.  Ajouter une chambre");
        System.out.println("2.  Ajouter un client");
        System.out.println("3.  Réserver une chambre");
        System.out.println("4.  Annuler une réservation");
        System.out.println("5.  Afficher les chambres disponibles");
        System.out.println("6.  Afficher les réservations d'un client");
        System.out.println("7.  Rechercher une chambre par type");
        System.out.println("8.  Calculer le prix moyen des chambres");
        System.out.println("9.  Afficher le chiffre d'affaires total");
        System.out.println("10. Quitter");
        System.out.println(ANSI_BLUE + "----------------------------------------------------" + ANSI_RESET);
    }

    private void promptAddRoom() {
        System.out.println(ANSI_YELLOW + "=== Ajouter une Chambre ===" + ANSI_RESET);
        try {
            String roomNumber = inputHelper.readString("Numéro de chambre : ");
            int floor = inputHelper.readInt("Étage (>= 0) : ");
            double pricePerNight = inputHelper.readDouble("Prix par nuit (> 0) : ");

            System.out.println("Type de chambre : ");
            System.out.println("1. Chambre Simple");
            System.out.println("2. Chambre Double");
            System.out.println("3. Suite");
            int typeChoice = inputHelper.readInt("Votre choix (1-3) : ");

            Room newRoom;
            if (typeChoice == 1) {
                boolean simpleBed = inputHelper.readBoolean("Lit simple (o/n) : ");
                newRoom = new SimpleRoom(roomNumber, floor, pricePerNight, true, simpleBed);
            } else if (typeChoice == 2) {
                boolean seaView = inputHelper.readBoolean("Vue sur mer (o/n) : ");
                newRoom = new DoubleRoom(roomNumber, floor, pricePerNight, true, seaView);
            } else if (typeChoice == 3) {
                int capacity = inputHelper.readInt("Capacité (>= 2) : ");
                int numberOfRooms = inputHelper.readInt("Nombre de pièces (> 1) : ");
                newRoom = new Suite(roomNumber, floor, pricePerNight, capacity, true, numberOfRooms);
            } else {
                System.out.println(ANSI_RED + "Type invalide. Chambre non créée." + ANSI_RESET);
                return;
            }

            app.registerRoom(newRoom);
            System.out.println(ANSI_GREEN + "Chambre ajoutée avec succès ! Détail : " + newRoom + ANSI_RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(ANSI_RED + "Erreur lors de la création de la chambre : " + e.getMessage() + ANSI_RESET);
        }
    }

    private void promptAddClient() {
        System.out.println(ANSI_YELLOW + "=== Ajouter un Client ===" + ANSI_RESET);
        try {
            String clientNumber = inputHelper.readString("Numéro de client (unique) : ");
            String name = inputHelper.readString("Nom (min 2 caractères) : ");
            String email = inputHelper.readString("Email (doit contenir @ et un point après) : ");
            String phone = inputHelper.readString("Téléphone (uniquement chiffres, min 8) : ");
            int age = inputHelper.readInt("Âge (>= 18) : ");

            Client newClient = new Client(clientNumber, name, email, phone, age);
            app.registerClient(newClient);
            System.out.println(ANSI_GREEN + "Client ajouté avec succès ! Détail : " + newClient + ANSI_RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(ANSI_RED + "Erreur lors de la création du client : " + e.getMessage() + ANSI_RESET);
        }
    }

    private void promptMakeReservation() {
        System.out.println(ANSI_YELLOW + "=== Réserver une Chambre ===" + ANSI_RESET);
        try {
            String clientNumber = inputHelper.readString("Numéro de client : ");
            String roomNumber = inputHelper.readString("Numéro de chambre : ");
            LocalDate startDate = inputHelper.readDate("Date de début (AAAA-MM-JJ) : ");
            LocalDate endDate = inputHelper.readDate("Date de fin (AAAA-MM-JJ) : ");

            Room room = app.getRoom(roomNumber);
            if (room == null) {
                System.out.println(ANSI_RED + "Erreur : La chambre n'existe pas." + ANSI_RESET);
                return;
            }

            Reservation reservation = app.createReservation(clientNumber, room, startDate, endDate);
            System.out.println(ANSI_GREEN + "Réservation effectuée avec succès !" + ANSI_RESET);
            System.out.println(reservation);
        } catch (ClientNotFoundException | RoomUnavailableException | ReservationLimitException e) {
            System.out.println(ANSI_RED + "Erreur de réservation : " + e.getMessage() + ANSI_RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(ANSI_RED + "Erreur de saisie : " + e.getMessage() + ANSI_RESET);
        }
    }

    private void promptCancelReservation() {
        System.out.println(ANSI_YELLOW + "=== Annuler une Réservation ===" + ANSI_RESET);
        try {
            String reservationNumber = inputHelper.readString("Numéro de réservation (ex: RES-1) : ");
            app.cancelReservation(reservationNumber);
            System.out.println(ANSI_GREEN + "Réservation annulée avec succès. La chambre est à nouveau disponible." + ANSI_RESET);
        } catch (ReservationNotFoundException e) {
            System.out.println(ANSI_RED + "Erreur d'annulation : " + e.getMessage() + ANSI_RESET);
        }
    }

    private void promptShowAvailableRooms() {
        System.out.println(ANSI_YELLOW + "=== Chambres Disponibles ===" + ANSI_RESET);
        List<Room> available = app.getAvailableRooms();
        if (available.isEmpty()) {
            System.out.println("Aucune chambre disponible actuellement.");
        } else {
            for (Room r : available) {
                System.out.println("- " + r);
            }
        }
    }

    private void promptShowClientReservations() {
        System.out.println(ANSI_YELLOW + "=== Réservations d'un Client ===" + ANSI_RESET);
        String clientNumber = inputHelper.readString("Numéro de client : ");
        List<Reservation> clientRes = app.getClientReservations(clientNumber);
        if (clientRes.isEmpty()) {
            System.out.println("Aucune réservation trouvée pour le client " + clientNumber + ".");
        } else {
            System.out.println("Réservations pour le client " + clientNumber + " :");
            for (Reservation res : clientRes) {
                System.out.println("- " + res);
            }
        }
    }

    private void promptFindRoomsByType() {
        System.out.println(ANSI_YELLOW + "=== Rechercher par Type ===" + ANSI_RESET);
        String type = inputHelper.readString("Saisissez le type (Simple / Double / Suite) : ");
        List<Room> rooms = app.getRoomsByType(type);
        if (rooms.isEmpty()) {
            System.out.println("Aucune chambre de type '" + type + "' trouvée.");
        } else {
            for (Room r : rooms) {
                System.out.println("- " + r);
            }
        }
    }

    private void promptCalculateAveragePrice() {
        System.out.println(ANSI_YELLOW + "=== Prix Moyen des Chambres ===" + ANSI_RESET);
        double avg = app.getAverageRoomPrice();
        System.out.printf("Le prix moyen par nuit est de : %.2f €\n", avg);
    }

    private void promptShowTotalRevenue() {
        System.out.println(ANSI_YELLOW + "=== Chiffre d'Affaires Total ===" + ANSI_RESET);
        double revenue = app.getTotalRevenue();
        System.out.printf("Le chiffre d'affaires total généré est de : %.2f €\n", revenue);
    }
}
