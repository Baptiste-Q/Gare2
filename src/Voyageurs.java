import java.util.*;

/**
 * Created by 14007427 on 18/11/14.
 */
public class Voyageurs extends Thread {

    private final int NB_MAX_VOYAGEURS = 500;
    private EspaceQuai espaceQuai;
    private String nom;
    private List<Voyageurs> listeVoyageursAttendus;
    private Ticket ticketAchete;

    public Voyageurs (String nom, EspaceQuai espaceQuai) {
        this.nom = nom;
        this.espaceQuai = espaceQuai;
    }

    public String getNom() {
        return nom;
    }


    @Override
    public void run() {
        Guichet guichetAssocie = this.espaceQuai.espaceVente.accederGuichet();
        System.out.println(""+getNom()+" : j'ai accédé au guichet n°" + guichetAssocie.getNumGuichet() + ".");
        ticketAchete = guichetAssocie.acheterTicket(this);
        espaceQuai.espaceVente.quitterGuichet(guichetAssocie);
        System.out.println(""+getNom()+" j'ai acheté un billet pour le " + ticketAchete.getTrain().getNomTrain() + ".");
        espaceQuai.accederAuTrain(ticketAchete.getTrain(), this);
        System.out.println(""+getNom()+" je suis monté dans le train");
    }
}
