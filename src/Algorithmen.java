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
        if(game instanceof Muehle) this.game = (Muehle)game;
        else this.game = (TicTacToe)game;
    }
    Move minimax() {
        int res = max(+1, gewuenschteTiefe, Integer.MIN_VALUE, Integer.MAX_VALUE);
        //if (game instanceof TicTacToe && new Algorithmen(game.makeMove(gespeicherterZug)).bewerten(turn) ==  0) new Algorithmen(game).monteCarlo();
        return gespeicherterZug;
    }
    int bewerten(int spieler) {
        if (game.isWin()) return -spieler;
        else return 0;
    }
    //Heuristik für Mühle (Monte-Carlo dauert zu lange)
    int bewertenMorris(int spieler) {
        Muehle morris = (Muehle) game;
        int score = 0;
        score += morris.freeNeighbours();
        if(morris.getIsMill()) score += -spieler*100;
        if(morris.getBrainThree().size() > 0){
            for(int[] mill : morris.getBrainThree()){
                if(morris.getBoard()[mill[0]] == -spieler) score += 100;
                else score -= 100;
            }
        }

        int p1 = 0;
        int p2 = 0;
        for(int i = 0; i<morris.getBoard().length;i++){
            if(morris.getBoard()[i] == -spieler) p1++;
            if(morris.getBoard()[i] == spieler) p2++;
        }
        //score += 100*(p1-p2);
        if (morris.isWin()) score = Integer.MAX_VALUE;
        //System.out.println(game_m+"\n"+score);
        return score;
    }
    int max(int spieler, int tiefe, int alpha, int beta) {
        if (tiefe == 0  || game.isWin() || game.isDraw()) {
            if(game instanceof TicTacToe) return bewerten(spieler);
            else return bewertenMorris(spieler);
        }
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
        if (tiefe == 0 || game.isWin() || game.isDraw()) {
            if(game instanceof TicTacToe) return bewerten(spieler);
            else return bewertenMorris(spieler);
        }
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
    void monteCarlo() {
        if (game.isWin()) return;
        ImmutableBoard firstGame = game.flip().flip();
        int zuege = 200;
        Random r = new Random();
        int counter = 0;
        int bestMonteCarlo = 0;
        List<int[]> results = new ArrayList<>();
        for (Move p : game.moves()) {
            ImmutableBoard newGame = game.makeMove(p);
            int[] count = {0, 0, 0};
            for (int i = 0; i < zuege; i++) {
                while (!newGame.isWin()) {
                    int pos = newGame.moves().size();
                    if (pos == 0) break;
                    game = newGame.makeMove(newGame.moves().get(r.nextInt(pos)));
                    newGame = game;
                    counter++;
                }
                count[bewerten(turn) + 1] += 1;
                for (int j = 0; j < counter; j++) {
                    game = newGame.undoMove();
                    newGame = game;
                }
                counter = 0;
            }
            results.add(count);
        }
        if (turn == 1) {
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i)[2] > results.get(bestMonteCarlo)[2]) {
                    bestMonteCarlo = results.indexOf(results.get(i));
                }
            }
        } else {
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i)[2] < results.get(bestMonteCarlo)[2]) {
                    bestMonteCarlo = results.indexOf(results.get(i));
                }
            }
        }
        gespeicherterZug = (Move)firstGame.moves().get(bestMonteCarlo);
    }
}
