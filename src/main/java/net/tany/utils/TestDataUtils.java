package net.tany.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestDataUtils {
    private static final String TEST_DATA_FOLDER="test-data";
    private static final String JSON_FILE=".json";
    private static final String EXCEL_FILE=".xlsx";
    private static final String SQL_FILE=".sql";

    private static String getPath(Class cls){
        return TEST_DATA_FOLDER + File.separator + cls.getName().replace(".", File.separator);
    }

    public static InputStream getTestData(Class cls){
        String path = getPath(cls) +JSON_FILE;
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String getExcelDataFile(Class cls){
        String path = getPath(cls) + EXCEL_FILE;
        return path;
    }

    public static String getSQLFile(Class cls){
        String path = getPath(cls) + SQL_FILE;
        return path;
    }
}
