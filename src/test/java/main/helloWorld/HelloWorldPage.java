package main.helloWorld;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static main.Constants.TIME_OUT_SECONDS;

class HelloWorldPage {
    @FindBy(how = How.ID, using = "spanId")
    private WebElement span;
    private final WebDriver webDriver;

    HelloWorldPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    HelloWorldPage waitOnSpan() {
        new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(span));
        return this;
    }

    WebElement getSpan() {
        return span;
    }
}
