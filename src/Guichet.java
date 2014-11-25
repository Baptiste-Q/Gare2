import java.util.*;

/**
 * Created by 14007427 on 19/11/14.
 */
public class Guichet extends Thread{

    private final int TEMPS_IMPRESSION_TICKET = 10;
    private EspaceVente espaceVenteAssocie;
    private boolean guichetLibre;
    private int numGuichet;
    private List<Train> listeTrainQuai;


    public Guichet(int numGuichet) {
        this.guichetLibre = true;
        this.numGuichet = numGuichet;
    }

    synchronized public Train acheterTicket(Voyageurs voyageur) {

        while (true) {

            listeTrainQuai = espaceVenteAssocie.getEspaceQuaiAssocie().getListeTrainQuai();
            int i = 0;
            while (i < listeTrainQuai.size()) {
                if (listeTrainQuai.get(i).getNbPlacesDisponibles() > 0) {
                    try {
                        Thread.sleep(TEMPS_IMPRESSION_TICKET);
                        listeTrainQuai.get(i).setNbPlacesDisponibles((listeTrainQuai.get(i).getNbPlacesDisponibles() - 1));
                        // On ajoute le voyageur a la liste du train
                        listeTrainQuai.get(i).getListeVoyageursAttendus().add(voyageur);
                        //On retourne le train concerne par l'achat du ticket
                        return listeTrainQuai.get(i);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    i++;
                }
            }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void setEspaceVenteAssocie(EspaceVente espaceVente) {
        this.espaceVenteAssocie = espaceVente;
    }

    public boolean getGuichetLibre() {
        return guichetLibre;
    }

    public int getNumGuichet() {
        return this.numGuichet;
    }

    public void setGuichetLibre(boolean tof) {
        this.guichetLibre = tof;
    }

    synchronized public void informeGuichets() {
        notifyAll();
    }

    @Override
    public void run() {
        System.out.println("Le guichet n°" + getNumGuichet() + " est ouvert");
    }

}

