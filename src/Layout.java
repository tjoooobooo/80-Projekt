import javax.swing.*;
import java.awt.*;

public class Layout extends JFrame {
    Layout(){
        setTitle("Tic Tac Toe");
        setSize(800,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        JPanel rightSide = new JPanel();
        rightSide.setLayout(new GridLayout(4,2));

        rightSide.add(new JLabel("let Computer play"));
        rightSide.add(new JButton("make move"));

        rightSide.add(new JLabel("Player 1 wins: "));
        rightSide.add(new JLabel("2"));
        rightSide.add(new JLabel("Player 2 wins: "));
        rightSide.add(new JLabel("2"));
        rightSide.add(new JLabel("Draws: "));
        rightSide.add(new JLabel("5"));

        panel.add(new Spielfeld(), BorderLayout.CENTER);
        panel.add(rightSide, BorderLayout.LINE_END);
        panel.add(new JTextArea("WASLOSHIER\nhalüü"), BorderLayout.PAGE_END);

       // getContentPane().add(new JLabel("HALOOO"), gbc);
        //getContentPane().add(new JLabel("HALOOO"), gbc);
        //getContentPane().add(new JButton("CLICK"));

        getContentPane().add(panel);
    }

    public static void main(String[] args) {
        new Layout();
    }
}
