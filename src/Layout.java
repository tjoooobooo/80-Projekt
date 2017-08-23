import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

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
    static JLabel player1Wins;
    static JLabel player2Wins;
    static JLabel draws;
    private JLabel jLabel8;
    private JPanel field;
    private JScrollPane jScrollPane1;
    private JTextPane jTextPane1;
    static boolean againstPc = false;
    static String name;

    public Layout() {

        field = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTextPane1 = new JTextPane();
        gameStone = new JComboBox<>();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        player1Wins = new JLabel();
        player2Wins = new JLabel();
        draws = new JLabel();
        jLabel8 = new JLabel();
        background = new JComboBox<>();
        againstComputer = new JCheckBox();
        jButton10 = new JButton();
        jButton10 = new JButton();
        //-------erstes Fenster----------
        JComboBox gameType = new JComboBox();
        JTextField input = new JTextField();
        JButton button1, button2;
        name = "Player1";
        //--------------------------------
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        jScrollPane1.setViewportView(jTextPane1);

        gameStone.setModel(new DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jLabel1.setText("Select Stone");
        jLabel2.setText("Player1 wins:");
        jLabel3.setText("Player2 wins:");
        jLabel4.setText("Draws: ");
        player1Wins.setText("0");
        player2Wins.setText("0");
        draws.setText("0");
        jLabel8.setText("Select Background");

        background.setModel(new DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        againstComputer.setText("Play against Computer");
        againstComputer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                againstPc = !againstPc;
            }
        });

        jButton10.setText("show recommended play");
        jButton10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

            }
        });
        if (!type) {
            setSize(500, 140);
            getContentPane().setLayout(new GridLayout(3, 2));
            add(new JLabel(" Choose Game Type"));
            gameType.addItem("Singleplayer");
            gameType.addItem("Multiplayer");
            add(gameType);
            add(new JLabel(" Write your Name"));
            add(input);
            button1 = new JButton("Confirm");
            button2 = new JButton("Cancel");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    name = input.getText();
                    System.out.println(name);
                    type = !type;
                    setVisible(false);
                    new Layout();
                }
            });
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            add(button1);
            add(button2);
            setVisible(true);
        } else {
            //setPreferredSize(new java.awt.Dimension(610, 600));
            setSize(new java.awt.Dimension(600, 600));
            field.setPreferredSize(new Dimension(400, 400));
            field.setLayout(new GridLayout(3, 3));

            for (int i = 0; i < 9; i++) {
                buttons[i] = new Field(this);
                field.add(buttons[i]);
            }
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addGap(0, 18, Short.MAX_VALUE)
                                                                    .addComponent(jLabel8)
                                                                    .addGap(18, 18, 18)
                                                                    .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(jLabel1)
                                                                    .addGap(18, 18, 18)
                                                                    .addComponent(gameStone, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addComponent(againstComputer)
                                                                            .addGroup(layout.createSequentialGroup()
                                                                                    .addGap(17, 17, 17)
                                                                                    .addComponent(jLabel2)
                                                                                    .addGap(18, 18, 18)
                                                                                    .addComponent(player1Wins))
                                                                            .addGroup(layout.createSequentialGroup()
                                                                                    .addGap(17, 17, 17)
                                                                                    .addComponent(jLabel3)
                                                                                    .addGap(18, 18, 18)
                                                                                    .addComponent(player2Wins))
                                                                            .addGroup(layout.createSequentialGroup()
                                                                                    .addGap(44, 44, 44)
                                                                                    .addComponent(jLabel4)
                                                                                    .addGap(18, 18, 18)
                                                                                    .addComponent(draws))
                                                                            .addComponent(jButton10))
                                                                    .addGap(0, 0, Short.MAX_VALUE)))))
                                    .addContainerGap())
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(11, 11, 11)
                                                    .addComponent(againstComputer)
                                                    .addGap(7, 7, 7)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel2)
                                                            .addComponent(player1Wins))
                                                    .addGap(11, 11, 11)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel3)
                                                            .addComponent(player2Wins))
                                                    .addGap(11, 11, 11)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel4)
                                                            .addComponent(draws))
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jButton10)
                                                    .addGap(157, 157, 157)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel8)
                                                            .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(18, 18, 18)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(gameStone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGap(1, 1, 1)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            pack();
            setVisible(true);
        }
    }
    public  void newGame() {
        type = true;
        //setVisible(false);
        for(int i=0; i<9; i++) buttons[i].setIcon(null);
        //new Layout();
    }
    public static void main(String args[]) {
        new Layout();
    }
}
