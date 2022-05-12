package Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Resources {

    public  String getProperties(String nameProperties) {
        Properties properties = new Properties();
        String getPropertyValue = "";

        try {
            properties.load(new FileInputStream("C:/Users/lesha/IdeaProjects/ConsolChat/Properties.properties"));
            getPropertyValue = properties.getProperty(nameProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getPropertyValue;
    }

}
