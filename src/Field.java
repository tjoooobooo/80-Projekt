import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class Field extends JButton implements ActionListener {
    ImageIcon X,O;
    static int val = 1;
    int player = 0;
    static int counter = 0;
    int fieldnumber = counter;
    static int depth = 0;
    static int[] board = new int[9];
    //1 = X --- 2 = O
    public Field(){
        X = new ImageIcon(this.getClass().getResource("X.png"));
        Image imageX = X.getImage();
        Image newX = imageX.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        X = new ImageIcon(newX);
        O = new ImageIcon(this.getClass().getResource("O.png"));
        Image imageO = O.getImage();
        Image newO = imageO.getScaledInstance(180,180, Image.SCALE_SMOOTH);
        O = new ImageIcon(newO);
        this.addActionListener(this);
        counter++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(val){
            case 1 :
                if(isWin() || isDraw()){
                    break;
                }
                setIcon(X);
                player = 1;
                board[fieldnumber] = 1;
                depth++;
                break;
            case -1 :
                if(isWin() || isDraw()) break;
                setIcon(O);
                player = -1;
                board[fieldnumber] = -1;
                depth++;
                break;
            default:
                setIcon(null);

        }
        for(int i = 0; i<9; i++) System.out.print(board[i]+" ,");
        System.out.println();
        val = -val;
    }

    public boolean isWin() {
        int[][] rows = {{0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}};
        if (depth < 5) return false;
        return IntStream.range(0,8).
                map(row -> Arrays.stream(rows[row]).
                        map(pos -> board[pos]).
                        sum()).
                map(Math::abs).
                filter(r -> r == 3).
                findAny().
                isPresent();
    }
    public boolean isDraw() {
        return depth >= 9;
    }

    public List moves() {
        List<Integer> z = new ArrayList<>();
        IntStream.range(0, 9).filter(i -> board[i] == 0).forEach(el -> z.add(el));
        return z;
    }
/*
    int max(int spieler, int tiefe, int alpha, int beta) {
        if (tiefe == 0  || isWin() || isDraw()) return bewerten(spieler);
        int maxWert = alpha;
        List<Integer> plays = moves();
        for (Move play : plays) {
            if (brain.containsAll(getHistory())) break;
            brain.add(getHistory());
            ImmutableBoard newGame = makeMove(play);
            int wert = min(-spieler, tiefe - 1, maxWert, beta);
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
        List<Integer> plays = moves();
        for (Move play : plays) {
            if (brain.containsAll(getHistory())) break;
            brain.add(getHistory());
            ImmutableBoard newGame = makeMove(play);
            int wert = max(-spieler, tiefe - 1, alpha, minWert);
            if (wert < minWert) {
                minWert = wert;
                if (minWert <= alpha) break;
            }
        }
        return minWert;
    }
    int bewerten(int spieler) {
        if (isWin()) return -spieler;
        else return 0;
    }*/
}
