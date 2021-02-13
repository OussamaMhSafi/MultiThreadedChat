package safi.oussama;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class Server
{

    static ArrayList<ServerWorker> ar = new ArrayList<>();
    static int PORT=8818;
    static int i = 0;


    public static void main(String[] args) throws IOException
    {
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(PORT);
        Socket s;
        System.out.println("Server is up and running....");
        // running infinite loop for getting
        // client request
        while (true)
        {

            // Accept the incoming request
            s = ss.accept();

            System.out.println("New client request received : " + s);

            // obtain input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating a new worker for client "+ s.getLocalAddress());

            // Create a new handler object for handling this request.
            ServerWorker worker = new ServerWorker(s,"Client " + i, dis, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(worker);

            System.out.println("Adding this client to active client list");

            ar.add(worker);
            t.start();
            i++;

        }
    }
}

