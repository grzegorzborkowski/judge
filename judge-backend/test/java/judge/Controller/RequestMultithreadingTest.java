package judge.Controller;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class RequestMultithreadingTest {

    Class[] testClasses = {SimulatedRequestTest.class};

    @Test
    public void testMultithreading() {
        Result result = JUnitCore.runClasses(new ParallelComputer(false, true), testClasses);
        System.out.println(result.getFailures());
        Assert.assertTrue((result.wasSuccessful()));
    }

}