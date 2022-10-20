public class Chance extends Field {
    public Chance(int id, String label, int cost, int income) {
        super(id, label, cost, income);
    }

    @Override
    public String onLand(Player p) {
        String s = super.onLand(p);
        s+= "\n Pull a card from the deck. (Press J): ";

        return s;
    }

    public String onAccept(Player p){
        ChanceCard card = Board.getChanceCard();
        if (card.getCost() > 0){
            p.pay(card.getCost());
        } else {
            p.receive(card.getIncome());
        }
        return "You pulled a chance card " + card.getName() + (card.getCost() > 0 ? " and now you have to pay " + card.getCost() + " kr." : " and you'll recieve " + card.getIncome() + " kr.");
    }
}