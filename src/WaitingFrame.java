import javax.swing.*;
import java.awt.*;

public class WaitingFrame extends JFrame{

    public WaitingFrame(){
        setSize(200,150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,2));
        ImageIcon loadingGif = new ImageIcon("res/ajax-loader.gif");
        JLabel label = new JLabel();
        label.setIcon(loadingGif);
        add(label,BorderLayout.CENTER);
        add(new JLabel("Waiting for other player"));
        add(new JButton("OK"));
        add(new JButton("Cancel"));
        pack();
        setVisible(true);
    }
}
