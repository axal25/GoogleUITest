package main.helloWorld;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import main.Constants;
import main.google.web.driver.selenium.ParameterResolutionExceptionHandler;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ParameterResolutionExceptionHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for included helloWorld.html page with span element added after delay.")
public class HelloWorldTest {

    @RegisterExtension
    static SeleniumJupiter seleniumJupiter = new SeleniumJupiter();

    @BeforeAll
    static void beforeAll() {
        seleniumJupiter.getConfig().setBrowserTemplateJsonFile(Constants.Urls.BROWSERS_JSON);
    }

    @TestTemplate
    @Order(1)
    void open_assertTitle_quit_smokeTest(WebDriver webDriver) {
        webDriver.get(Constants.Urls.HELLO_WORLD_FILE);

        assertEquals("Hello World", webDriver.getTitle());

        webDriver.quit();
    }

    @TestTemplate
    @Order(2)
    void waitOnSpanToBeAdded(WebDriver webDriver) {
        webDriver.get(Constants.Urls.HELLO_WORLD_FILE);

        HelloWorldPage helloWorldPage = new HelloWorldPage(webDriver);
        assertThrows(NoSuchElementException.class, () -> helloWorldPage.getSpan().isDisplayed());
        assertTrue(helloWorldPage
                .waitOnSpan()
                .getSpan()
                .isDisplayed());

        webDriver.quit();
    }
}
