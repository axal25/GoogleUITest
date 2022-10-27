package main.google.web.driver.custom;

import main.Constants;
import main.EnvVarSetter;
import main.google.pages.home.HomePage;
import main.google.pages.search.text.SearchAllModeConstants;
import main.google.pages.search.text.SearchAllModeWithMoreModesMenuOpenedPage;
import main.google.pages.terms.and.conditions.TermsAndConditionsPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.AssertionUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AssertionNoAssertsExceptionHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for https://www.google.com/ using Customized WebDriver")
public class GoogleCustomWebDriverTest {

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

    @Test
    @Order(1)
    void open_assertTitle_quit_smokeTest() {
        assertEquals("Google", webDriver.getTitle());
    }

    @Test
    @Order(2)
    void denyTerms() {
        new TermsAndConditionsPage(webDriver).deny();
    }

    @Test
    @Order(3)
    void acceptTerms() {
        new TermsAndConditionsPage(webDriver).accept();
    }

    @Test
    @Order(4)
    void acceptTermsIfNecessary() {
        new TermsAndConditionsPage(webDriver).acceptIfNecessary();
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

//        @Test
//        @Order(999)
//        void searchText_completeKeyInput_submit_redundantLengthyOverhead() {
//            String inputQueryText = "Input query";
//
//            new HomePage(webDriver).submitKeysToSearchBar(inputQueryText);
//
//            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);
//            assertEquals(inputQueryText, searchAllModePage.getSearchBarTextValue());
//
//            assertTrue(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_ALL));
//            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_IMAGE));
//            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_VIDEO));
//            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_MAPS));
//            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_NEWS));
//            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_SHOPPING));
//            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_BOOKS));
//            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_FINANCE));
//            assertFalse(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_FLIGHTS));
//
//            assertFalse(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_ALL));
//            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_IMAGE));
//            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_VIDEO));
//            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_MAPS));
//            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_NEWS));
//            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_SHOPPING));
//            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_BOOKS));
//            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_FINANCE));
//            assertTrue(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_FLIGHTS));
//        }

        @Test
        @Order(1)
        void searchText_completeKeyInput_submit() {
            String inputQueryText = "Input query";

            new HomePage(webDriver).submitKeysToSearchBar(inputQueryText);

            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);
            assertEquals(inputQueryText, searchAllModePage.getSearchBarTextValue());

            assertTrue(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_ALL));
            assertFalse(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_ALL));
        }

        @Test
        @Order(2)
        void searchText_partialKeyInput_selectFromPrompts() {
            String inputQueryText = "Input";

            new HomePage(webDriver).sendKeysToSearchBarAndSelectFirstSuggestion(inputQueryText);

            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);

            AssertionUtils.assertContainsIgnoreCase(searchAllModePage.getSearchBarTextValue(), inputQueryText);

            assertTrue(searchAllModePage.isSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_ALL));
            assertFalse(searchAllModePage.isNotSelectedSearchMode(SearchAllModeConstants.SEARCH_MODE_ALL));
        }

        @Test
        @Order(3)
        void searchImage_usingTextQuery() {
            new HomePage(webDriver).submitKeysToSearchBar("Google logo");
            new SearchAllModeWithMoreModesMenuOpenedPage(webDriver).navigateToSearchMode(SearchAllModeConstants.SEARCH_MODE_IMAGE);

            AssertionNoAsserts.fail("1) Assert new ImagePageClass().isSelectedSearchModeImage().");
        }

        @Test
        @Order(4)
        void searchVideo() {
            new HomePage(webDriver).submitKeysToSearchBar("Oldest video");
            new SearchAllModeWithMoreModesMenuOpenedPage(webDriver).navigateToSearchMode(SearchAllModeConstants.SEARCH_MODE_VIDEO);

            AssertionNoAsserts.fail("1) Assert new VideoPageClass().isSelectedSearchModeVideo().");
        }

        @Test
        @Order(5)
        void searchMaps() {
            new HomePage(webDriver).submitKeysToSearchBar("London SW1A 0AA, UK");
            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);
            searchAllModePage.clickSearchModeMore();
            searchAllModePage.navigateToSearchMode(SearchAllModeConstants.SEARCH_MODE_MAPS);

            AssertionNoAsserts.fail("1) Assert new MapsPageClass().isSelectedSearchModeMaps().");
        }

        @Test
        @Order(6)
        void searchNews() {
            new HomePage(webDriver).submitKeysToSearchBar("Latest");
            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);
            searchAllModePage.clickSearchModeMore();
            searchAllModePage.navigateToSearchMode(SearchAllModeConstants.SEARCH_MODE_NEWS);

            AssertionNoAsserts.fail("1) Assert new NewsPageClass().isSelectedSearchModeMaps().");
        }

        @Test
        @Order(7)
        void searchShopping() {
            new HomePage(webDriver).submitKeysToSearchBar("Present");
            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);
            searchAllModePage.clickSearchModeMore();
            searchAllModePage.navigateToSearchMode(SearchAllModeConstants.SEARCH_MODE_SHOPPING);

            AssertionNoAsserts.fail("1) Assert new ShoppingPageClass().isSelectedSearchModeMaps().");
        }

        @Test
        @Order(8)
        void searchBooks() {
            new HomePage(webDriver).submitKeysToSearchBar("Selenium Basics");
            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);
            searchAllModePage.clickSearchModeMore();
            searchAllModePage.navigateToSearchMode(SearchAllModeConstants.SEARCH_MODE_BOOKS);

            AssertionNoAsserts.fail("1) Assert new BooksPageClass().isSelectedSearchModeMaps().");
        }

        @Test
        @Order(9)
        void searchFinance() {
            new HomePage(webDriver).submitKeysToSearchBar("Bitcoin");
            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);
            searchAllModePage.clickSearchModeMore();
            searchAllModePage.navigateToSearchMode(SearchAllModeConstants.SEARCH_MODE_FINANCE);

            AssertionNoAsserts.fail("1) Assert new BooksPageClass().isSelectedSearchModeMaps().");
        }

        @Test
        @Order(10)
        void searchFlights() {
            new HomePage(webDriver).submitKeysToSearchBar("Cracow to London");
            SearchAllModeWithMoreModesMenuOpenedPage searchAllModePage = new SearchAllModeWithMoreModesMenuOpenedPage(webDriver);
            searchAllModePage.clickSearchModeMore();
            searchAllModePage.navigateToSearchMode(SearchAllModeConstants.SEARCH_MODE_FLIGHTS);

            AssertionNoAsserts.fail("1) Assert new BooksPageClass().isSelectedSearchModeMaps().");
        }
    }
}
