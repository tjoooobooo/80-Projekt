import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame2 extends JFrame {

    public Field[] buttons = new Field[9];
    private static JPanel field = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    JTextPane jTextPane1 = new JTextPane();
    JComboBox<String> gameStone = new JComboBox<>();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel player1Wins = new JLabel();
    JLabel player2Wins = new JLabel();
    JLabel draws = new JLabel();
    JLabel jLabel8 = new JLabel();
    JComboBox<String> background = new JComboBox<>();
    JCheckBox againstComputer = new JCheckBox();
    JButton recPlay = new JButton();


    Frame2(Frame1 frame1) {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(600, 600));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new Field(this);
            field.add(buttons[i]);
        }

        field.setPreferredSize(new Dimension(400, 400));
        field.setLayout(new GridLayout(3, 3));
        jScrollPane1.setViewportView(jTextPane1);

        jLabel2.setText(frame1.name + " wins:");
        jLabel3.setText(frame1.enemyName + " wins:");
        jLabel4.setText("Draws: ");
        player1Wins.setText("0");
        player2Wins.setText("0");
        draws.setText("0");
        againstComputer.setText("Play against Computer"); // muss raus

        jLabel1.setText("Select Stone");
        DefaultComboBoxModel stones = new DefaultComboBoxModel<>(new String[]{"X","O","Monkey","Cat", "Penguin","Crown","Smiley"});
        gameStone.setModel(stones);

        jLabel8.setText("Select Background");
        background.setModel(new DefaultComboBoxModel<>(new String[]{"default", "green", "blue", "pink", "yellow"}));
        Color[] colors = {null, Color.green, Color.blue, Color.pink, Color.yellow};
        background.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Field button : buttons){
                    button.setBackground(colors[background.getSelectedIndex()]);
                }
            }
        });

        recPlay.setText("show recommended play");
        recPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int num = (new Algorithmen(Field.t3).minimax()).getT3();
                Thread thread = new Thread(() -> {
                    int counter = 0;
                    while(counter++ < 4){
                        if(buttons[num].getBackground() == Color.GREEN) buttons[num].setBackground(colors[background.getSelectedIndex()]);
                        else buttons[num].setBackground(Color.GREEN);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        buttons[num].repaint();
                    }
                });
                thread.setPriority(2);
                thread.start();
            }
        });



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
                                                                        .addComponent(recPlay))
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
                                                .addComponent(recPlay)
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

    public void newGame() {
        //setVisible(false);
        gameStone.setEnabled(true);
        for (int i = 0; i < 9; i++) buttons[i].setIcon(null);
        //new Layout();
    }
}
