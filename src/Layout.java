import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Layout extends JFrame {
    static boolean type = false;
    static Field[] buttons = new Field[9];

    private JButton jButton10;
    private JCheckBox againstComputer;
    private JComboBox<String> gameStone;
    private JComboBox<String> background;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel field;
    private JScrollPane jScrollPane1;
    private JTextPane jTextPane1;

    public Layout() {
        initComponents();
    }

    private void initComponents() {

        field = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTextPane1 = new JTextPane();
        gameStone = new JComboBox<>();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        background = new JComboBox<>();
        againstComputer = new JCheckBox();
        jButton10 = new JButton();
        jButton10 = new JButton();


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 600));
        setSize(new java.awt.Dimension(600, 600));
        getContentPane().setLayout(new GridBagLayout());

        field.setPreferredSize(new Dimension(400, 400));
        field.setLayout(new GridLayout(3,3));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new Field(this);
            field.add(buttons[i]);
        }

        jScrollPane1.setViewportView(jTextPane1);

        gameStone.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Select Stone");

        jLabel2.setText("Player1 wins:");

        jLabel3.setText("Player2 wins:");

        jLabel4.setText("Draws: ");

        jLabel5.setText("jLabel5");

        jLabel6.setText("jLabel6");

        jLabel7.setText("jLabel7");

        jLabel8.setText("Select Background");

        background.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        againstComputer.setText("Play against Computer");
        againstComputer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

            }
        });

        jButton10.setText("show recommended play");
        jButton10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

            }
        });

        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.ipadx = 181;
        gridBagConstraints.ipady = 331;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        getContentPane().add(field, gridBagConstraints);

        jScrollPane1.setViewportView(jTextPane1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 602;
        gridBagConstraints.ipady = 154;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 10, 11, 10);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        gameStone.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 4, 0, 0);
        getContentPane().add(gameStone, gridBagConstraints);

        jLabel1.setText("Select Stone");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 46, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setText("Player1 wins:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 35, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setText("Player2 wins:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 35, 0, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        jLabel4.setText("Draws: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 62, 0, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setText("jLabel5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 11, 0, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel6.setText("jLabel6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 0, 0);
        getContentPane().add(jLabel6, gridBagConstraints);

        jLabel7.setText("jLabel7");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 0, 0);
        getContentPane().add(jLabel7, gridBagConstraints);

        jLabel8.setText("Select Background");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(160, 18, 0, 0);
        getContentPane().add(jLabel8, gridBagConstraints);

        background.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(157, 4, 0, 0);
        getContentPane().add(background, gridBagConstraints);

        againstComputer.setText("Play against Computer");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(22, 18, 0, 0);
        getContentPane().add(againstComputer, gridBagConstraints);

        jButton10.setText("show recommended play");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 0);
        getContentPane().add(jButton10, gridBagConstraints);

        pack();
    }
    public  void newGame() {
        type = true;
        setVisible(false);
        new Layout();
    }
    public static void main(String args[]) {
        new Layout().setVisible(true);
    }
}
