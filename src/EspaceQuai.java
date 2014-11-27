import java.util.*;

/**
 * Created by 14007427 on 18/11/14.
 */
public class EspaceQuai {

    private final int NB_VOIES = 2;
    private int nbTrainEnQuai;
    private int voiesDispo;

    private EspaceVente espaceVente;

    EspaceQuai(EspaceVente espaceVente){
        voiesDispo = NB_VOIES;
        nbTrainEnQuai = 0;
        this.espaceVente = espaceVente;
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
        espaceVente.addTrain(train);

        espaceVente.ajouterTicketVente(train);
        train.setVenteOuverte(true);
        System.out.println("Train en gare " + train.getNomTrain());
    }

    //Une fois que tous les voyageurs ont embarque et que le delai d'attente est depasse, le train peut quitter la voie
    synchronized public void quitterVoie(Train train)  {
        train.setVenteOuverte(false);
        espaceVente.removeTrain(train);
        voiesDispo++;
        //Pour les trains en attente d'un quai libre.
        notifyAll();
    }

    //Permet a un passager de monter dans le bon train
   synchronized public void accederAuTrain (Train train, Voyageurs voyageur) {

        int i = 0;
       ArrayList<Train> liste = espaceVente.getListeTrainQuai();
        Train traindelaListe = liste.get(i);
        while (i < espaceVente.getListeTrainQuai().size()) {
            if(traindelaListe.getNomTrain() == train.getNomTrain() ) {
                // On supprime le voyageur de la liste des voyageurs attendus
                notifyAll();
                //train.informeTrain();
                break;
            }
            else {
                i++;
            }
        }
    }

    public void addTrainEnQuai(Train train){
        espaceVente.addTrain(train);
    }
}
