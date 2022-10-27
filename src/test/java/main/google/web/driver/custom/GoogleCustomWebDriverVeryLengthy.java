package main.google.web.driver.custom;

import io.github.bonigarcia.seljup.Browser;
import io.github.bonigarcia.seljup.EnabledIfBrowserAvailable;
import main.Constants;
import main.EnvVarSetter;
import main.google.pages.home.HomePage;
import main.google.pages.search.mode.text.SearchAllModeConstants;
import main.google.pages.search.mode.text.SearchAllModeWithMoreModesMenuOpenedPage;
import main.google.pages.terms.and.conditions.TermsAndConditionsPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Is supposed to be excluded from quick/small tests
 */
@EnabledIfBrowserAvailable(Browser.CHROME)
@ExtendWith(AssertionNoAssertsExceptionHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Very lengthy Test for https://www.google.com/ using Customized WebDriver")
public class GoogleCustomWebDriverVeryLengthy {

    private WebDriver webDriver;

    @BeforeAll
    static void beforeAll() {
        EnvVarSetter.chromeDriverSetup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().deleteAllCookies();
        webDriver.get(Constants.Urls.GOOGLE_HOME);
    }

    @AfterEach
    void tearDown() {
        webDriver.quit();
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Tests dependant on correctly closing Terms and Conditions")
    public class AcceptIfNecessaryDependantTest {

        @BeforeEach
        void setUp() {
            new TermsAndConditionsPage(webDriver).acceptIfNecessary();
        }

        @Test
        @Order(999)
        void searchText_completeKeyInput_submit_redundantLengthyOverhead() {
            String inputQueryText = "Input query";

            new HomePage(webDriver).submitKeysToSearchBar(inputQueryText);

            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);
            assertEquals(inputQueryText, searchAllModePage.getSearchBarTextValue());

            assertTrue(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_ALL));
            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_IMAGE));
            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_VIDEO));
            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_MAPS));
            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_NEWS));
            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_SHOPPING));
            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_BOOKS));
            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_FINANCE));
            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_FLIGHTS));

            assertFalse(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_ALL));
            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_IMAGE));
            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_VIDEO));
            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_MAPS));
            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_NEWS));
            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_SHOPPING));
            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_BOOKS));
            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_FINANCE));
            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_FLIGHTS));
        }
    }
}
