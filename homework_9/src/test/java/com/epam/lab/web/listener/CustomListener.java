package test.java.com.epam.lab.web.listener;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListener implements ITestListener {

    private static final Logger LOG = Logger.getLogger(CustomListener.class);

    public void onTestStart(ITestResult iTestResult){
        LOG.info("STARTING TEST: [" + iTestResult.getInstanceName() + "], method: ["
                + iTestResult.getMethod().getMethodName() + "] at " + iTestResult.getStartMillis());
    }

    public void onTestSuccess(ITestResult iTestResult){
        LOG.info("SUCCESS: [" + iTestResult.getInstanceName() + "], method: ["
                + iTestResult.getMethod().getMethodName() + "] at " + iTestResult.getEndMillis());
    }

    public void onTestFailure(ITestResult iTestResult){
        LOG.info("FAILURE: [" + iTestResult.getInstanceName() + "], method: ["
                + iTestResult.getMethod().getMethodName() + "] at " + iTestResult.getEndMillis());
    }

    public void onTestSkipped(ITestResult iTestResult){
        LOG.info("SKIPPED: [" + iTestResult.getInstanceName() + "], method: ["
                + iTestResult.getMethod().getMethodName() + "] at " + iTestResult.getEndMillis());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult){
        // TODO Auto-generated method stub
    }

    public void onStart(ITestContext iTestContext){
        LOG.info("---START EXECUTION OF: " + iTestContext.getSuite().getName() + " AT "
                + iTestContext.getStartDate() + "---");
    }

    public void onFinish(ITestContext iTestContext){
        LOG.info("---FINISH EXETUION OF: " + iTestContext.getSuite().getName() + " AT "
                + iTestContext.getEndDate() + "---");
        LOG.info("--RESULTS--");
        LOG.info("PASSED TEST CASES:");
        iTestContext.getPassedTests().getAllResults().forEach(t -> LOG.info("\t" + t.getName()));
        LOG.info("FAILED TEST CASES:");
        iTestContext.getFailedTests().getAllResults().forEach(t -> LOG.info("\t" + t.getName()));
        LOG.info("SKIPPED TEST CASES:");
        iTestContext.getSkippedTests().getAllResults().forEach(t -> LOG.info("\t" + t.getName()));
        LOG.info("PASS RATE: " + (iTestContext.getPassedTests().size()
                / (iTestContext.getPassedTests().size() + iTestContext.getFailedTests().size())) * 100 + "%" );
    }
}
