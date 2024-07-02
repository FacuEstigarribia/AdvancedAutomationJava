package com.solvd.advancedautomation;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClassExample {
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void test1() {
        Assert.fail("Failing test1 on purpose");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void test2() {
        Assert.fail("Failing test2 on purpose");
    }
}
