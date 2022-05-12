package Server;

import Connection.Connection;

import java.util.HashMap;

public class ModelServer {
    private final HashMap<String, Connection> allUsersMultiChat = new HashMap<>();

    protected HashMap<String, Connection> getAllUsersMultiChat() {
        return allUsersMultiChat;
    }

    protected void addUser(String nameUser, Connection connection) {
        allUsersMultiChat.put(nameUser, connection);
    }

    protected void removeUser(String nameUser) {
        allUsersMultiChat.remove(nameUser);
    }


}