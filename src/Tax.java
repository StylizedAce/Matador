public class Tax extends Field {

    public Tax(int id, String label, int cost, int income) {
        super(id, label, cost, income);
    }

    @Override
    public String onLand(Player p) {
        return super.onLand(p) + "\nYou have to pay tax. Press J to pay: " + cost + ", otherwise we will take your actives.";
    }

    @Override
    public String onAccept(Player p) {
        p.pay(cost);
        return p.getName() + " has paid " + cost + " kr. in tax.";
    }

    @Override
    public String onReject(Player p) {
        float calcTax = calculateAssets(p) * 0.1f;
        p.pay((int) calcTax);
        return "We have pulled 10% of your actives.";
    }

    private int calculateAssets(Player p) {
        int assets = p.getBankAccount().getBalance();
        assets += p.getPropertyValues();
        return assets;
    }
}