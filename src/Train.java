import java.util.*;

/**
 * Created by 14007427 on 18/11/14.
 */
public class Train extends Thread {

    private final int NB_MAX_TRAIN = 5;
    private int vitesseTrain;
    private int tempsArretTrain;
    private int capaciteTrain;
    private int nbPlacesDisponibles;
    private String nomTrain;
    private EspaceQuai quai;
    private List<Voyageurs> listeVoyageursAttendus;


    public Train(String nom, EspaceQuai quai) {
        vitesseTrain    = 100;
        tempsArretTrain = 5;
        capaciteTrain = 25;
        this.nomTrain = nom;
        this.quai = quai;
    }

    public double getVitesseTrain() {
        return vitesseTrain;
    }

    public double getTempsArretTrain() {return tempsArretTrain;}

    synchronized public void setNbPlacesDisponibles(int x) {
        this.nbPlacesDisponibles = x;
    }

    synchronized public int getNbPlacesDisponibles() {
        return nbPlacesDisponibles;
    }

    public int getCapaciteTrain() {
        return capaciteTrain;
    }

    public void setNomTrain(String nomTrain) {
        this.nomTrain = nomTrain;
    }

    public String getNomTrain() { return nomTrain; }


    public void createListeVoyageursAttendus () {
        listeVoyageursAttendus = new ArrayList<Voyageurs>();
    }

    public List<Voyageurs> getListeVoyageursAttendus() { return listeVoyageursAttendus; }


    @Override
    public void run() {

        //Le train entre en gare
        quai.entrerVoie(this);
        System.out.println(""+getNomTrain()+" : arrive en gare.");

        //Le train attend la duree d'arret normale.
        try {
            Thread.sleep(tempsArretTrain*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // S'il y a toujours des voyageurs attendus, le train ne part pas et attend les voyageurs manquants

        //Le train est plein, il peut quitter la gare
        quai.quitterVoie(this);
        System.out.println(""+getNomTrain()+" a quitté la gare.");

    }
}

