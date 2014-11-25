import java.util.*;

/**
 * Created by 14007427 on 18/11/14.
 */
public class Voyageurs extends Thread {

    private final int NB_MAX_VOYAGEURS = 500;
    private boolean ticketValide;
    private EspaceQuai espaceQuai;
    private String nom;
    private List<Voyageurs> listeVoyageursAttendus;

    public Voyageurs (String nom, EspaceQuai espaceQuai) {
        this.nom = nom;
        ticketValide = false;
        this.espaceQuai = espaceQuai;
    }

    public String getNom() {
        return nom;
    }


    @Override
    public void run() {
        Guichet guichetAssocie = this.espaceQuai.espaceVente.accederGuichet();
        System.out.println(""+getNom()+" : j'ai accédé au guichet n°" + guichetAssocie.getNumGuichet() + ".");
        Train trainReserve = guichetAssocie.acheterTicket(this);
        this.espaceQuai.espaceVente.quitterGuichet(guichetAssocie);
        System.out.println(""+getNom()+" j'ai acheté un billet pour le " + trainReserve.getNomTrain() + ".");
        espaceQuai.accederAuTrain(trainReserve, this);
        System.out.println(""+getNom()+" je suis monté dans le train");
    }
}
