package net.tany.utils;

import net.tany.TestData;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    private static final Logger logger = Logger.getLogger(ExcelUtils.class);

    public static Map<String, TestData> load(String path){
        File file = new File(path);
        if(!file.exists()){
            return null;
        }

        InputStream inputStream = null;
        XSSFWorkbook workbook = null;
        try{
            inputStream = new FileInputStream(path);
            workbook = new XSSFWorkbook(inputStream);
            int sheetCount = workbook.getNumberOfSheets();
            Map<String, TestData> result = new HashMap<String, TestData>();
            for(int index =0; index< sheetCount; index++){
                XSSFSheet sheet = workbook.getSheetAt(index);
                result.put(sheet.getSheetName(), loadSheetData(sheet));
            }

            return result;
        }catch(IOException e){
            logger.error("ioexception: "+ path, e);
            throw new RuntimeException(e);
        } finally{
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private static TestData loadSheetData(XSSFSheet sheet){
        List<String> headers = new ArrayList<String>();
        XSSFRow row = sheet.getRow(0);
        for(int i = 0; i< row.getLastCellNum(); i++){
            XSSFCell cell = row.getCell(i);
            if(cell != null && cell.getStringCellValue()!= null){
                headers.add(cell.getStringCellValue());
            } else {
                break;
            }
        }

        List<List<Object>> items = new ArrayList<List<Object>>();
        for (int k = 1; k <= sheet.getLastRowNum(); k++) {
            row = sheet.getRow(k);

            List<Object> item = new ArrayList<Object>();
            for(int i = 0; i< headers.size(); i++){
                XSSFCell cell = row.getCell(i);
                if(cell != null){
                    String cellVal = getCellValue(cell);
                    item.add(cellVal);
                } else {
                    item.add(null);
                }
            }

            items.add(item);
        }

        TestData testData = new TestData();
        testData.setHeaders(headers);
        testData.setItems(items);
        return testData;
    }

    private static String getCellValue(XSSFCell cell) {
        if (cell == null){
            return "";
        }

        if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
            return String.valueOf(cell.getStringCellValue());
        }
        return "";
    }
}
