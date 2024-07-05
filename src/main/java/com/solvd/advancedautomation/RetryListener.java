package com.solvd.advancedautomation;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {

    private int maxRetryCount;

    public RetryListener(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retryAnalyzer = annotation.getRetryAnalyzer();
        if (retryAnalyzer == null || retryAnalyzer.getClass() != RetryAnalyzer.class) {
            RetryAnalyzer analyzer = new RetryAnalyzer();
            analyzer.setMaxRetryCount(maxRetryCount);
            annotation.setRetryAnalyzer(analyzer.getClass());
        }
    }
}

