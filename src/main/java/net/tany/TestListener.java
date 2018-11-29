package net.tany;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {
    @Override
    public void onTestStart(ITestResult result){
        super.onTestStart(result);
        System.out.println("listener test Start function");
    }
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        System.out.println("listener test failure function");
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        System.out.println("listener test Skipped function");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        System.out.println("listener test Success function");
    }
}
