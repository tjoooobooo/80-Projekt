import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Field extends JButton implements ActionListener {
    ImageIcon icon;
    String[] stones = {"X.png","O.png","monkey.png","cat.png","penguin.png","krone.png", "smiley.png"};
    ImageIcon[] icons = new ImageIcon[stones.length];
    static int val = 1;
    static int counter = 0;
    int fieldnumber = counter;
    static TicTacToe t3 = new TicTacToe();
    Layout game;

    static int p1Wins = 0;
    static int p2Wins = 0;
    static int draws = 0;

    public Field(Layout game){
        this.game = game;
        counter++;
        this.addActionListener(this);
        for(int i=0;i<stones.length;i++) {
            icons[i] = new ImageIcon("res/" + stones[i]);
            Image image = icons[i].getImage();
            Image newImage = image.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            icons[i] = new ImageIcon(newImage);
        }
        /*--------AFFE GIF---------------
        File file = new File("res/monkey.gif");
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        icon = new ImageIcon(url);*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(Layout.gameChoose);
        game.gameStone.setEnabled(false);
        TicTacToe tmp = new TicTacToe();
        switch(val){
            case 1 :
                if(t3.isWin() || t3.isDraw()) break;
                setIcon(icons[game.gameStone.getSelectedIndex()]);
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
        val = -val;
        t3 = tmp;
        if(t3.isWin() || t3.isDraw()) gameOver();
            else if(Layout.gameChoose == 1) {
            int zug = (new Algorithmen(t3).minimax()).getT3();
            tmp = (TicTacToe) t3.makeMove(new Move(zug));
            game.buttons[zug].setIcon(val == 1 ? icons[game.gameStone.getSelectedIndex()] : icons[1]);
            val = -val;
            t3 = tmp;
        }
        if(t3.isWin() || t3.isDraw()) {
                gameOver();
        }
    }
    public void gameOver() {
        int nextGame;
        if(t3.isWin()){
            if(val == -1) p1Wins++;
            else p2Wins++;
            nextGame = JOptionPane.showConfirmDialog(null, (val == -1 ? game.name : game.enemyName) + " has won\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
        } else {
            draws++;
            nextGame = JOptionPane.showConfirmDialog(null, "Game is draw\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
        }
        if(nextGame == 0) {
            val = 1;
            counter = 0;
            game.newGame();
            t3 = new TicTacToe();
        } else System.exit(0);
        game.player1Wins.setText(String.valueOf(p1Wins));
        game.player2Wins.setText(String.valueOf(p2Wins));
        game.draws.setText(String.valueOf(draws));
    }
}
