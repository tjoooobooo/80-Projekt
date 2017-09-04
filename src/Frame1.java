import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Frame1 extends JFrame {
    Network network = new Network();
    public JComboBox gameType;
    static String name;
    static String enemyName;
    public static int gameChoose;
    JLabel names = new JLabel();
    JTextField inputName = new JTextField();
    JTextField inputEnemyName = new JTextField();
    JTextField inputIP = new JTextField();
    JButton button1, button2;
    Frame1 frame1 = this;
    Frame2 secondFrame;

    public static void main(String args[]) {
        new Frame1();
    }

    public Frame1() {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(350, 140);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel(" Choose Game Type"));
        gameType = new JComboBox(new String[]{"Local(PVP)", "Local(PVC)", "Online(PVP)"});
        panel.add(gameType);
        panel.add(new JLabel(" Write your Name"));

        names.setLayout(new GridLayout(1, 0));
        names.add(inputName);
        names.add(inputEnemyName);
        panel.add(names);

        panel.add(new JLabel("Choose IP-Address"));
        panel.add(inputIP);
        button1 = new JButton("Confirm");
        button2 = new JButton("Cancel");

        gameType.addActionListener(e -> {
            if (gameType.getSelectedIndex() != 0) {
                names.remove(inputEnemyName);

            } else {
                names.add(inputEnemyName);
            }
            names.repaint();
        });
        panel.add(button1);
        panel.add(button2);

        button2.addActionListener(e -> System.exit(0));

        button1.addActionListener(e -> {
            gameChoose = gameType.getSelectedIndex();
            name = inputName.getText().isEmpty() ? "Player1" : inputName.getText();
            if(gameChoose == 0) enemyName = inputEnemyName.getText().isEmpty() ? "Player2" : inputEnemyName.getText();
            else enemyName = inputEnemyName.getText().isEmpty() ? "Computer" : inputEnemyName.getText();
            //-------Netzwerk verbinden---------------------------------------------------------------
            if (gameType.getSelectedIndex() == 2) {
                frame1.remove(panel);
                frame1.setPreferredSize(new Dimension(100,100));
                frame1.setContentPane(new WaitingPanel());
                frame1.validate();

                Thread thread = new Thread(() -> {
                    network.setIP(inputIP.getText().isEmpty() ? "localhost" : inputIP.getText());
                    while (!network.isAccepted()) {
                        network.listenForServerRequest();
                    }
                    setVisible(false);
                    try {
                        network.dos.writeUTF(name);
                        enemyName = network.dis.readUTF();
                        //enemyName = enemyName.equals("Player1") ? "Player" : enemyName;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    secondFrame = new Frame2(frame1);
                    try {
                        while (network.isAccepted()) {
                            if (network.dis.available() == 4) {
                                Integer tmp = network.dis.readInt();
                                secondFrame.updateButtons(true);
                                secondFrame.buttons[tmp].doClick();
                                network.swapTurn();
                            } else if (network.dis.available() > 4) {
                                String s = network.dis.readUTF();
                                if (s.startsWith("/nehmeStein")) {
                                    s = s.replace("/nehmeStein", "");
                                    if (network.getisServer()) secondFrame.gameStoneP2.setSelectedIndex(Integer.parseInt(s));
                                    else secondFrame.gameStoneP1.setSelectedIndex(Integer.parseInt(s));
                                } else if ((s.replace(name, "")).replace(enemyName, "").substring(2, 3).equals("!")) {
                                    s = s.replace(name, "").replace(enemyName, "");
                                    s = s.substring(2, s.length());
                                    int tmp;
                                    switch (s) {
                                        case "!kick":
                                            break;
                                        case "!userDisconnected":
                                            if (network.getisServer()) {
                                                tmp = JOptionPane.showConfirmDialog(null, "Client has left game\nback to game options?", "Client disconnected", JOptionPane.OK_CANCEL_OPTION);
                                            } else {
                                                tmp = JOptionPane.showConfirmDialog(null, "Server has left game\nback to game options?", "Server disconnected", JOptionPane.OK_CANCEL_OPTION);
                                            }
                                            network.closeServer();
                                            secondFrame.serverActive = false;
                                            if (tmp == 0) secondFrame.optionsButton.doClick();
                                            else System.exit(0);
                                            break;
                                        case "!iGiveUp":
                                            JOptionPane.showMessageDialog(null, enemyName + " gave up", "YOU WON", JOptionPane.OK_OPTION);
                                            secondFrame.gameOver(true);
                                            break;
                                        case "!reset?":
                                            tmp = JOptionPane.showConfirmDialog(null, "Opponnent asks if you want to reset the stats", "Reset the stats?", JOptionPane.YES_NO_OPTION);
                                            if (tmp == 0) {
                                                secondFrame.resetStats();
                                                secondFrame.sendChatText("!reset");
                                            } else secondFrame.sendChatText("!resetDenied");
                                            break;
                                        case "!resetDenied":
                                            JOptionPane.showMessageDialog(null, "Reset was denied", "No reset", JOptionPane.OK_OPTION);
                                            break;
                                        case "!reset":
                                            secondFrame.resetStats();
                                            break;
                                        default:
                                            System.out.println("Unbekannter Befehl!");
                                            break;
                                    }
                                } else
                                    secondFrame.addChatText(s);
                                secondFrame.chatTextField.setCaretPosition(secondFrame.chatTextField.getText().length());
                            }
                        }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                thread.setPriority(1);
                thread.start();
            } else {
                secondFrame = new Frame2(frame1);
                setVisible(false);
            }
            //--------------------------------------------------------------------------------------
        });
        add(panel);
        setVisible(true);
    }
}
