package main.google.pages.terms.and.conditions;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static main.Constants.TIME_OUT_SECONDS;

public class TermsAndConditionsPage {
    @FindBy(how = How.ID, using = "L2AGLb")
    private WebElement acceptButton;
    @FindBy(how = How.ID, using = "W0wltc")
    private WebElement denyButton;
    @FindBy(how = How.ID, using = "CXQnmb")
    private WebElement termsAndConditionsDiv;

    private final WebDriver webDriver;

    public TermsAndConditionsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void acceptIfNecessary() {
        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                    .until(ExpectedConditions.visibilityOf(termsAndConditionsDiv));
        } catch (TimeoutException e) {
            return;
        }
        accept();
    }

    public void accept() {
        clickButton(acceptButton);
    }

    public void deny() {
        clickButton(denyButton);
    }

    private void clickButton(WebElement button) {
        new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }
}
