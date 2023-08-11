import java.util.Random;

public class Ticket {
    private int ticketNumber;
    private int seatNumber;
    private double price;
    private String location;
    private int quantity;

    public Ticket(int ticketNumber, int seatNumber, double price) {
        this.ticketNumber = ticketNumber;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isTicketEligible() {
        int ticketNumber = generateRandomNumber();
        int a = generateRandomNumber();
        int b = generateRandomNumber();

        int min = Math.min(a, b);
        int max = Math.max(a, b);

        return ticketNumber >= min && ticketNumber <= max;
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(15000) + 1;
    }

    public Ticket[] getPurchasedTickets() {
        return null;
    }
}