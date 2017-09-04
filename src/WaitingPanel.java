import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaitingPanel extends JPanel{

    public WaitingPanel(){
        //setSize(200,150);
        setLayout(new BorderLayout());
        ImageIcon loadingGif = new ImageIcon("res/ajax-loader.gif");
        JLabel label = new JLabel("waiting for other player",loadingGif,SwingConstants.CENTER);
        add(label,BorderLayout.CENTER);
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exit,BorderLayout.PAGE_END);
        setVisible(true);
    }
}
