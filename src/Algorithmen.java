import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Thomas on 30.06.2017.
 */
public class Algorithmen{
    Move gespeicherterZug = new Move(new int[]{9});
    int gewuenschteTiefe = 9;
    List<List<Move>> brain = new ArrayList<List<Move>>();
    ImmutableBoard<Move> game;
    int turn = +1;


    Algorithmen(ImmutableBoard<Move> game){
        if(game.isBeginnersTurn()) turn = 1;
        else turn = -1;
        this.game = (TicTacToe)game;
    }
    Move minimax() {
        int res = max(+1, gewuenschteTiefe, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return gespeicherterZug;
    }
    int bewerten(int spieler) {
        if (game.isWin()) return -spieler;
        else return 0;
    }
    //Heuristik für Mühle (Monte-Carlo dauert zu lange)

    int max(int spieler, int tiefe, int alpha, int beta) {
        if (tiefe == 0  || game.isWin() || game.isDraw()) return bewerten(spieler);
        int maxWert = alpha;
        List<Move> plays = game.moves();
        for (Move play : plays) {
            if (brain.containsAll(game.getHistory())) break;
            brain.add(game.getHistory());
            ImmutableBoard newGame = game.makeMove(play);
            int wert = new Algorithmen(newGame).min(-spieler, tiefe - 1, maxWert, beta);
            if (wert > maxWert) {
                maxWert = wert;
                if (maxWert >= beta) break;
                if (tiefe == gewuenschteTiefe) gespeicherterZug = play;
            }
        }
        return maxWert;
    }

    int min(int spieler, int tiefe, int alpha, int beta) {
        if (tiefe == 0 || game.isWin() || game.isDraw()) return bewerten(spieler);

        int minWert = beta;
        List<Move> plays = game.moves();
        for (Move play : plays) {
            if (brain.containsAll(game.getHistory())) break;
            brain.add(game.getHistory());
            ImmutableBoard newGame = game.makeMove(play);
            int wert = new Algorithmen(newGame).max(-spieler, tiefe - 1, alpha, minWert);
            if (wert < minWert) {
                minWert = wert;
                if (minWert <= alpha) break;
            }
        }
        return minWert;
    }
}
