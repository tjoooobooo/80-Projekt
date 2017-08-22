import javax.swing.*;
import java.awt.*;

public class Spielfeld extends JPanel {

    Spielfeld(){
        setLayout(new GridLayout(3,3));
        Dimension size = getPreferredSize();
        size.width = 600;
        setPreferredSize(size);
        for (int i = 0; i < 9; i++) add(new JButton());

    }
}
