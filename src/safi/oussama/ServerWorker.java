package safi.oussama;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


public class ServerWorker extends Thread implements Runnable{

        Scanner scn = new Scanner(System.in);
        private String name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;
        boolean isloggedin;

        // constructor
        public ServerWorker(Socket s, String name,
                             DataInputStream dis, DataOutputStream dos) {
            this.dis = dis;
            this.dos = dos;
            this.name = name;
            this.s = s;
            this.isloggedin=true;
        }

        @Override
        public void run() {

            for (ServerWorker w : Server.ar) {

                if (w.isloggedin) {
                    try {
                        w.dos.writeUTF(w.name + " status: ONLINE");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            String received;

            while (true)
            {
                try
                {
                    received = dis.readUTF();

                    System.out.println(received);

                    if(received.equals("logout")){

                        this.isloggedin=false;
                        this.s.close();
                        break;
                    }

                    StringTokenizer st = new StringTokenizer(received, "#");
                    String MsgToSend = st.nextToken();
                    //String recipient = st.nextToken();


                    dos.writeUTF(this.name+" : "+MsgToSend);

            } catch (IOException e) {

                    e.printStackTrace();
                }

            }
            try
            {
                // closing resources
                this.dis.close();
                this.dos.close();

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
