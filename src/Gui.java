import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Gui extends JFrame implements MouseListener {
    //----------Graphic------------------------------------
    JPanel panel = new JPanel();
    static Field buttons[] = new Field[9];
    static boolean type = false;
    JButton button1, button2;
    static Gui game;
    JComboBox gameType = new JComboBox();
    JTextField input = new JTextField();
    static String name;

    public static void main(String[] args) { game = new Gui(); }

    JPanel window = new JPanel();



    public Gui() {
        super("Tic-Tac-Toe");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        if (!type) {
            setSize(500, 140);
            panel.setLayout(new GridLayout(3, 2));
            panel.add(new JLabel(" Choose Game Type"));
            gameType.addItem("Singleplayer");
            gameType.addItem("Multiplayer");
            panel.add(gameType);
            panel.add(new JLabel(" Write your Name"));
            panel.add(input);
            panel.add(button1 = new JButton("Confirm")).addMouseListener(this);
            panel.add(button2 = new JButton("Cancel")).addMouseListener(this);
        } else {
            JLabel field = new JLabel();
            setSize(600, 600);
            //panel.setLayout(new GridLayout(2,2));

            panel.setLayout(new GridLayout(3,3));
            for (int i = 0; i < 9; i++) {
                //buttons[i] = new Field(this);
                panel.add(buttons[i]);
            }
            /*panel.add(field);
            panel.add(new JLabel("hi spielan"));
            panel.add(new JLabel("hi spielan"));
            panel.add(new JLabel("hi spielan"));*/
        }
        add(panel);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        name = input.getText();
        if(e.getSource() == button1){
            type = true;
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
        type = true;
        setVisible(false);
        new Gui();
    }
}
