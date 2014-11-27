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
    private int nbVoyageursAttendus;


    public Train(String nom, EspaceQuai quai) {
        vitesseTrain        = 100;
        tempsArretTrain     = 5;
        capaciteTrain       = 25;
        this.nomTrain       = nom;
        this.quai           = quai;
        nbVoyageursAttendus = 0;
    }

    synchronized public void setNbPlacesDisponibles(int x) {
        this.nbPlacesDisponibles = x;
    }

    synchronized public int getNbPlacesDisponibles() {
        return nbPlacesDisponibles;
    }

    public int getCapaciteTrain() {
        return capaciteTrain;
    }

    public String getNomTrain() {
        return nomTrain;
    }

    synchronized public void informeTrain() {
        notifyAll();
    }

    @Override
    public void run() {

        //Le train entre en gare
        quai.entrerVoie(this);
        System.out.println(""+getNomTrain()+" : arrive en gare.");

        //Le train attend la duree d'arret normale.
        try {
            Thread.sleep(tempsArretTrain*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // S'il y a toujours des voyageurs attendus, le train ne part pas et attend les voyageurs manquants

        //Le train est plein, il peut quitter la gare
        quai.quitterVoie(this);
        System.out.println(""+getNomTrain()+" a quitté la gare.");

    }
}

