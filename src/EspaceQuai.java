import java.util.*;

/**
 * Created by 14007427 on 18/11/14.
 */
public class EspaceQuai {

    private final int NB_VOIES = 2;
    private int voiesDispo;
    private int trainsEnQuai;
    private List<Train> listeTrainQuai;

    public EspaceVente espaceVente;

    EspaceQuai(EspaceVente espaceVente){
        voiesDispo = NB_VOIES;
        trainsEnQuai = 0;
        listeTrainQuai = new ArrayList<Train>();
        this.espaceVente = espaceVente;
        this.espaceVente.setEspaceQuaiAssocie(this);
    }

    //Le train entre dans la voie
    synchronized public void entrerVoie(Train train){
        while (voiesDispo == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        voiesDispo--;
        //Le train est ajoute a la liste des trains en gare
        listeTrainQuai.add(train);
        //Le train cree une liste de voyageurs et signale son nombre de place disponible
        train.createListeVoyageursAttendus();
        espaceVente.ajouterPlace(train, train.getCapaciteTrain());
        train.setEtatVente(true);

        System.out.println("Train en gare " + train.getNomTrain());
    }

    //Une fois que tous les voyageurs ont embarque et que le delai d'attente est depasse, le train peut quitter la voie
    synchronized public void quitterVoie(Train train){

        train.setEtatVente(false);
        listeTrainQuai.remove(train);
        voiesDispo++;
        // Pour les trains en attente d'un quai libre.
        notifyAll();
    }

    //Permet a un passager de monter dans le bon train
   synchronized public void accederAuTrain (Train train, Voyageurs voyageur) {

        int i = 0;
        Train traindelaListe = listeTrainQuai.get(i);
        while (i < listeTrainQuai.size()) {
            if(traindelaListe.getNomTrain() == train.getNomTrain() ) {
                // On supprime le voyageur de la liste des voyageurs attendus
                train.getListeVoyageursAttendus().remove(voyageur);
                notifyAll();
                break;
            }
            else {
                i++;
            }
        }
    }

    public EspaceVente getEspaceVente() {
        return espaceVente;
    }

    public EspaceQuai getEspaceQuai() {
        return this;
    }

    public List<Train> getListeTrainQuai() { return listeTrainQuai; }

    public List<Guichet> getListeGuichet() { return espaceVente.getListeGuichet();}

}
