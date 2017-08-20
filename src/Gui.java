import javax.swing.*;
import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.stream.IntStream;


public class Gui extends JFrame implements MouseListener {
    //----------Graphic------------------------------------
    JPanel panel = new JPanel();
    Field buttons[] = new Field[9];
    static boolean gameType = false;

    static TicTacToe game = new TicTacToe();

    public static void main(String[] args) {
        new Gui();
    }


    public Gui() {
        super("Tic-Tac-Toe");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        if (!gameType) {
            setSize(500, 140);
            panel.setLayout(new GridLayout(3, 2));
            panel.add(new JLabel(" Choose Game Type"));
            JComboBox box = new JComboBox();
            box.addItem("Singleplayer");
            box.addItem("Multiplayer");
            panel.add(box);
            panel.add(new JLabel(" Write your Name"));
            JTextField input = new JTextField();
            panel.add(input);
            panel.add(new JButton("Confirm")).addMouseListener(this);
            panel.add(new JButton("Cancel"));
        } else {
            setSize(600, 600);
            panel.setLayout(new GridLayout(3, 3));
            for (int i = 0; i < 9; i++) {
                buttons[i] = new Field();
                panel.add(buttons[i]);
            }
        }
        add(panel);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gameType = true;
        setVisible(false);
        new Gui();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
