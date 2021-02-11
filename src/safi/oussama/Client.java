package safi.oussama;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

    private static DataOutputStream out;
    private static BufferedReader in;
    private static Scanner sc;
    private static Socket socket;

    static {
        try {
            socket = new Socket("localhost", 1025);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sc = new Scanner(System.in);

            String line = null;

            while (true) {

                line = sc.nextLine();
                out.writeBytes(line);
                Thread.sleep(100);
            }
            } catch(IOException | InterruptedException e){
                e.printStackTrace();
            }
    }

    Client(){ }

    public static void main(String[] args) throws IOException {

        Client client=new Client();
        ClientMessage clientMessage=new ClientMessage();
        client.start();
        clientMessage.start();

    }


    public static class ClientMessage extends Thread {


        @Override
        public void run() {

                try {
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String line;

                    while (true) {

                        while ((line = in.readLine()) != null) {

                            Thread.sleep(100);
                            System.out.println(line);
                        }
                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}

