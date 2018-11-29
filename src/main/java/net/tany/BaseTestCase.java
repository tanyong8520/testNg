package net.tany;


import com.alibaba.fastjson.JSON;
import net.tany.test.LoginApi;
import net.tany.utils.*;
import org.junit.After;
import org.junit.Before;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Method;
import java.util.Map;

public class BaseTestCase {
    private static final String DEFAULT_SERVER_URL="server.url";
    private static final String USER_NAME = "user.name";
    private static final String USER_PASSWORD = "user.password";
    private final Map<String, TestData> testDataMap;
    private final TestProperties testProperties;
    private String sqlPath;
    private RestClient restClient;


    public BaseTestCase() {
        this.testProperties =  new TestProperties(getTestPropertiesFile());
        String value = testProperties.getProperty("server.url");
        System.out.println("get valuse from properties file"+value);
        this.sqlPath = TestDataUtils.getSQLFile(this.getClass());
        String path = TestDataUtils.getExcelDataFile(this.getClass());
        testDataMap = ExcelUtils.load(path);
    }

    protected String getTestPropertiesFile(){
        return "test.properties";
    }

    /**
     * 登录系统
     */
    public void loginService(){
        AjaxResult authResult = LoginApi.login(restClient, testProperties.getProperty(USER_NAME), testProperties.getProperty(USER_PASSWORD));
        Assert.assertEquals(authResult.getCode(), "200", "login result");
    }

    /**
     * 执行对应路径下的sql脚步，具体的执行位置自己添加
     */
    public void runScript(){
        DbUtils.runScript(this.testProperties, this.sqlPath);
    }

    public RestClient getRestClient(){
        return this.restClient;
    }

    @BeforeClass
    public void beforeClass(){
        restClient = createRestClient();
        System.out.println("before class");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("after class");
    }

    /**
     *创建rest客户端
     * @return
     */
    protected RestClient createRestClient(){
        return new RestClient(this.getProperty(DEFAULT_SERVER_URL));
    }

    /**
     * 从配置文件里读取对应的key值
     * @param name
     * @return
     */
    protected String getProperty(String name){
        return this.testProperties.getProperty(name);
    }

    protected Object[][] getDataProvider(Method method) {
        StackTraceElement stElement = Thread.currentThread().getStackTrace()[2];
        //		List<String> items = this.testcaseDataMap.get(stElement.getMethodName());
        System.out.println("mothod name :"+stElement.getMethodName());
        TestData testData = testDataMap.get(stElement.getMethodName());
        System.out.println("testData"+ JSON.toJSONString(testData));
        Object[][] result = DataProviderUtils.convert(method, testData);
        return result;
    }
}
