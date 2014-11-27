import java.util.*;

/**
 * Created by 14007427 on 19/11/14.
 */
public class Guichet extends Thread {

    private final int TEMPS_IMPRESSION_TICKET = 10;
    private EspaceVente espaceVenteAssocie;
    private boolean guichetLibre;
    private int numGuichet;
    private List<Train> listeTrainQuai;


    public Guichet(int numGuichet, EspaceVente espaceVente) {
        this.guichetLibre = true;
        this.numGuichet = numGuichet;
        this.espaceVenteAssocie = espaceVente;
    }

    synchronized public Ticket acheterTicket(Voyageurs voyageur) {

        while (true) {

            Ticket ticketAchete = espaceVenteAssocie.chercherTicketDisponible();
            ticketAchete.setPossesseurTicket(voyageur);
            try {
                Thread.sleep(TEMPS_IMPRESSION_TICKET);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ticketAchete;
        }
    }

    public boolean getGuichetLibre() {
        return guichetLibre;
    }

    public int getNumGuichet() {
        return this.numGuichet;
    }

    public void setGuichetLibre(boolean tof) {
        this.guichetLibre = tof;
    }

    synchronized public void informeGuichets() {
        notifyAll();
    }

    @Override
    public void run() {
        System.out.println("Le guichet n°" + getNumGuichet() + " est ouvert");
    }

}

