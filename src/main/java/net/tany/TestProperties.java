package net.tany;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {
    private final Properties testProperties;

    public TestProperties(String proppertiesFile){
        testProperties = new Properties();
        File testPropertiesFile = new File(proppertiesFile);
        InputStream inStream;
        try {
            inStream = new FileInputStream(testPropertiesFile);
            testProperties.load(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String name){
        return this.testProperties.getProperty(name);
    }
}
