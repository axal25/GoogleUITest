package main.google.web.driver.selenium;

import io.github.bonigarcia.seljup.Browser;
import io.github.bonigarcia.seljup.EnabledIfBrowserAvailable;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import main.Constants;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ParameterResolutionExceptionHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for https://www.google.com/ using WebDriver provided by SeleniumJupiter")
public class GoogleSeleniumJupiterWebDriverTest {

    @RegisterExtension
    static SeleniumJupiter seleniumJupiter = new SeleniumJupiter();

    @BeforeAll
    static void beforeAll() {
        seleniumJupiter.getConfig().setBrowserTemplateJsonFile(Constants.Urls.BROWSERS_JSON);
    }

    @Test
    @Order(1)
    @EnabledIfBrowserAvailable(Browser.CHROME)
    void open_assertTitle_quit_smokeTest_localChrome(ChromeDriver webDriver) {
        webDriver.get(Constants.Urls.GOOGLE_HOME);
        assertEquals("Google", webDriver.getTitle());
        webDriver.quit();
    }

    @Test
    @Order(2)
    @EnabledIfBrowserAvailable(Browser.FIREFOX)
    void open_assertTitle_quit_smokeTest_localFirefox(FirefoxDriver webDriver) {
        webDriver.get(Constants.Urls.GOOGLE_HOME);
        assertEquals("Google", webDriver.getTitle());
        webDriver.quit();
    }

    @Test
    @Order(3)
    @EnabledIfBrowserAvailable(Browser.SAFARI)
    void open_assertTitle_quit_smokeTest_localSafari(SafariDriver webDriver) {
        webDriver.get(Constants.Urls.GOOGLE_HOME);
        assertEquals("Google", webDriver.getTitle());
        webDriver.quit();
    }

    @Test
    @Order(4)
    @EnabledIfBrowserAvailable(Browser.EDGE)
    void open_assertTitle_quit_smokeTest_localEdge(EdgeDriver webDriver) {
        webDriver.get(Constants.Urls.GOOGLE_HOME);
        assertEquals("Google", webDriver.getTitle());
        webDriver.quit();
    }

    @TestTemplate
    @Order(5)
    void open_assertTitle_quit_smokeTest_templateTest(WebDriver webDriver) {
        webDriver.get(Constants.Urls.GOOGLE_HOME);
        assertEquals("Google", webDriver.getTitle());
        webDriver.quit();
    }
}
