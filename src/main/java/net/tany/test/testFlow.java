package net.tany.test;

import net.tany.BaseTestCase;
import net.tany.utils.RestClient;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class testFlow extends BaseTestCase {


    @BeforeMethod
    public void beforeMethod(){

        System.out.println("before method");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("after method");
    }

    @DataProvider()
    public Object[][] page(Method method){
        return this.getDataProvider(method);
    }

    @Test(dataProvider = "page",priority = 1)
    public void test001(int test_a,int test_b,String test_c){
        System.out.println("test001");
        System.out.println("a:"+test_a+",b:"+test_b+",c:"+test_c);
    }

    @Test(dataProvider = "page",priority = 2)
    public void test002(){
        System.out.println("test002");
    }
}
