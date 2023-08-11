import java.util.ArrayList;
import java.util.List;

public class Buyer {
    private String name;
    private String email;
    private List<Ticket> purchasedTickets;

    public Buyer(String name, String email) {
        this.name = name;
        this.email = email;
        purchasedTickets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void addPurchasedTicket(Ticket ticket) {
        purchasedTickets.add(ticket);
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }
}