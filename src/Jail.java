public class Jail extends Field {

    public Jail(int id, String label, int cost) {
        super(id, label, cost, 0);
    }

    @Override
    public String onLand(Player p) {
        return super.onLand(p) + "\nGo to jail";
    }

    @Override
    public String onAccept(Player p) {
        return super.onAccept(p) + "\nGo to jail";
    }

    @Override
    public String onReject(Player p) {
        return super.onReject(p) + "\nGo to jail";
    }
}