import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

class Frame2 extends JFrame {
    Field[] buttons = new Field[9];
    private JTextPane chatInputPane = new JTextPane();

    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    private JLabel player1Wins = new JLabel();
    private JLabel player2Wins = new JLabel();
    private JLabel draws = new JLabel();

    JComboBox<String> gameStoneP1 = new JComboBox<>();
    JComboBox<String> gameStoneP2 = new JComboBox<>();
    private JComboBox<String> background = new JComboBox<>();

    JButton optionsButton = new JButton("back to game options");
    private JButton giveUpButton = new JButton("give up");

    JTextArea chatTextField = new JTextArea();
    private StringBuilder sB = new StringBuilder();
    Frame1 firstFrame;
    boolean serverActive = true;

    private static int p1WinsCounter = 0;
    private static int p2WinsCounter = 0;
    private static int drawsCounter = 0;

    private int p1Stone;
    private int p2Stone;

    Frame2(Frame1 frame1) {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        if (Frame1.gameChoose == 2) setSize(new Dimension(600, 600));
        else setPreferredSize(new Dimension(650, 480));
        chatTextField.setEditable(false);
        firstFrame = frame1;
        //-------wins & draws counters---------------------------------------------------------
        JPanel countersPanel = new JPanel();
        countersPanel.setLayout(new java.awt.GridLayout(3, 3, 15, 20));
        jLabel2.setText(Frame1.name + " wins:");
        jLabel2.setOpaque(true);
        if (Frame1.gameChoose == 0) jLabel2.setBackground(Color.green);
        countersPanel.add(jLabel2);
        player1Wins.setText("0");
        countersPanel.add(player1Wins);
        jLabel3.setText(Frame1.enemyName + " wins:");
        jLabel3.setOpaque(true);
        countersPanel.add(jLabel3);
        player2Wins.setText("0");
        countersPanel.add(player2Wins);
        JLabel jLabel4 = new JLabel();
        jLabel4.setText("Draws: ");
        countersPanel.add(jLabel4);
        draws.setText("0");
        countersPanel.add(draws);
        //--------background and stone selection--------------------------------------------
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new java.awt.GridLayout(4, 1, 0, 5));
        JLabel jLabel1 = new JLabel();
        jLabel1.setText("Select Stone");
        optionsPanel.add(jLabel1);
        String[] stoneTypes = {"X", "O", "Monkey", "Cat", "Penguin", "Crown", "Smiley"};
        gameStoneP1.setModel(new DefaultComboBoxModel<>(stoneTypes));
        gameStoneP1.addActionListener(gameStoneListener);
        gameStoneP2.setModel(new DefaultComboBoxModel<>(stoneTypes));
        gameStoneP2.setSelectedIndex(1);
        gameStoneP2.addActionListener(gameStoneListener);
        if (Frame1.gameChoose != 2) {
            JPanel gameStones = new JPanel();
            gameStones.setLayout(new GridLayout());
            gameStones.add(gameStoneP1);
            gameStones.add(gameStoneP2);
            optionsPanel.add(gameStones);
        } else if (firstFrame.network.getisServer()) {
            optionsPanel.add(gameStoneP1);
        } else optionsPanel.add(gameStoneP2);
        JLabel jLabel8 = new JLabel();
        jLabel8.setText("Select Background");
        optionsPanel.add(jLabel8);
        background.setModel(new DefaultComboBoxModel<>(new String[]{"default", "blue", "pink", "yellow"}));
        Color[] colors = {null, Color.blue, Color.pink, Color.yellow};
        optionsPanel.add(background);
        background.addActionListener(e -> {
            for (Field button : buttons) {
                button.setBackground(colors[background.getSelectedIndex()]);
            }
        });
        //-------Tic Tac Toe field-------------------
        JPanel field = new JPanel();
        field.setPreferredSize(new Dimension(400, 400));
        field.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new Field(this);
            field.add(buttons[i]);
        }
        //------Chat--------------------------------------
        JScrollPane chatInputScrollPane = new JScrollPane();
        chatInputScrollPane.setViewportView(chatInputPane);
        JScrollPane chatTextScrollPane = new JScrollPane();
        chatTextScrollPane.setViewportView(chatTextField);
        JButton recPlay = new JButton("show recommended play");
        recPlay.addActionListener(evt -> {
            int num = (new Algorithmen(Field.t3).minimax()).getT3();
            Thread thread = new Thread(() -> {
                int counter = 0;
                while (counter++ < 4) {
                    if (buttons[num].getBackground() == Color.GREEN)
                        buttons[num].setBackground(colors[background.getSelectedIndex()]);
                    else buttons[num].setBackground(Color.GREEN);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    buttons[num].repaint();
                }
            });
            thread.setPriority(2);
            thread.start();
        });
        //-----Buttons----------------------------------------------
        giveUpButton.addActionListener(e -> {
            gameOver(true);
            if (Frame1.gameChoose == 2) sendChatText("!iGiveUp");
        });
        JButton resetButton = new JButton("reset");
        resetButton.addActionListener(e -> {
            if (Frame1.gameChoose == 2) {
                sendChatText("!reset?");
            } else resetStats();
            if (Frame1.gameChoose == 0) {
                Field.val = 1;
                jLabel2.setBackground(Color.green);
                jLabel3.setBackground(null);
            }

        });
        optionsButton.addActionListener(e -> {
            if (Frame1.gameChoose == 2 && serverActive) {
                sendChatText("!userDisconnected");
                firstFrame.network.closeServer();
                serverActive = false;
            }
            Field.t3 = new TicTacToe();
            setVisible(false);
            firstFrame = new Frame1();
        });

        JButton sendChatButton = new JButton("send");
        sendChatButton.addActionListener(e -> sendChatText(""));
        chatInputPane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //nichts
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendChatText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    chatInputPane.setText("");
                }
            }
        });
        //--------Layout-----------------------------------------------------------------------------------------------------------------------------------------------------------
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(chatInputScrollPane, GroupLayout.PREFERRED_SIZE, 534, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(sendChatButton))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(countersPanel, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(giveUpButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addGap(18, 18, 18)
                                                                                        .addComponent(resetButton))
                                                                                .addComponent(optionsButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(recPlay, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(chatTextScrollPane, GroupLayout.PREFERRED_SIZE, 561, GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(420, 420, 420)
                                .addComponent(optionsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(countersPanel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(recPlay)
                                                .addGap(18, 18, 18)
                                                .addComponent(optionsButton)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(giveUpButton)
                                                        .addComponent(resetButton))
                                                .addGap(24, 24, 24)
                                                .addComponent(optionsPanel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addComponent(chatTextScrollPane, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(sendChatButton, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(chatInputScrollPane))
                                .addContainerGap())
        );
        pack();
        setVisible(true);
        if (Frame1.gameChoose != 2) {
            chatInputScrollPane.setVisible(false);
            chatTextScrollPane.setVisible(false);
            sendChatButton.setVisible(false);
        } else updateButtons(firstFrame.network.isYourTurn());
    }

    //------------------------------------------------------------------------------------------------------------------------------------------
    private ActionListener gameStoneListener = e -> {

        if (gameStoneP2.getSelectedIndex() != p2Stone || gameStoneP1.getSelectedIndex() != p1Stone) {
            if (gameStoneP1.getSelectedIndex() == gameStoneP2.getSelectedIndex()) {
                gameStoneP1.setSelectedIndex(0);
                gameStoneP2.setSelectedIndex(1);
                JOptionPane.showMessageDialog(firstFrame, "You should select two different stones", "Invalid stone selection", JOptionPane.ERROR_MESSAGE);
            } else {
                p1Stone = gameStoneP1.getSelectedIndex();
                p2Stone = gameStoneP2.getSelectedIndex();
                if (Frame1.gameChoose == 2) {
                    try {
                        firstFrame.network.dos.writeUTF(("/nehmeStein" + (firstFrame.network.getisServer() ? gameStoneP1.getSelectedIndex() : gameStoneP2.getSelectedIndex())));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    };

    void updateButtons(boolean update) {
        for (Field button : buttons) {
            if (update) {
                button.setEnabled(true);
                giveUpButton.setEnabled(true);
            } else {
                button.setEnabled(false);
                giveUpButton.setEnabled(false);
            }
        }
    }

    void sendChatText(String text) {
        text = text.isEmpty() ? chatInputPane.getText() : text;
        if (text.isEmpty() || text.equals("\n")) return;
        text = Frame1.name + ": " + text;
        if (!text.substring(Frame1.name.length() + 2, Frame1.name.length() + 3).equals("!")) addChatText(text);
        try {
            firstFrame.network.dos.writeUTF(text);
            firstFrame.network.dos.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        chatInputPane.setText("");
    }

    void addChatText(String s) {
        sB.append(s).append("\n");
        chatTextField.setText(String.valueOf(sB));
    }

    void resetStats() {
        p1WinsCounter = p2WinsCounter = drawsCounter = 0;
        updateCounters();
        Field.t3 = new TicTacToe();
        gameStoneP1.setSelectedIndex(0);
        gameStoneP2.setSelectedIndex(1);
        background.setSelectedIndex(0);
        newGame();
    }

    private void newGame() {
        gameStoneP1.setEnabled(true);
        gameStoneP2.setEnabled(true);
        for (int i = 0; i < 9; i++) buttons[i].setIcon(null);
        if (Frame1.gameChoose == 0) {
            if (Field.val == 1) {
                jLabel2.setBackground(Color.green);
                jLabel3.setBackground(null);
            } else {
                jLabel3.setBackground(Color.green);
                jLabel2.setBackground(null);
            }
        }
    }

    void check(int fieldnumber) {
        //-----------Multiplayer online--------------------------------------------------
        if (Frame1.gameChoose == 2 && firstFrame.network.isYourTurn()) {
            try {
                firstFrame.network.dos.writeInt(fieldnumber);
                firstFrame.network.dos.flush();
                firstFrame.network.swapTurn();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (Field.t3.isWin() || Field.t3.isDraw()) gameOver(false);
            //--------------gegen Computer-----------------------------------------------------
        else if (Frame1.gameChoose == 1) {
            int zug = (new Algorithmen(Field.t3).minimax()).getT3();
            TicTacToe tmp;
            tmp = (TicTacToe) Field.t3.makeMove(new Move(zug));
            buttons[zug].setIcon(Field.val == 1 ? Field.icons[gameStoneP1.getSelectedIndex()] : Field.icons[gameStoneP2.getSelectedIndex()]);
            Field.val = -Field.val;
            Field.t3 = tmp;
            if (Field.t3.isWin() || Field.t3.isDraw()) gameOver(false);
        }
        //--------------------------------------------------------------------------------------------
    }

    void gameOver(boolean giveUp) {
        int nextGame;
        if (Field.t3.isWin() || giveUp) {
            if (Field.val == -1) {
                p1WinsCounter++;
            } else p2WinsCounter++;
            if (firstFrame.network.getisServer())
                nextGame = JOptionPane.showConfirmDialog(null, (Field.val == -1 ? Frame1.name : Frame1.enemyName) + " has won\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
            else
                nextGame = JOptionPane.showConfirmDialog(null, (Field.val == 1 ? Frame1.name : Frame1.enemyName) + " has won\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
        } else {
            drawsCounter++;
            nextGame = JOptionPane.showConfirmDialog(null, "Game is draw\nStart a new Game?", "Game End", JOptionPane.OK_CANCEL_OPTION);
        }
        if (nextGame == 0) {
            if (Frame1.gameChoose == 1) Field.val = 1;
            //else if (Frame1.gameChoose == 0) Field.val = -Field.val;
            Field.counter = 0;
            newGame();
            Field.t3 = new TicTacToe();
        } else if(Frame1.gameChoose == 2) {
            sendChatText("!userDisconnected");
            System.exit(0);
        }
        updateCounters();
    }

    private void updateCounters() {
        if (!firstFrame.network.getisServer()) {
            player1Wins.setText(String.valueOf(p2WinsCounter));
            player2Wins.setText(String.valueOf(p1WinsCounter));
        } else {
            player1Wins.setText(String.valueOf(p1WinsCounter));
            player2Wins.setText(String.valueOf(p2WinsCounter));
        }
        draws.setText(String.valueOf(drawsCounter));
    }
}
