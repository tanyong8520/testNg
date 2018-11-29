package net.tany.utils;

import net.tany.TestData;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;


/**
 * 数据格式化
 */
public class DataProviderUtils {

    public static Object[][] convert(Method method, TestData testData){
        Parameter[] paramters = method.getParameters();
        int size = testData.getItems().size();
        List<String> headers = testData.getHeaders();
        Object[][] result = new Object[size][];
        for(int i=0; i < size; i++){
            Object[] item = new Object[paramters.length];
            result[i] = item;

            List<Object> orgData = testData.getItems().get(i);
            for(int j=0; j<paramters.length; j++){
                String paraName = paramters[j].getName();
                int index = headers.indexOf(paraName);
                //如果名称不能匹配，就按照位置匹配
                if(index<0){
                    item[j] = convertVal(orgData.get(j), paramters[j].getType());
                }else {
                    item[j] = convertVal(orgData.get(index), paramters[j].getType());
                }
            }
        }
        return result;
    }

    public static Object[] parse(Method method, String paraStr){
        String[] items = paraStr.split(",");
        Parameter[] paramters = method.getParameters();
        Class<?>[] paraTypes = method.getParameterTypes();
        Object[] objs = new Object[paraTypes.length];
        for(int i =0; i<objs.length; i++){
            objs[i] = convertVal(items[i], paraTypes[i]);
        }

        return objs;
    }

    private static Object convertVal(Object obj, Class<?> cls){
        if(obj == null){
            return null;
        }
        String str = obj.toString().trim();
        if(cls == String.class){
            return str;
        }else if(cls == int.class){
            return Integer.parseInt(str);
        }else if(cls == long.class){
            return Long.parseLong(str);
        }else if(cls == double.class){
            return Double.parseDouble(str);
        }else if(cls == Integer.class){
            return Integer.parseInt(str);
        }else if(cls == Double.class){
            return Double.parseDouble(str);
        }else if(cls == boolean.class){
            return Boolean.parseBoolean(str);
        }else if(cls == Boolean.class){
            return Boolean.parseBoolean(str);
        }
        return str;
    }
}
