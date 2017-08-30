import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame2 extends JFrame {

    public Field[] buttons = new Field[9];

    public JPanel countersPanel = new JPanel();
    public JPanel optionsPanel = new JPanel();
    public  JPanel field = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    JTextPane chatInputPane = new JTextPane();

    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JLabel player1Wins = new JLabel();
    JLabel player2Wins = new JLabel();
    JLabel draws = new JLabel();

    JPanel gameStones = new JPanel();
    JComboBox<String> gameStoneP1 = new JComboBox<>();
    JComboBox<String> gameStoneP2 = new JComboBox<>();
    JComboBox<String> background = new JComboBox<>();

    JButton recPlay = new JButton("show recommended play");
    JButton sendChatButton = new JButton("send");
    JButton optionsButton = new JButton("back to game options");
    JButton resetButton = new JButton("reset");
    JButton giveUpButton = new JButton("give up");

    JLabel chatTextField = new JLabel();

    Frame2(Frame1 frame1) {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false);
        setSize(new java.awt.Dimension(600, 600));
        //-------wins & draws counters---------------------------------------------------------
        countersPanel.setLayout(new java.awt.GridLayout(3, 3, 15, 20));
        jLabel2.setText(frame1.name + " wins:");
        countersPanel.add(jLabel2);
        player1Wins.setText("0");
        countersPanel.add(player1Wins);
        jLabel3.setText(frame1.enemyName + " wins:");
        countersPanel.add(jLabel3);
        player2Wins.setText("0");
        countersPanel.add(player2Wins);
        jLabel4.setText("Draws: ");
        countersPanel.add(jLabel4);
        draws.setText("0");
        countersPanel.add(draws);
        // TODO Namen online übertragen und an richtiger stelle anzeigen
        //--------background and stone selection--------------------------------------------
        optionsPanel.setLayout(new java.awt.GridLayout(4, 1, 0, 5));
        jLabel1.setText("Select Stone");
        optionsPanel.add(jLabel1);
        String [] stoneTypes = {"X","O","Monkey","Cat", "Penguin","Crown","Smiley"};
        gameStoneP1.setModel(new DefaultComboBoxModel<>(stoneTypes));
        gameStoneP2.setModel(new DefaultComboBoxModel<>(stoneTypes));
        gameStoneP2.setSelectedIndex(1);
        gameStones.setLayout(new GridLayout());
        gameStones.add(gameStoneP1);
        gameStones.add(gameStoneP2);
        optionsPanel.add(gameStones);
        jLabel8.setText("Select Background");
        optionsPanel.add(jLabel8);
        background.setModel(new DefaultComboBoxModel<>(new String[]{"default", "blue", "pink", "yellow"}));
        Color[] colors = {null, Color.blue, Color.pink, Color.yellow};
        optionsPanel.add(background);
        background.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Field button : buttons){
                    button.setBackground(colors[background.getSelectedIndex()]);
                }
            }
        });
        //-------Tic Tac Toe field-------------------
        field.setPreferredSize(new Dimension(400, 400));
        field.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new Field(this);
            field.add(buttons[i]);
        }
        //------Chat--------------------------------------
        chatTextField.setText("HI");
        jScrollPane1.setViewportView(chatInputPane);

        recPlay.addActionListener(evt -> {
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
        });
        //-----Buttons----------------------------------------------
        giveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO online muss richtig bestimmt werden wer aufgibt
                buttons[0].gameOver(true);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Field.p1Wins = Field.p2Wins = Field.draws = 0;
                buttons[0].updateCounters();
                Field.t3 = new TicTacToe();
                gameStoneP1.setSelectedIndex(0);
                gameStoneP2.setSelectedIndex(1);
                background.setSelectedIndex(0);
                newGame();
            }
        });
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Field.t3 = new TicTacToe();
                setVisible(false);
                new Frame1();
            }
        });

        sendChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO send Button
            }
        });
        //--------Layout-----------------------------------------------------------------------------------------------------------------------------------------------------------
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(sendChatButton))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(countersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(giveUpButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addGap(18, 18, 18)
                                                                                        .addComponent(resetButton))
                                                                                .addComponent(optionsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(recPlay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(chatTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(420, 420, 420)
                                .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(countersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(recPlay)
                                                .addGap(18, 18, 18)
                                                .addComponent(optionsButton)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(giveUpButton)
                                                        .addComponent(resetButton))
                                                .addGap(24, 24, 24)
                                                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addComponent(chatTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(sendChatButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1))
                                .addContainerGap())
        );

        pack();
        setVisible(true);
    }

    public void newGame() {
        //setVisible(false);
        gameStoneP1.setEnabled(true);
        gameStoneP2.setEnabled(true);
        for (int i = 0; i < 9; i++) buttons[i].setIcon(null);
        //new Layout();
    }
}
