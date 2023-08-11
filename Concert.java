import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Concert {
    private List<Ticket> tickets;
    private List<String> availableLocations;
    private Buyer activeBuyer;

    public Concert() {
        tickets = new ArrayList<>();
        availableLocations = new ArrayList<>();
        availableLocations.add("Localidad 1");
        availableLocations.add("Localidad 5");
        availableLocations.add("Localidad 10");
        activeBuyer = null;
    }

    public void distributeTickets(int totalTickets) {
        // Distribuye los boletos en las diferentes localidades
        for (String location : availableLocations) {
            for (int i = 0; i < totalTickets / availableLocations.size(); i++) {
                double price = getLocationPrice(location);
                Ticket ticket = new Ticket(i + 1, i + 101, price);
                ticket.setLocation(location);
                tickets.add(ticket);
            }
        }
    }

    public Ticket purchaseTicket(String name, String email, int quantity, double budget) {
        if (activeBuyer == null || activeBuyer.getEmail().equals(email)) {
            activeBuyer = new Buyer(name, email);
        }

        Ticket chosenTicket = null;
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = getRandomTicket();
            if (ticket != null) {
                if (ticket.getPrice() <= budget && ticket.isTicketEligible()) {
                    activeBuyer.addPurchasedTicket(ticket);
                    chosenTicket = ticket;
                }
            }
        }
        return chosenTicket;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea pendiente

            switch (choice) {
                case 1:
                    createNewBuyer(scanner);
                    break;
                case 2:
                    participateInTicketRequest(scanner);
                    break;
                case 3:
                    displayTotalAvailability();
                    break;
                case 4:
                    displayIndividualAvailability(scanner);
                    break;
                case 5:
                    displayCashReport();
                    break;
                case 6:
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción inválida. Por favor, elige una opción válida.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n==== Menú ====");
        System.out.println("1. Nuevo comprador");
        System.out.println("2. Nueva solicitud de boletos");
        System.out.println("3. Consultar disponibilidad total");
        System.out.println("4. Consultar disponibilidad individual");
        System.out.println("5. Reporte de caja");
        System.out.println("6. Salir");
        System.out.print("Elige una opción: ");
    }

    private void createNewBuyer(Scanner scanner) {
        System.out.print("Ingrese el nombre del comprador: ");
        String name = scanner.nextLine();
        System.out.print("Ingrese el correo electrónico del comprador: ");
        String email = scanner.nextLine();

        activeBuyer = new Buyer(name, email);
        System.out.println("Nuevo comprador creado: " + activeBuyer.getName() + " (" + activeBuyer.getEmail() + ")");
    }

    private void participateInTicketRequest(Scanner scanner) {
        if (activeBuyer == null) {
            System.out.println("Debes crear un nuevo comprador primero.");
            return;
        }

        System.out.print("Ingrese la cantidad de boletos que desea comprar: ");
        int quantity = scanner.nextInt();
        System.out.print("Ingrese su presupuesto máximo: ");
        double budget = scanner.nextDouble();

        Ticket purchasedTicket = purchaseTicket(activeBuyer.getName(), activeBuyer.getEmail(), quantity, budget);

        if (purchasedTicket != null) {
            System.out.println("¡Felicidades! Has comprado " + quantity + " boleto(s) para la localidad " +
                               purchasedTicket.getLocation() + " por un total de $" + purchasedTicket.getPrice() * quantity);
        } else {
            System.out.println("Lo sentimos, no ha sido posible realizar la compra.");
        }
    }

    private void displayTotalAvailability() {
        System.out.println("\n==== Disponibilidad Total ====");
        for (String location : availableLocations) {
            int availableTickets = getAvailableTicketsForLocation(location);
            System.out.println("Localidad: " + location + " - Disponibles: " + availableTickets);
        }
    }

    private void displayIndividualAvailability(Scanner scanner) {
        System.out.print("Ingrese la localidad para consultar la disponibilidad: ");
        String location = scanner.nextLine();
        int availableTickets = getAvailableTicketsForLocation(location);
        System.out.println("Disponibles en " + location + ": " + availableTickets);
    }

    private void displayCashReport() {
        double totalRevenue = calculateTotalRevenue();
        System.out.println("\n==== Reporte de Caja ====");
        System.out.println("Total recaudado: $" + totalRevenue);
    }

    private int getLocationIndex(String location) {
        return availableLocations.indexOf(location);
    }

    private double getLocationPrice(String location) {
        if (location.equals("Localidad 1")) {
            return 100.0;
        } else if (location.equals("Localidad 5")) {
            return 500.0;
        } else if (location.equals("Localidad 10")) {
            return 1000.0;
        }
        return 0.0;
    }

    private Ticket getRandomTicket() {
        if (tickets.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(tickets.size());
        return tickets.get(randomIndex);
    }

    private int getAvailableTicketsForLocation(String location) {
        int availableTickets = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getLocation().equals(location)) {
                availableTickets++;
            }
        }
        return availableTickets;
    }

    private double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        for (Ticket buyer : activeBuyer.getPurchasedTickets()) {
            for (Ticket ticket : buyer.getPurchasedTickets()) {
                totalRevenue += ticket.getPrice();
            }
        }
        return totalRevenue;
    }

    public static void main(String[] args) {
        Concert concert = new Concert();
        concert.distributeTickets(60);
        concert.run();
    }
}
