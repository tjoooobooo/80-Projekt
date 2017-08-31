import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Field extends JButton implements ActionListener {
    String[] stones = {"X.png", "O.png", "monkey.png", "cat.png", "penguin.png", "krone.png", "smiley.png"};
    ImageIcon[] icons = new ImageIcon[stones.length];
    static int val = 1;
    static int counter = 0;
    int fieldnumber = counter % 9;
    static TicTacToe t3 = new TicTacToe();
    static Integer buttonNumber;
    Frame2 game;

    static int p1Wins = 0;
    static int p2Wins = 0;
    static int draws = 0;

    public Field(Frame2 game) {
        this.game = game;
        counter++;
        this.addActionListener(this);
            for (int i = 0; i < stones.length; i++) {
                icons[i] = new ImageIcon("res/" + stones[i]);
                Image image = icons[i].getImage();
                Image newImage = image.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
                icons[i] = new ImageIcon(newImage);
            }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonNumber = fieldnumber;
        game.gameStoneP1.setEnabled(false);
        game.gameStoneP2.setEnabled(false);
        TicTacToe tmp = new TicTacToe();
        switch (val) {
            case 1:
                if (t3.isWin() || t3.isDraw()) break;
                setIcon(icons[game.gameStoneP1.getSelectedIndex()]);
                tmp = (TicTacToe) t3.makeMove(new Move(fieldnumber));
                break;
            case -1:
                if (t3.isWin() || t3.isDraw()) break;
                setIcon(icons[game.gameStoneP2.getSelectedIndex()]);
                tmp = (TicTacToe) t3.makeMove(new Move(fieldnumber));
                break;
            default:
                setIcon(null);
        }
        val = -val;
        t3 = tmp;
        check();
    }
        public void check() {
        if (Frame1.gameChoose == 2 && Frame1.network.isYourTurn()) {
            try {
                Frame1.network.dos.writeInt(fieldnumber);
                Frame1.network.dos.flush();
                Frame1.network.swapTurn();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (t3.isWin() || t3.isDraw()) gameOver(false);
                //--------------gegen Computer---------
            else if (Frame1.gameChoose == 1) {
                int zug = (new Algorithmen(t3).minimax()).getT3();
                TicTacToe tmp;
                tmp = (TicTacToe) t3.makeMove(new Move(zug));
                game.buttons[zug].setIcon(val == 1 ? icons[game.gameStoneP1.getSelectedIndex()] : icons[game.gameStoneP2.getSelectedIndex()]);
                val = -val;
                t3 = tmp;
                if (t3.isWin() || t3.isDraw()) gameOver(false);
                //--------------------------------------------------------------------------------------------
            }
        }

    public void gameOver(boolean giveUp) {
        // TODO turn ist falsch nachdem player1 gewinnt online
        int nextGame;
        if(Frame1.gameChoose == 2) Frame1.network.resetYourTurn();
        if(t3.isWin() || giveUp){
            if(val == -1) p1Wins++;
            else p2Wins++;
            nextGame = JOptionPane.showConfirmDialog(null, (val == -1 ? Frame1.name : Frame1.enemyName) + " has won\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
        } else {
            draws++;
            nextGame = JOptionPane.showConfirmDialog(null, "Game is draw\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
        }
        if(nextGame == 0) {
            val = 1;
            counter = 0;
            game.newGame();
            t3 = new TicTacToe();
        } else {
            try {
                Frame1.network.dos.writeUTF("!userDisconnected");
                Frame1.network.dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
        updateCounters();
    }
    public void updateCounters() {
        game.player1Wins.setText(String.valueOf(p1Wins));
        game.player2Wins.setText(String.valueOf(p2Wins));
        game.draws.setText(String.valueOf(draws));
    }
}
