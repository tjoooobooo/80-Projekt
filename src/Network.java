import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Philipp on 14.08.2017.
 */
@SuppressWarnings("Duplicates")
public class Network {

    private String ip = "localhost";
    private String name = "Guest";
    private int port = 42000;
    public Socket socket;
    public DataOutputStream dos;
    public DataInputStream dis;
     ServerSocket serverSocket;

    private boolean accepted = false;
    private boolean yourTurn;
    private boolean isServer = true;


    public void setIP(String ip){
        this.ip = ip;
        if (!connect()) initializeServer();
    }

    public void evaluateInputStream() {
        String s = "";
        try {
            if (isAccepted() && (dis.available() != 0)) {
                Thread.sleep(100);
                s = dis.readUTF();
                System.out.println("DIS_CHECK: " + s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("MyErrorMSG: Fehler beim pausieren des Thread!");
            e.printStackTrace();
        }
        if (Objects.equals((s.length() != 0 ? s.substring(0, 1).toLowerCase() : s), "!")) {
            if(s.contains("playerName")){
                String player2 = s.substring(s.indexOf("="),s.length()-1);
            }
            switch (s) {
                case "!kick":
                    if (getisServer()) {//kick ist server only, deswegen passiert als client nichts
                        disconnectPlayerFromServer();
                    } else {
                        System.out.println("Du wurdest aus der Sitzung geworfen!");
                    }
                    break;
                case "!userDisconnected":
                    if (getisServer()) {
                        System.out.println("Der Client hat die Verbindung getrennt!");
                    } else {
                        System.out.println("Der Server hat die Verbindung getrennt!");
                    }
                    break;
                case "!makeMove"://wie machst du einen move als string befehl? am besten !makemove[d] als regex
                    break;
                default:
                    System.out.println("Unbekannter Befehl!");
                    break;
            }
        } else {
            //Kein Befehl? dann Sende als Chat
        }
    }

    public void evaluateInputStream(String s) {
        try {
            if (isAccepted() && (dis.available() != 0)) {
                Thread.sleep(100);
                s = dis.readUTF();
                System.out.println("DIS_CHECK: " + s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("MyErrorMSG: Fehler beim pausieren des Thread!");
            e.printStackTrace();
        }
        if (Objects.equals((s.length() != 0 ? s.substring(0, 1).toLowerCase() : s), "!")) {
            switch (s) {
                case "!kick"://Wenn der Client exit drückt, bekommt der Server einen !clientDisconnected
                    if (getisServer()) {//kick ist server only, deswegen passiert als client nichts
                        disconnectPlayerFromServer();
                    } else {
                        System.out.println("Du wurdest aus der Sitzung geworfen!");
                    }
                    break;
                case "!userDisconnected":
                    if (getisServer()) {
                        System.out.println("Der Client hat die Verbindung getrennt!");
                    } else {
                        System.out.println("Der Server hat die Verbindung getrennt!");
                    }
                    break;
                default:
                    System.out.println("Unbekannter Befehl!");
                    break;
            }
        } else {
            //Kein Befehl? dann Sende als Chat
        }
    }

    public void listenForServerRequest() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("Ein Client hat sich verbunden!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean connect() {
        try {
            socket = new Socket(ip, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
        } catch (IOException e) {
            System.out.println("Verbindung zu Adresse: " + ip + ":" + port + " konnte nicht hergestellt werden. | Starte Server!");
            return false;
        }
        System.out.println("Verbindung zum Server erfolgreich hergestellt!");
        yourTurn = false;
        isServer = false;
        return true;
    }

    public void initializeServer() {
        try {
            serverSocket = new ServerSocket(port, 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        yourTurn = true;
    }

    public void disconnectPlayerFromServer() {//Was soll passieren wenn der Gegner disconnected? automatisch nach neuem suchen? neues spiel? startbildschirm?
        try {
            serverSocket.close();
            initializeServer();
            listenForServerRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void resetYourTurn() {
        yourTurn = false;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void swapTurn() {
        yourTurn = !yourTurn;
    }

    public boolean getisServer() {
        return isServer;
    }

    public void closeServer() {
        accepted = false;
        if(isServer) try {
            serverSocket.close();
            if(socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
