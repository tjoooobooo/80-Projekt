import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class Frame1 extends JFrame {
    Network network = new Network();
    public JComboBox gameType;
    static String name;
    static String enemyName;
    public static int gameChoose;

    public static void main(String args[]) {
        new Frame1();
    }

    public Frame1() {
        JTextField inputName = new JTextField();
        JTextField inputEnemyName = new JTextField();
        JTextField inputIP = new JTextField();
        JButton button1, button2;

        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(350, 140);
        getContentPane().setLayout(new GridLayout(4, 2));

        add(new JLabel(" Choose Game Type"));
        gameType = new JComboBox(new String[]{"Local(PVP)", "Local(PVC)", "Online(PVP)"});
        add(gameType);
        add(new JLabel(" Write your Name"));
        JLabel names = new JLabel();
        names.setLayout(new GridLayout(1, 2));
        names.add(inputName);
        names.add(inputEnemyName);
        add(names);
        add(new JLabel("Choose IP-Address"));
        add(inputIP);
        button1 = new JButton("Confirm");
        button2 = new JButton("Cancel");
        Frame1 frame1 = this;

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameChoose = gameType.getSelectedIndex();
                name = inputName.getText().isEmpty() ? "Player1" : inputName.getText();
                enemyName = inputEnemyName.getText().isEmpty() ? "Computer" : inputEnemyName.getText();
                setVisible(false);
                Frame2 secondFrame;
                // TODO Fenster wird erst richtig angezeigt wenn verbindung da ist
                //-------Netzwerk verbinden---------------------------------------------------------------
                if (gameType.getSelectedIndex() == 2) {
                    network.setIP(inputIP.getText().isEmpty() ? "localhost" : inputIP.getText());
                    while (!network.isAccepted() && network.getisServer()) {
                        network.listenForServerRequest();
                    }
                    try {
                        network.dos.writeUTF(name);
                        enemyName = network.dis.readUTF();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    secondFrame = new Frame2(frame1);
                    Thread thread = new Thread(() -> {
                        try {
                            while (secondFrame.serverActive) {
                                if (network.dis.available() == 4) {
                                    Integer tmp = network.dis.readInt();
                                    secondFrame.updateButtons(true);
                                    secondFrame.buttons[tmp].doClick();
                                    network.swapTurn();
                                } else if(network.dis.available() > 4) {
                                    String s = network.dis.readUTF();
                                    if ((s.replace(name, "")).replace(enemyName, "").substring(2,3).equals("!")) {
                                        s = s.replace(name, "").replace(enemyName, "");
                                        s = s.substring(2,s.length());
                                        switch (s) {
                                            case "!kick":
                                                System.out.println("Du wurdest aus der Sitzung geworfen!");
                                                break;
                                            case "!leave":
                                                int disconnected;
                                                if (network.getisServer()) {
                                                    disconnected = JOptionPane.showConfirmDialog(null, "Client has left game\nback to game options?", "Client disconnected", JOptionPane.OK_CANCEL_OPTION);
                                                } else {
                                                    disconnected = JOptionPane.showConfirmDialog(null, "Server has left game\nback to game options?", "Server disconnected", JOptionPane.OK_CANCEL_OPTION);
                                                }
                                                if (disconnected == 0) secondFrame.optionsButton.doClick();
                                                else System.exit(0);
                                                break;
                                            case "!iGiveUp":
                                                //TODO giveUp online
                                                JOptionPane.showMessageDialog(null,enemyName + " gave up", "YOU WON",JOptionPane.OK_OPTION);
                                                secondFrame.gameOver(true);
                                                break;
                                            default:
                                                System.out.println("Unbekannter Befehl!");
                                                break;
                                        }
                                    } else {
                                        secondFrame.addChatText(s);
                                        secondFrame.chatTextField.setCaretPosition(secondFrame.chatTextField.getText().length());
                                    }
                                }
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        }
                    });
                    thread.setPriority(1);
                    thread.start();
                } else secondFrame = new Frame2(frame1);
                //--------------------------------------------------------------------------------------
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
    }
}
