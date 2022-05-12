package Server;

import Connection.*;

import java.io.*;
import java.net.Socket;
import java.util.*;


public class ServerThread implements Runnable {
    private final Socket socket;
    private static final ModelServer modelServer = new ModelServer();
    private static final MessageHistory messageHistory = new MessageHistory();

    public ServerThread(Socket incomingSocket) {
        this.socket = incomingSocket;

    }

    private String askUserName(Connection connection) throws IOException {
        while (true) {
            try {
                connection.sendMessage("Press your name: ");
                String userName = connection.receiveMessage();

                if (userName != null && !userName.isEmpty() && !modelServer.getAllUsersMultiChat().containsKey(userName)) {
                    modelServer.addUser(userName, connection);
                    connection.sendMessage("Hello " + userName + "!Enter EXIT to exit. Enter VIEW ONLINE to view online users");
                    messageHistory.printStory(connection);
                    return userName;

                } else {
                    connection.sendMessage("This name is already used. Please type any user name...");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }

    private void sendMessageAllUsers(String message, Connection connection) {
        for (Map.Entry<String, Connection> user : modelServer.getAllUsersMultiChat().entrySet()) {
            try {
                if (!user.getValue().equals(connection)) {
                    user.getValue().sendMessage(message);
                }
            } catch (Exception e) {
                System.out.println("Failed to send message to all users!\n");
            }
        }
    }


    private void messageBetweenUsers(String userName, Connection connection) {
        while (true) {
            try {
                String message = connection.receiveMessage();
                if (message.equalsIgnoreCase("exit")) {
                    userDisconnect(userName, connection);
                    break;
                }
                if (message.equalsIgnoreCase("view online")) {
                    viewOnlineUsers(connection);
                } else {
                    sendMessageAllUsers("" + userName + ": " + message, connection);
                    messageHistory.addMessageInHistory("" + userName + ": " + message);
                }
            } catch (Exception e) {
                System.out.println("Error, user cant send message :( ");
            }
        }
    }

    private void userDisconnect(String userName, Connection connection) throws IOException {
        sendMessageAllUsers("User <" + userName + "> left the chat. Goodbye!", connection);
        modelServer.removeUser(userName);
        connection.close();
        System.out.printf("A user with remote access %s disconnected.", socket.getRemoteSocketAddress());
    }

    private void viewOnlineUsers(Connection connection) throws IOException {
        connection.sendMessage("\nOnline list:\n");
        for (Map.Entry<String, Connection> user : modelServer.getAllUsersMultiChat().entrySet())
            connection.sendMessage(user.getKey());
        connection.sendMessage("__________\n");
    }

    public void run() {
        try {
            Connection connection = new Connection(socket);
            String userName = askUserName(connection);
            messageBetweenUsers(userName, connection);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}


