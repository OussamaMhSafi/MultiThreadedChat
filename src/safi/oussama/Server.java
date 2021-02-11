package safi.oussama;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Server extends Thread {

    private final int serverPort;
    private ArrayList<ServerWorker> workers=new ArrayList<>();

    public Server(int serverPort) {
        this.serverPort=serverPort;
    }

    public ArrayList<ServerWorker> getWorkers() {
        return workers;
    }

    @Override
    public void run() {

        ServerSocket server = null;
        try {
            server = new ServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server up and running");
        while (true) {

            try {


                System.out.println("Accepting connection");
                Socket clientSocket = server.accept();
                OutputStream clientOut = clientSocket.getOutputStream();
                clientOut.write("Hello Client\n".getBytes());
                clientOut.write(("Current time is: " + new Date().toString() + "\n").getBytes());
                Thread.sleep(1000);
                clientOut.close();
                System.out.println("Accepted connection from " + clientSocket.getLocalAddress());
                ServerWorker serverWorker = new ServerWorker(this,clientSocket);
                workers.add(serverWorker);
                serverWorker.start();
            }
            catch(IOException | InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}
