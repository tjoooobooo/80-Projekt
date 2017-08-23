import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Field extends JButton implements ActionListener {
    ImageIcon X,O,AFFE,KATZE,PENGUIN;
    ImageIcon[] icons = new ImageIcon[5];
    static int val = 1;
    static int counter = 0;
    int fieldnumber = counter;
    static TicTacToe t3 = new TicTacToe();
    Layout game;

    static int p1Wins = 0;
    static int p2Wins = 0;
    static int draws = 0;
    String[] stones = {"X.png","O.png","affe.jpg","cat.png","penguin.png"};
    public Field(Layout game){
        this.game = game;
        counter++;
        this.addActionListener(this);
        for(int i=0;i<5;i++) {
            icons[i] = new ImageIcon("res/" + stones[i]);
            Image image = icons[i].getImage();
            Image newImage = image.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            icons[i] = new ImageIcon(newImage);
        }
/*
        X = new ImageIcon("res/" + stones[Layout.gameStone.getSelectedIndex()]);
        Image imageX = X.getImage();
        Image newX = imageX.getScaledInstance(130,130, Image.SCALE_SMOOTH);
        X = new ImageIcon(newX);
        O = new ImageIcon("res/O.png");
        Image imageO = O.getImage();
        Image newO = imageO.getScaledInstance(130,130, Image.SCALE_SMOOTH);
        O = new ImageIcon(newO);*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(Layout.gameStone.getSelectedIndex());
        TicTacToe tmp = new TicTacToe();
        switch(val){
            case 1 :
                if(t3.isWin() || t3.isDraw()) break;
                setIcon(icons[Layout.gameStone.getSelectedIndex()]);
                tmp = (TicTacToe) t3.makeMove(new Move(fieldnumber));
                break;
            case -1 :
                if(t3.isWin() || t3.isDraw()) break;
                setIcon(icons[1]);
                tmp = (TicTacToe) t3.makeMove(new Move(fieldnumber));
                break;
            default:
                setIcon(null);
        }
        t3 = tmp;
        if(t3.isWin() || t3.isDraw()) gameOver();
            else if(Layout.againstPc) {
            int zug = (new Algorithmen(t3).minimax()).getT3();
            tmp = (TicTacToe) t3.makeMove(new Move(zug));
            val = -val;
            Layout.buttons[zug].setIcon(val == 1 ? X : O);
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
            if(val == 1) p1Wins++;
            else p2Wins++;
            nextGame = JOptionPane.showConfirmDialog(null, (val == 1 ? Layout.name : "Computer") + " has won\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
            val = -val;
        } else {
            draws++;
            nextGame = JOptionPane.showConfirmDialog(null, "Game is draw\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
        }
        if(nextGame == 0) {
            val = -val;
            counter = 0;
            game.newGame();
            t3 = new TicTacToe();
        } else System.exit(0);
        Layout.player1Wins.setText(String.valueOf(p1Wins));
        Layout.player2Wins.setText(String.valueOf(p2Wins));
        Layout.draws.setText(String.valueOf(draws));
    }
}
