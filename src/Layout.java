import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Layout extends JFrame {
    static boolean type = false;
    public Field[] buttons = new Field[9];

    private JButton jButton10;
    private JCheckBox againstComputer;
    public JComboBox<String> gameStone;
    public JComboBox<String> background;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    public JLabel player1Wins;
    public JLabel player2Wins;
    public JLabel draws;
    private JLabel jLabel8;
    private JPanel field;
    private JScrollPane jScrollPane1;
    private JTextPane jTextPane1;
    static String name;
    static String enemyName;
    public JComboBox gameType;
    public static int gameChoose;

    Network network = new Network();

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
        JTextField inputName = new JTextField();
        JTextField inputEnemyName = new JTextField();
        JTextField inputIP = new JTextField();
        JButton button1, button2;
        //--------------------------------
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        jScrollPane1.setViewportView(jTextPane1);

        jLabel1.setText("Select Stone");
        jLabel2.setText(name + " wins:");
        jLabel3.setText(enemyName + " wins:");
        jLabel4.setText("Draws: ");
        player1Wins.setText("0");
        player2Wins.setText("0");
        draws.setText("0");
        jLabel8.setText("Select Background");

        DefaultComboBoxModel stones = new DefaultComboBoxModel<>(new String[]{"X","O","Monkey","Cat", "Penguin","Crown","Smiley"});
        gameStone.setModel(stones);

        background.setModel(new DefaultComboBoxModel<>(new String[]{"default", "green", "blue", "pink", "yellow"}));
        background.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color[] colors = {null, Color.green, Color.blue, Color.pink, Color.yellow};
                for(Field button : buttons){
                    button.setBackground(colors[background.getSelectedIndex()]);
                }
            }
        });

        againstComputer.setText("Play against Computer");

        jButton10.setText("show recommended play");
        jButton10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

            }
        });
        if (!type) {
            setSize(350, 140);
            getContentPane().setLayout(new GridLayout(4, 2));
            add(new JLabel(" Choose Game Type"));
            gameType = new JComboBox(new String[]{"Local(PVP)", "Local(PVC)", "Online(PVP"});
            add(gameType);
            add(new JLabel(" Write your Name"));
            JLabel names = new JLabel();
            names.setLayout(new GridLayout(1,2));
            names.add(inputName);
            names.add(inputEnemyName);
            add(names);
            add(new JLabel("Choose IP-Adress"));
            add(inputIP);
            button1 = new JButton("Confirm");
            button2 = new JButton("Cancel");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //-------Netzwerk verbinden----------------
                    if(gameType.getSelectedIndex() == 2) {
                            network.setIP(inputIP.getText());
                    }
                    //-----------------------------------------
                    gameChoose = gameType.getSelectedIndex();
                    name = inputName.getText().isEmpty() ? "Player1" : inputName.getText();
                    enemyName = inputEnemyName.getText().isEmpty() ? "Computer" : inputEnemyName.getText();
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
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addGap(0, 18, Short.MAX_VALUE)
                                                                    .addComponent(jLabel8)
                                                                    .addGap(18, 18, 18)
                                                                    .addComponent(background, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(jLabel1)
                                                                    .addGap(18, 18, 18)
                                                                    .addComponent(gameStone, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
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
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(11, 11, 11)
                                                    .addComponent(againstComputer)
                                                    .addGap(7, 7, 7)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel2)
                                                            .addComponent(player1Wins))
                                                    .addGap(11, 11, 11)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel3)
                                                            .addComponent(player2Wins))
                                                    .addGap(11, 11, 11)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel4)
                                                            .addComponent(draws))
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jButton10)
                                                    .addGap(157, 157, 157)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel8)
                                                            .addComponent(background, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                    .addGap(18, 18, 18)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(gameStone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                    .addGap(1, 1, 1)
                                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
            );
            pack();
            setVisible(true);
        }
    }
    public int getGameType() {
        return gameType.getSelectedIndex();
    }
    public  void newGame() {
        type = true;
        //setVisible(false);
        gameStone.setEnabled(true);
        for(int i=0; i<9; i++) buttons[i].setIcon(null);
        //new Layout();
    }
    public static void main(String args[]) {
        new Layout();
    }
}
