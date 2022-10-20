
import java.util.ArrayList;

public class Game {
    public FileIO fileIO = new FileIO();
    private TextUI textUI = new TextUI();
    private ArrayList<Player> players = new ArrayList();
    private Board board;
    private Player currentPlayer;

    public void gameSetup() {

        ArrayList<String> data = fileIO.readGameData();
        if (data.isEmpty()) {
            data = textUI.getUserInput("Write playername. Press Q to quit.", 6);
        }
        createPlayers(data);

        String[] fieldData = fileIO.readBoardData();
        ArrayList<String> chanceData = fileIO.readChanceData();

        board = new Board(fieldData, chanceData);
    }

    public void saveGame(){
        FileIO.writeGameData(players);
    }

    public void runGame(){

        String input = "";

        int count = 0;

        while(!input.equalsIgnoreCase("q")) {

            currentPlayer = players.get(count);
            count++;
            throwAndMove();
            input = textUI.getUserInput("Continue(C) or quit (Q) ?");
            if(count == players.size()){
                count = 0;
            }
        }

    }
    private void throwAndMove(){

        System.out.println("It is "+currentPlayer.getName()+"'s turn. \n"
                +currentPlayer
                + " is on field "
                +currentPlayer.getPosition());

        int result = 4;//Dice.rollDiceSum();//Det er denne linie du skal ændre for at teste forskellige felter!


        int newPos = currentPlayer.updatePos(result);
        Field f = board.getField(newPos);
        landAndAct(f);
    }

    private void landAndAct(Field f){

        String optionMsg = f.onLand(currentPlayer);
        String choice = textUI.getUserInput(optionMsg);
        String msg = f.processChoice(choice, currentPlayer);
        textUI.displayMessage(msg+  "\n STATUS:"+currentPlayer);

    }

    private void createPlayers(ArrayList<String> data){

        for (String s : data) {
            String[] values = s.replaceAll(" ","").split(","); //split arrayet så vi får adskildt de to værdier
            int balance;

            if (values.length > 1) {
                balance = Integer.parseInt(values[1]);
            } else {
                balance = Integer.parseInt("30000");
            }
            Player p = new Player(values[0], balance); // brug de to værdier til at lave en ny Player instans
            players.add(p);                            // tilføj Player instansen til array'et af spillere
        }

    }

    public void displayPlayers() {
        for (Player p:players) {
            System.out.println(p);
        }
    }
}