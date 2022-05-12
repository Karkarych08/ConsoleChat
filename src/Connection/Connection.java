package Connection;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Connection implements AutoCloseable {
    private final Socket socket;
    private final PrintWriter write;
    private final Scanner read;


    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.read = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
        this.write = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }


    public void sendMessage(String message) throws IOException {
        synchronized (this.write) {
            write.println(message);
        }
    }

    public String receiveMessage() {
        synchronized (this.read) {
            return read.nextLine();
        }
    }

    @Override
    public void close() throws IOException {
        read.close();
        write.close();
        socket.close();
    }
}