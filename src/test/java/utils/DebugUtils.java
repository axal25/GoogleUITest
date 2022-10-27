package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

// TODO delete
public class DebugUtils {

    public static void dragAndDrop(WebDriver webDriver, WebElement webElement) {
        Actions actions = new Actions(webDriver);
        actions.dragAndDropBy(webElement, 20, 20)
                .pause(Duration.ofSeconds(5))
                .build().perform();
    }
}
