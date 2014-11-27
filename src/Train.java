import java.util.*;

/**
 * Created by 14007427 on 18/11/14.
 */
public class Train extends Thread {

    private int vitesseTrain;
    private int tempsArretTrain;
    private int capaciteTrain;
    private int nbPlacesDisponibles;
    private String nomTrain;
    private Boolean venteOuverte;

    private EspaceQuai quai;
    private Collection<Voyageurs> voyageursAttendus;


    public Train(String nom, EspaceQuai quai) {
        vitesseTrain        = 100;
        tempsArretTrain     = 5;
        capaciteTrain       = 25;
        this.nomTrain       = nom;
        this.quai           = quai;
        voyageursAttendus = new ArrayList<Voyageurs>();
        venteOuverte = false;
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

    public void setVenteOuverte(Boolean bool){
        venteOuverte = bool;
    }

    synchronized public void ajouterVoyageurAttendus(Voyageurs voyageur){
        voyageursAttendus.add(voyageur);
    }

    @Override
    public void run() {

        //train en gare
        quai.entrerVoie(this);
        System.out.println(""+getNomTrain()+" : arrive en gare.");

        //train wait
        try {
            Thread.sleep(tempsArretTrain*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Le train quitte la gare.
        quai.quitterVoie(this);
        System.out.println(""+getNomTrain()+" a quitté la gare.");
    }
}

