package safi.oussama;

import javax.imageio.IIOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/*Created by Oussama Safi */


public class MainServer extends Thread {

    private static final int port=8818;
    Server server = new Server(port);
    private static ArrayList<BufferedWriter> clients;
    private static ServerSocket serverSocket;
    private String nome;
    private Socket connection;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;

    public static void main(String[] args) {
        try {
            MainServer mainServer=new MainServer(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientSocket(Socket clientSocket) throws InterruptedException, IOException {

        OutputStream clientOut = clientSocket.getOutputStream();
        clientOut.write("Hello Client\n".getBytes());
        clientOut.write(("Current time is: " + new Date().toString() + "\n").getBytes());
        Thread.sleep(1000);
        clientOut.close();

    }

    public MainServer(String[] args) throws IOException {

        server.start();
    }
}
