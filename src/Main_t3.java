import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Philipp on 03.07.2017.
 */
@SuppressWarnings("Duplicates")
public class Main_t3 {
    static TicTacToe game = new TicTacToe();
    static Network network = new Network();

    public static void main(String[] args) throws IOException {
        int user_entry;
        System.out.println("Hallo User, das hier ist das Spiel ~Tic-Tac-Toe~");
        System.out.println("Um einen Zug zu machen, gib eine Zahl von 1-9 ein(Siehe Beispiel)");
        System.out.println("|1|2|3|\n" +
                "|4|5|6|\n" +
                "|7|8|9|");

        if (!network.isAccepted() && network.getisServer()) {
            network.listenForServerRequest();
        }

        while (true) {
            //network.evaluateInputStream();
            if (network.isYourTurn() && (!game.isWin() || !game.isDraw())) {
                user_entry = user_entry_t3() - 1;
                TicTacToe newGame = (TicTacToe) game.makeMove(new Move(user_entry));
                game = newGame;
                try {
                    //schicken
                    network.dos.writeInt(user_entry);
                    network.dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                network.swapTurn();
                System.out.println(game.toString());
                System.out.println("Der Gegner ist am Zug...");
            }
            if (!network.isYourTurn() && (network.dis.available() == 4) && (!game.isWin() || !game.isDraw())) {
                try {
                    //nachricht lesen
                    user_entry = network.dis.readInt();
                    TicTacToe newGame = (TicTacToe) game.makeMove(new Move(user_entry));
                    game = newGame;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                network.swapTurn();
                System.out.println(game.toString());
            }

            if (game.isWin() || game.isDraw()) {
                System.out.println(game.toString());
                if (game.isWin()) {
                    if (!game.isBeginnersTurn()) {
                        System.out.println("X hat gewonnen!");
                    } else {
                        System.out.println("O hat gewonnen!");
                    }
                } else {
                    System.out.println("Unentschieden!");
                }
                System.out.println("Gib \"new\" für ein neues Spiel ein, oder \"exit\" um das Spiel zu beenden!");
                System.out.println("[?: Hilfe]: _");
                break;
            }
        }
    }

    private static int user_entry_t3() throws IOException {
        boolean contains_check = false;
        boolean check = true;
        String test_str = "";
        int user_int = 0;
        Scanner scanner = new Scanner(System.in);
        while (check) {
            test_str = scanner.nextLine(); //speichere eingegebenen String
            if (isInteger(test_str)) {
                user_int = Integer.parseInt(test_str);
                if (user_int >= 1 && user_int <= 9) {
                    for (int i = 0; i < game.moves().size(); i++) {
                        if (((Move) game.moves().get(i)).getT3() == (user_int - 1)) {
                            contains_check = true;
                        }
                    }
                    if (contains_check) {
                        check = false;
                    } else {
                        System.out.println("Dieser Platz ist schon belegt, gib einen neuen Zug ein!");
                        System.out.println(game.toString());
                    }
                } else {
                    System.out.println("Ungültige Eingabe! Bitte gib eine Zahl von 0-9 ein!");
                    System.out.println(game.toString());
                }
            } else {
                switch (test_str.length() != 0 ? test_str.substring(0, 1).toLowerCase() : test_str) {
                    case "?"://Hilfe aufruf
                        help_t3();
                        System.out.println(game.toString());
                        break;
                    case "h"://Hilfe aufruf
                        help_t3();
                        System.out.println(game.toString());
                        break;
                    case "e"://Spiel beenden
                        network.dos.writeUTF("!userDisconnected");
                        network.dos.flush();
                        System.exit(0);
                        break;
                    case "k"://kickt den Client vom Server
                            network.dos.writeUTF("!kick");
                            network.evaluateInputStream("!kick");
                            network.dos.flush();
                        break;
                    default://Keiner der Befehle erkannt, also Fehlerhafte eingabe.
                        System.out.println("Unbekannter Befehl, gib \"help\" für eine Auswahl an Befehlen ein.: _");
                        System.out.println(game.toString());
                }
            }
        }
        return user_int;
    }

    private static void help_t3() {
        System.out.println("Spielen Sie Tic-Tac-Toe gegen den Computer oder gegen einen anderen Spieler.");
        System.out.println("Die Positionen des Spielfelds sind von 1-9 nummeriert");
        System.out.println("|1|2|3|\n" +
                "|4|5|6|\n" +
                "|7|8|9|");
        System.out.println("Es wird abwechselnd gezogen. Gewonnen hat," +
                " wer zuerst drei Spielsteine(entweder X oder O) dis Reihe anordnet");
        System.out.println("1-9     Position des Feldes, das man besetzen möchte");
        System.out.println("exit    Beendet das Programm");
        System.out.println("help    Zeigt diese Übersicht an");
        System.out.println("kick    Kickt den Client vom Server.(Nicht als Client verfügbar!)");
        System.out.println("?       Wie \"help_t3\"");
    }

    private static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isBoolean(String string) {
        try {
            Boolean.getBoolean(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
