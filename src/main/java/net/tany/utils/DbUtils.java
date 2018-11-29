package net.tany.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import net.tany.TestProperties;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    private static final String URL = "jdbc.url";
    private static final String USER_NAME = "jdbc.username";
    private static final String PASSWORD = "jdbc.password";

    public static void runScript(TestProperties testProperties, String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            return ;
        }

        String url = testProperties.getProperty(URL);
        String userName = testProperties.getProperty(USER_NAME);
        String password = testProperties.getProperty(PASSWORD);

        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            // 创建ScriptRunner，用于执行SQL脚本
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            // 执行SQL脚本
            runner.runScript(Resources.getResourceAsReader(filePath));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
