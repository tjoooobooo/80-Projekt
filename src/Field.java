import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Field extends JButton implements ActionListener {
    ImageIcon X,O;
    static int val = 1;
    static int counter = 0;
    int fieldnumber = counter;
    static TicTacToe t3 = new TicTacToe();
    Gui game;

    public Field(Gui game){
        this.game = game;
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
        TicTacToe tmp = new TicTacToe();
        switch(val){
            case 1 :
                if(t3.isWin() || t3.isDraw()) break;
                setIcon(X);
                tmp = (TicTacToe) t3.makeMove(new Move(fieldnumber));
                break;
            case -1 :
                if(t3.isWin() || t3.isDraw()) break;
                setIcon(O);
                tmp = (TicTacToe) t3.makeMove(new Move(fieldnumber));
                break;
            default:
                setIcon(null);
        }
        t3 = tmp;
        System.out.println(t3.toString());
        if(t3.isWin() || t3.isDraw()) gameOver();
            else {
            int zug = (new Algorithmen(t3).minimax()).getT3();
            tmp = (TicTacToe) t3.makeMove(new Move(zug));
            val = -val;
            Gui.buttons[zug].setIcon(val == 1 ? X : O);
            t3 = tmp;
        }
        if(t3.isWin() || t3.isDraw()) {
                gameOver();
        }
        val = -val;
    }
    public void gameOver() {
        int nextGame;
        if(t3.isWin()){
            nextGame = JOptionPane.showConfirmDialog(null, "Player " + (val == 1 ? "X" : "O") + " has won\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
        } else nextGame = JOptionPane.showConfirmDialog(null, "Game is draw\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
        if(nextGame == 0) {
            val = -val;
            counter = 0;
            game.newGame();
            t3 = new TicTacToe();
        } else System.exit(0);
    }
}
