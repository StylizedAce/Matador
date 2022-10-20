public class Property extends Field {

    private final int serieID;
    private Player owner;
    private String option;

    boolean monopoly = false; // bruges i Plot.onLand() til at bestemme om der skal vises tilbud om at bygge

    public Property(int id, String label, int cost, int income, int serieID) {
        super(id, label, cost, income);
        this.serieID = serieID;
        this.owner = null;
    }

    @Override
    public String toString() {
        return super.toString() + " is from series; " + getSerieID();
    }

    @Override
    public String onLand(Player p) {
        // TODO: add if the owner == p then you shouldn't pay rent
        String s = super.onLand(p) + "\n";
        if (this.getOwner() == null) {
            option = "buy";
            s += "Will you buy " + this.getLabel() + " for " + this.getCost() + " kr? your balance now: " + p.getBankAccount().getBalance() + "\nTast J for ja, N for nej.";
        } else {
            option = "payRent";
            s += this.getOwner().getName() + " already owns " + this.getLabel();
            s += "\nYou have to pay " + this.getIncome() + " kr in houserent. Press J to accept:";
        }
        return s;
    }

    @Override
    protected String onAccept(Player p) {
        String s = "";
        if (option.equalsIgnoreCase("buy")) {
            s = this.getLabel() + " is yours!";
            p.buy(this);
            setOwner(p);
        } else if (option.equalsIgnoreCase("payRent")) {
            s = "You have paid " + this.getIncome()+ " to " + this.owner.getName() + " in houserent.";
            p.pay(this.owner, this.getIncome());
        }
        return s;
    }

    @Override
    protected String onReject(Player p) {
        String s = "";
        if (option.equalsIgnoreCase("buy")) {
            // budrunde
            s = "a commission has started on " + this.getLabel();
        } else if (option.equalsIgnoreCase("payRent")) {
            // forlader spillet
            s = "You do not have sufficient funds to pay " + this.owner.getName() + " " + this.getIncome() + " for landing on " + this.getLabel() + ". You leave the game.";
        }
        return s;
    }


    protected int getSerieID() {
        return serieID;
    }

    protected Player getOwner() {
        return owner;
    }

    protected void setOwner(Player owner) {
        this.owner = owner;
    }
}
