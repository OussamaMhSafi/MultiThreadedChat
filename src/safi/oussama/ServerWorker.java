package safi.oussama;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Date;
import java.util.List;


public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private OutputStream outputStream;
    private User user;
    private InputStream inputStream;

    public ServerWorker(Server server, Socket clientSocket) {

        this.server=server;
        this.clientSocket=clientSocket;
    }

    @Override public void run(){

        try {
            handleClientSocket(clientSocket);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }


    private void handleClientSocket(Socket clientSocket) throws InterruptedException, IOException {

        this.inputStream=clientSocket.getInputStream();
        this.outputStream=clientSocket.getOutputStream();

        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while((line=bufferedReader.readLine())!=null){

            String[] token = StringUtils.split(line);

            if("quit".equalsIgnoreCase(line.toLowerCase())){

                break;
            }

            else if(line.equalsIgnoreCase("login")){

                handleUserLogin(outputStream, bufferedReader);
            }
            else {
                String message = "You typed: " + line + "\n";
                outputStream.write(message.getBytes());


            }
        }

        /*
        clientSocket.getOutputStream();
            OutputStream clientOut = clientSocket.getOutputStream();
            clientOut.write("Hello Client\n".getBytes());
            for(int i=0;i<10;i++) {
                clientOut.write(("Current time is: " + new Date().toString() + "\n").getBytes());
                Thread.sleep(1000);
            }
            clientOut.close();
         */

    }

    private void handleUserLogin(OutputStream outputStream, BufferedReader bufferedReader) throws IOException {
        UserData userData=new UserData();
        String request="Please enter your username: "+"\n";
        outputStream.write(request.getBytes());

        String username=bufferedReader.readLine();

        User user = new User(username);
        userData.setUser(user);
        String confirmMsg="User "+username+" created"+"\n";
        outputStream.write(confirmMsg.getBytes());
        List<ServerWorker> workerList=server.getWorkers();

        for(ServerWorker worker:workerList){
            String onlineMsgOut="Online "+worker.getUser().getUsername();
            send(onlineMsgOut);
        }


        String onlineMsg="Online "+username+"\n";
        for(ServerWorker worker:workerList){

            worker.send(onlineMsg);
        }
    }

    private void send(String onlineMsg) throws IOException {

        outputStream.write(onlineMsg.getBytes());
    }

    public User getUser() {
        return user;
    }
}
