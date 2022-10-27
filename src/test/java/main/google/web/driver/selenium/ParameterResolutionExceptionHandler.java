package main.google.web.driver.selenium;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import static org.junit.jupiter.api.Assertions.fail;

public class ParameterResolutionExceptionHandler implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        if (!(throwable instanceof ParameterResolutionException)) {
            throw throwable;
        }
        // TODO: remove
//         System.err.println(throwable.getMessage());
        throwable.printStackTrace();
        fail(throwable.getMessage());
    }
}
