import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Gui extends JFrame implements MouseListener {
    //----------Graphic------------------------------------
    JPanel panel = new JPanel();
    static Field buttons[] = new Field[9];
    static boolean gameType = false;
    JButton button1, button2;
    static Gui game;

    public static void main(String[] args) { game = new Gui(); }


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
            panel.add(button1 = new JButton("Confirm")).addMouseListener(this);
            panel.add(button2 = new JButton("Cancel")).addMouseListener(this);
        } else {
            setSize(600, 600);
            panel.setLayout(new GridLayout(3, 3));
            for (int i = 0; i < 9; i++) {
                buttons[i] = new Field(this);
                panel.add(buttons[i]);
            }
        }
        add(panel);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == button1){
            gameType = true;
            setVisible(false);
            new Gui();
        } else if(e.getSource() == button2) System.exit(0);

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
    public  void newGame() {
        gameType = true;
        setVisible(false);
        new Gui();
    }
}
