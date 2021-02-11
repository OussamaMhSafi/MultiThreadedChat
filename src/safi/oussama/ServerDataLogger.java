package safi.oussama;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerDataLogger {

    private final String fileName="C:\\Users\\46768\\IdeaProjects\\MultiThreadedChat\\src\\safi\\oussama\\logData.txt";
    private final LocalDateTime requestTime;
    private final String requestType;
    private final String localAddress;
    private final String remoteAddress;
    private final String contentType;
    private final Integer contentLength;
    private final int statusCode;

    public ServerDataLogger(LocalDateTime requestTime, String request, String localAddress, String remoteAddress, String contentType, Integer contentLength, int statusCode) {
        this.requestTime = requestTime;
        this.requestType = request;
        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.statusCode = statusCode;

        try {
            logData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logData() throws IOException {

        FileWriter fileWriter=new FileWriter(fileName);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");

        fileWriter.write( "Date: "+dateTimeFormatter.format(this.getRequestTime()) + "\r\n");
        fileWriter.write("Request: "+requestType+"\r\n");
        fileWriter.write("Server IP: "+localAddress+"\r\n");
        fileWriter.write("Remote IP: "+remoteAddress+"\r\n");
        fileWriter.write("Status code: "+statusCode+"\r\n");
        fileWriter.write("Content type: "+contentType+"\r\n");
        fileWriter.write("Content Length:"+contentLength+"\r\n");
        fileWriter.write("=========================================="+"\r\n");
        fileWriter.close();
    }


    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getContentType() {
        return contentType;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public int getStatusCode() {
        return statusCode;
    }



}
