package main.google.pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static main.Constants.TIME_OUT_SECONDS;

public class HomePage {

    private static final By PROMPT_LIST_LOCATOR = By.cssSelector("ul.G43f7e[role='listbox']");
    private static final By PROMPT_LIST_ITEM_LOCATOR = By.cssSelector("li.sbct[role='presentation']");
    @FindBy(how = How.NAME, using = "q")
    private WebElement searchBar;

    private final WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void sendKeysToSearchBarAndSelectFirstSuggestion(String keys) {
        new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(searchBar));
        searchBar.click();
        searchBar.sendKeys(keys);
        new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(PROMPT_LIST_LOCATOR));
        List<WebElement> promptListItems =
                new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                        .until(
                                ExpectedConditions.presenceOfNestedElementsLocatedBy(
                                        PROMPT_LIST_LOCATOR,
                                        PROMPT_LIST_ITEM_LOCATOR));
        if (promptListItems.isEmpty()) {
            throw new NoSuchElementException("Prompt item list was empty. There was nothing to click.");
        }
        promptListItems.get(0).click();
    }

    public void submitKeysToSearchBar(String keys) {
        new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(searchBar));
        searchBar.sendKeys(keys);
        searchBar.submit();
    }
}
