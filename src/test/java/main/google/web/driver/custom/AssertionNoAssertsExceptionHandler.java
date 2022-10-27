package main.google.web.driver.custom;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class AssertionNoAssertsExceptionHandler implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        if (!(throwable instanceof AssertionNoAsserts)) {
            throw throwable;
        }
        AssertionNoAsserts assertionNoAsserts =
                (AssertionNoAsserts) throwable;
        System.err.println(assertionNoAsserts);
    }
}
