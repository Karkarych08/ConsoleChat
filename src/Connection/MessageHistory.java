package Connection;

import java.io.IOException;
import java.util.LinkedList;

public class MessageHistory {
    private final LinkedList<String> story = new LinkedList<>();

    public void addMessageInHistory(String message) {
        if (story.size() >= 10) {
            story.removeFirst();
            story.add(message);
        } else {
            story.add(message);
        }
    }

    public void printStory(Connection connection) {
        try {
            if (story.size() > 0) {
                connection.sendMessage("\nHistory messages" + "");
                for (String earlyMessage : story) {
                    connection.sendMessage(earlyMessage + "");
                }
                connection.sendMessage("/...." + "\n");
            }
        }catch (RuntimeException | IOException exception) {
            exception.printStackTrace();
        }

    }

}
