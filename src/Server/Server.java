package Server;

import java.io.IOException;
import java.net.*;
import Resources.*;



public class Server {
    private static ServerSocket serverSocket;
    private  final Resources resources = new Resources();

    protected void startServer(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The server is up and running");
        } catch (Exception e) {
            System.out.println("Failed to start the server");
            serverSocket.close();
        }
    }

    protected void acceptServer() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable perform = new ServerThread(socket);
                var thread = new Thread(perform);
                thread.start();
            } catch (Exception e) {
                break;
            }
        }
    }



    public static void main(String[] args) throws IOException {

        Server server = new Server();
        server.startServer(Integer.parseInt(server.resources.getProperties("PORT")));

        while (true) {
            server.acceptServer();
        }

    }

}




