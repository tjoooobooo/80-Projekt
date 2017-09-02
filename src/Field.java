import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Field extends JButton implements ActionListener {
    static String[] stones = {"X.png", "O.png", "monkey.png", "cat.png", "penguin.png", "krone.png", "smiley.png"};
    static ImageIcon[] icons = new ImageIcon[stones.length];
    static int val = 1;
    static int counter = 0;
    int fieldnumber = counter % 9;
    static TicTacToe t3 = new TicTacToe();
    static Integer buttonNumber;
    Frame2 secondFrame;

    public Field(Frame2 secondFrame) {
        this.secondFrame = secondFrame;
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
        if(t3.getBoard()[fieldnumber] != 0) {
            JOptionPane.showMessageDialog(null, "This place is already taken!", "Wrong move", JOptionPane.OK_OPTION);

        } else if(secondFrame.gameStoneP1.getSelectedIndex() == secondFrame.gameStoneP2.getSelectedIndex()) {
            JOptionPane.showMessageDialog(null,"You should select two different stones", "Invalid stone selection",JOptionPane.OK_OPTION);

        } else {
            buttonNumber = fieldnumber;
            secondFrame.gameStoneP1.setEnabled(false);
            secondFrame.gameStoneP2.setEnabled(false);
            TicTacToe tmp = new TicTacToe();
            switch (val) {
                case 1:
                    if (t3.isWin() || t3.isDraw()) break;
                    setIcon(icons[secondFrame.gameStoneP1.getSelectedIndex()]);
                    tmp = (TicTacToe) t3.makeMove(new Move(fieldnumber));
                    break;
                case -1:
                    if (t3.isWin() || t3.isDraw()) break;
                    setIcon(icons[secondFrame.gameStoneP2.getSelectedIndex()]);
                    tmp = (TicTacToe) t3.makeMove(new Move(fieldnumber));
                    break;
                default:
                    setIcon(null);
            }
            val = -val;
            t3 = tmp;
            secondFrame.updateButtons(!secondFrame.firstFrame.network.isYourTurn());
            secondFrame.check(fieldnumber);
        }

    }

}
