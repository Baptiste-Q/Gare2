import java.util.*;

/**
 * Created by 14007427 on 18/11/14.
 */
public class EspaceVente {

    private final int NB_MAX_GUICHET = 5;
    private EspaceQuai espaceQuaiAssocie;
    private List<Guichet> listeGuichet;
    private List<Train> listeTrainQuai;


    public EspaceVente() {

        listeGuichet = new ArrayList<Guichet>();

        for (int i=0; i<4; i++) {
            Guichet guichet = new Guichet(i+1);
            guichet.start();
            guichet.setEspaceVenteAssocie(this);
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

        guichetOccupe.setGuichetLibre(true);
        notifyAll();
    }

    synchronized public void ajouterPlace(Train train, int x) {
        train.setNbPlacesDisponibles(x);
        for (int i=0; i<4; i++) {
            listeGuichet.get(i).informeGuichets();
        }
    }

    public void setEspaceQuaiAssocie(EspaceQuai espaceQuai) {
        this.espaceQuaiAssocie = espaceQuai;
    }

    public List<Guichet> getListeGuichet() {
        return listeGuichet;
    }

    public EspaceQuai getEspaceQuaiAssocie() {return this.espaceQuaiAssocie;}

}

