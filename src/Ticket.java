/**
 * Created by Baptiste on 26/11/2014.
 */
public class Ticket {

    private int numTicket;
    private Train train;
    private Voyageurs possesseurTicket;

    public Ticket(Train train, int numTicket) {
        this.train      = train;
        this.numTicket  = numTicket;
    }

    public int getNumTicket() {
        return numTicket;
    }

    public Train getTrain() {
        return train;
    }

    public Ticket getTicket() {
        return this;
    }

    public void setPossesseurTicket(Voyageurs voyageurs) {
        possesseurTicket = voyageurs;
    }

    public boolean hasPossesseur() {
        if (possesseurTicket == null) {
            return false;
        }else {
            return true;
        }
    }
}
