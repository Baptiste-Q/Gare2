import java.util.*;

/**
 * Created by 14007427 on 18/11/14.
 */
public class EspaceVente {

    private final int NB_MAX_GUICHET = 5;
    private EspaceQuai espaceQuaiAssocie;
    private int nbTicketEnVente;
    private Collection<Guichet> listeGuichet;
    private Map<Train,Collection<Ticket>> mapTicket;
    private ArrayList<Train> listeTrainQuai;
    private Guichet guichet;




    public EspaceVente() {

        listeGuichet = new ArrayList<Guichet>();
        listeTrainQuai = new ArrayList<Train>();

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
    // test
    synchronized public void quitterGuichet(Guichet guichetOccupe) {
        notifyAll();
    }

    synchronized public void ajouterTicketVente(Train train) {

        mapTicket = new HashMap<Train, Collection<Ticket>>();
        mapTicket.put(train,new ArrayList<Ticket>());
        Collection<Ticket> tickets = mapTicket.get(train);

        for (int i = 0; i<train.getNbPlacesDisponibles();i++){
            tickets.add(new Ticket(train,i));
        }
/*
        for (int i = 0; i < nbPlacesDisponibles; i++) {
            listeTicket.add(new Ticket(train, i + 1));
        }
  */
    }

    synchronized public Ticket chercherTicketDisponible () {

        while (true) {
            if (!listeTrainQuai.isEmpty()) {
                for (int i = 0; i < listeTrainQuai.size(); i++) {

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

    public void addTrain(Train train){
        listeTrainQuai.add(train);
    }

    public void removeTrain(Train train){
        listeTrainQuai.remove(train);
    }

    public ArrayList<Train> getListeTrainQuai(){
        return listeTrainQuai;
    }

    public EspaceQuai getEspaceQuaiAssocie() { return espaceQuaiAssocie; }




}


