import java.util.*;

/**
 * Created by 14007427 on 18/11/14.
 */
public class EspaceVente {

    private final int NB_MAX_GUICHET = 5;
    private EspaceQuai espaceQuaiAssocie;
    private List<Guichet> listeGuichet;
    private List<Ticket> listeTicket;
    private Guichet guichet;



    public EspaceVente() {

        listeGuichet = new ArrayList<Guichet>();

        for (int i=0; i<4; i++) {
            guichet = new Guichet(i+1, this);
            guichet.start();
            listeGuichet.add(guichet);
        }
    }

    synchronized public Guichet accederGuichet() {

        while(true) {

            boolean guichetTrouve = false;

            int i =0;
            while (i < getListeGuichet().size()) {
                Guichet guichetdelaListe = getListeGuichet().get(i);

                if (guichetdelaListe.getGuichetLibre()) {
                    guichetdelaListe.setGuichetLibre(false);
                    guichetTrouve = true;
                    return guichetdelaListe;
                } else {
                    i++;
                }
            }
            if (!guichetTrouve) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    synchronized public void quitterGuichet(Guichet guichetOccupe) {

        notifyAll();
    }

    synchronized public void ajouterTicketVente(Train train, int nbPlacesDisponibles) {


        for (int i = 0; i < nbPlacesDisponibles; i++) {
            listeTicket.add(new Ticket(train, i + 1));
        }
    }

    synchronized public Ticket chercherTicketDisponible () {

        while (true) {

            if (!listeTicket.isEmpty()) {
                for (int i = 0; i < listeTicket.size(); i++)
                {
                    if (!listeTicket.get(i).hasPossesseur() )
                    {
                        return listeTicket.get(i);
                    }
                }
            }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public List<Ticket> getListeTicket() {return listeTicket; }

    public void setEspaceQuaiAssocie(EspaceQuai espaceQuai) {
        this.espaceQuaiAssocie = espaceQuai;
    }

    public List<Guichet> getListeGuichet() {
        return listeGuichet;
    }

    public EspaceQuai getEspaceQuaiAssocie() { return espaceQuaiAssocie; }

}


