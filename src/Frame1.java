import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class Frame1 extends JFrame {
    static Network network = new Network();
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
                    Frame2 secondFrame = new Frame2(frame1);
                    Thread thread = new Thread(() -> {
                        try {
                            while (true) {
                                if (network.dis.available() == 4) {
                                    Integer tmp = network.dis.readInt();
                                    secondFrame.buttons[tmp].doClick();
                                    network.swapTurn();
                                } else if(network.dis.available() > 4) {
                                    String s = network.dis.readUTF();
                                    if ((s.replace(name, "")).replace(enemyName, "").substring(2,3).equals("!")) {
                                        s = s.replace(name, "").replace(enemyName, "");
                                        s = s.substring(2,s.length());
                                        System.out.println(s);
                                        switch (s) {
                                            case "!kick":
                                                System.out.println("Du wurdest aus der Sitzung geworfen!");
                                                break;
                                            case "!userDisconnected":
                                                if (network.getisServer()) {
                                                    System.out.println("Der Client hat die Verbindung getrennt!");
                                                } else {
                                                    System.out.println("Der Server hat die Verbindung getrennt!");
                                                }
                                                break;
                                            case "!GiveUP":
                                                //TODO giveUp online
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
                } else new Frame2(frame1);
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
