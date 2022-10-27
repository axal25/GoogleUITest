package main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test for http://formy-project.herokuapp.com/")
public class FormyProjectHerokuAppTest {

    private static final String URL = "http://formy-project.herokuapp.com/";

    @BeforeAll
    static void beforeAll() {
        EnvVarSetter.chromeDriverSetup();
    }

    @Test
    void keyboardAndMouseInput() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "keypress");

        WebElement enterFullNameInput = webDriver.findElement(By.id("name"));
        enterFullNameInput.click();
        enterFullNameInput.sendKeys("Jacek K. O.");

        WebElement button = webDriver.findElement(By.id("button"));
        button.click();

        webDriver.quit();
    }

    @Test
    void autocomplete() throws InterruptedException {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "autocomplete");

        WebElement autocomplete = webDriver.findElement(By.id("autocomplete"));
        autocomplete.sendKeys("1555 Park Blvd, Palo Alto, CA");

        Thread.sleep(1250);

        Boolean gotAnyAnticipatedResult = null;
        try {
            WebElement autocompleteResult = webDriver.findElement(By.className("pac-item"));
            autocompleteResult.click();
            gotAnyAnticipatedResult = true;
        } catch (NoSuchElementException e) {
            System.out.println("[FAILED] Didn't get correct response from Google Maps Api. \n" +
                    "\t\tDidn't find single autocomplete result.");
            e.printStackTrace();
        }

        try {
            webDriver.findElement(By.className("pac-container"));
            WebElement okButton = webDriver.findElement(By.className("dismissButton"));
            okButton.click();
            gotAnyAnticipatedResult = true;
        } catch (NoSuchElementException e) {
            System.out.println("[FAILED] Didn't get result: \n" +
                    "\t\t\"This page can't load Google Maps correctly.\n" +
                    "\t\tDo you own this website? [OK]\"");
            e.printStackTrace();
        }

        webDriver.quit();
        if(!Boolean.TRUE.equals(gotAnyAnticipatedResult)) {
            fail("[FAILED RESULT] Didn't get any anticipated Result");
        }
        System.out.println("[SUCCESS RESULT] Expected behavior.");
    }

    @Test
    void scroll() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "scroll");

        WebElement name = webDriver.findElement(By.id("name"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(name);
        name.sendKeys("Jacek K. O.");
        WebElement date = webDriver.findElement(By.id("date"));
        date.sendKeys("12/12/2012");

        webDriver.quit();
    }

    @Test
    void switchWindow() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "switch-window");

        WebElement newTabButton = webDriver.findElement(By.id("new-tab-button"));
        newTabButton.click();

        String originalHandle = webDriver.getWindowHandle();
        Set<String> windowHandles = webDriver.getWindowHandles();
        windowHandles.forEach(windowHandle -> webDriver.switchTo().window(windowHandle));
        webDriver.switchTo().window(originalHandle);

        webDriver.quit();
    }

    @Test
    void switchAlert() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "switch-window");

        WebElement alertButton = webDriver.findElement(By.id("alert-button"));
        alertButton.click();

        Alert alert = webDriver.switchTo().alert();
        alert.accept();

        webDriver.quit();
    }

    @Test
    void modalWithoutJavaScript() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "modal");

        WebElement modalButton = webDriver.findElement(By.id("modal-button"));
        modalButton.click();

        WebElement closeButton = webDriver.findElement(By.id("close-button"));
        closeButton.click();

        webDriver.quit();
    }

    @Test
    void modalJavaScript() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "modal");

        WebElement modalButton = webDriver.findElement(By.id("modal-button"));
        modalButton.click();

        WebElement closeButton = webDriver.findElement(By.id("close-button"));

        JavascriptExecutor jsExec = (JavascriptExecutor) webDriver;
        jsExec.executeScript("arguments[0].click();", closeButton);

        webDriver.quit();
    }

    @Test
    void dragDrop() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "dragdrop");

        WebElement image = webDriver.findElement(By.id("image"));

        WebElement box = webDriver.findElement(By.id("box"));

        Actions actions = new Actions(webDriver);
        actions.dragAndDrop(image, box).build().perform();

        webDriver.quit();
    }

    @Test
    void byCssSelector() {
        WebDriver webDriver = new ChromeDriver();
        // <input id="idValue", class="classValue", name="nameValue">
//        webDriver.findElement(By.cssSelector("#idValue"));
//        webDriver.findElement(By.cssSelector(".classValue"));
//        webDriver.findElement(By.cssSelector("input[name='nameValue']"));

        // <tagValue id="idValue class="classValue" type="typeValue" value="valueValue">
//        webDriver.findElement(By.cssSelector("tagValue.classValue"));
//        webDriver.findElement(By.cssSelector("tagValue#idValue"));
//        webDriver.findElement(By.cssSelector("tagValue[type='typeValue']"));
//        webDriver.findElement(By.cssSelector("tagValue[value='valueValue'"));

        // <tagValue type="typeValue" class="classValue1 classValue2">
//        webDriver.findElement(By.cssSelector(".classValue1.classValue2"));

        // a[id^='id_prefix_']
        // a[id$='_id_suffix']
        // a[id*='_substring_pattern_']
        // a[id='exact_match_string']

        // <tagValue value="prefix_substring_suffix">

        // PREFIX
//        webDriver.findElement(By.cssSelector("tagValue[value^='prefix_']"));

        // SUFFIX
//        webDriver.findElement(By.cssSelector("tagValue[value$='_suffix']"));

        // SUBSTRING
//        webDriver.findElement(By.cssSelector("tagValue[value*='_substring_']"));

        // EXACT MATCH
//        webDriver.findElement(By.cssSelector("tagValue[value='prefix_substring_suffix']"));

    }

    @Test
    void byChild() {
        WebDriver webDriver = new ChromeDriver();
        // <tagValueParent id="idValueParent">
        // <tagValueChild id="idValueChild">
        // webDriver.findElement(By.cssSelector("tagValueParent#idValueParent tagValueChild"));

        // <tagValueParent id="idValueParent">
        // <tagValueChildren id="idValueChild1">
        // <tagValueChildren id="idValueChild2">
        // webDriver.findElement(By.cssSelector("#idValueParent tagValueChildren:nth-child(1)"));
    }

    @Test
    void radioButton() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "radiobutton");

        WebElement radioButton1 = webDriver.findElement(By.id("radio-button-1"));
        radioButton1.click();

        WebElement radioButton2 = webDriver.findElement(By.cssSelector("input[value='option2']"));
        radioButton2.click();

        // using XPath
        WebElement radioButton3 = webDriver.findElement(By.xpath("/html/body/div/div[3]/input"));
        radioButton3.click();

        webDriver.quit();
    }

    @Test
    void datePicker() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "datepicker");

        WebElement datePicker = webDriver.findElement(By.id("datepicker"));
        datePicker.sendKeys("12/12/2012");
        datePicker.sendKeys(Keys.ENTER);

        webDriver.quit();
    }

    @Test
    void dropDown() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "dropdown");

        WebElement dropdownMenuButton = webDriver.findElement(By.id("dropdownMenuButton"));
        dropdownMenuButton.click();

        WebElement autocomplete = webDriver.findElement(By.id("autocomplete"));
        autocomplete.click();

        webDriver.quit();
    }

    @Test
    void fileUpload() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "fileupload");

        WebElement fileUploadField = webDriver.findElement(By.id("file-upload-field"));
        fileUploadField.sendKeys("google_logo.png");

        webDriver.quit();
    }

    @Test
    void implicitWait_autocomplete() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "autocomplete");

        WebElement autocomplete = webDriver.findElement(By.id("autocomplete"));
        autocomplete.sendKeys("1555 Park Blvd, Palo Alto, CA");

        // deprecated
        webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(Duration.of(1L, ChronoUnit.SECONDS));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1L));

        Boolean gotAnyAnticipatedResult = null;
        try {
            WebElement autocompleteResult = webDriver.findElement(By.className("pac-item"));
            autocompleteResult.click();
            gotAnyAnticipatedResult = true;
        } catch (NoSuchElementException e) {
            System.out.println("[FAILED] Didn't get correct response from Google Maps Api. \n" +
                    "\t\tDidn't find single autocomplete result.");
            e.printStackTrace();
        }

        try {
            webDriver.findElement(By.className("pac-container"));
            WebElement okButton = webDriver.findElement(By.className("dismissButton"));
            okButton.click();
            gotAnyAnticipatedResult = true;
        } catch (NoSuchElementException e) {
            System.out.println("[FAILED] Didn't get result: \n" +
                    "\t\t\"This page can't load Google Maps correctly.\n" +
                    "\t\tDo you own this website? [OK]\"");
            e.printStackTrace();
        }

        webDriver.quit();
        if(!Boolean.TRUE.equals(gotAnyAnticipatedResult)) {
            fail("[FAILED RESULT] Didn't get any anticipated Result");
        }
        System.out.println("[SUCCESS RESULT] Expected behavior.");
    }

    @Test
    void explicitWait_autocomplete() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "autocomplete");

        WebElement autocomplete = webDriver.findElement(By.id("autocomplete"));
        autocomplete.sendKeys("1555 Park Blvd, Palo Alto, CA");

        // deprecated
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        Boolean gotAnyAnticipatedResult = null;
        try {
            WebElement autocompleteResult = wait.until(
                    ExpectedConditions
                            .visibilityOfElementLocated(
                                    By.className("pac-item")));
            // Other conditions
            // ExpectedConditions.elementToBeClickable(By.id("..."));
            // ExpectedConditions.elementToBeSelected(By.id("..."));
            // ExpectedConditions.presenceOfElementLocated(By.id("..."));
            // ExpectedConditions.textToBePresentInElement(By.id("..."), "text");
            // www.selenium.dev

            autocompleteResult.click();
            gotAnyAnticipatedResult = true;
        } catch (TimeoutException e) {
            System.out.println("[FAILED] Didn't get correct response from Google Maps Api. \n" +
                    "\t\tDidn't find single autocomplete result.");
            e.printStackTrace();
        }

        try {
            webDriver.findElement(By.className("pac-container"));
            WebElement okButton = webDriver.findElement(By.className("dismissButton"));
            okButton.click();
            gotAnyAnticipatedResult = true;
        } catch (NoSuchElementException e) {
            System.out.println("[FAILED] Didn't get result: \n" +
                    "\t\t\"This page can't load Google Maps correctly.\n" +
                    "\t\tDo you own this website? [OK]\"");
            e.printStackTrace();
        }

        webDriver.quit();
        if(!Boolean.TRUE.equals(gotAnyAnticipatedResult)) {
            fail("[FAILED RESULT] Didn't get any anticipated Result");
        }
        System.out.println("[SUCCESS RESULT] Expected behavior.");
    }

    @Test
    void form() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(URL);

        webDriver.navigate().to(URL + "form");

        webDriver.findElement(By.id("first-name")).sendKeys("FirstName");
        webDriver.findElement(By.id("last-name")).sendKeys("LastName");
        webDriver.findElement(By.id("job-title")).sendKeys("JobTitle");
        webDriver.findElement(By.id("radio-button-2")).click();
        webDriver.findElement(By.id("checkbox-1")).click();

        webDriver.findElement(By.id("select-menu")).click();
        webDriver.findElement(By.cssSelector("option[value='2']")).click();

        webDriver.findElement(By.id("datepicker")).sendKeys("12/12/2022");
        webDriver.findElement(By.id("datepicker")).sendKeys(Keys.ENTER);

        webDriver.findElement(By.cssSelector(".btn.btn-lg.btn-primary")).click();

        assertEquals(new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success")))
                .getText(), "The form was successfully submitted!");

        webDriver.quit();
    }

    @Test
    void cookie() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().addCookie(new Cookie("cookieName", "cookieValue", "/path/"));
    }

    @Test
    void localStorage() {
        WebDriver webDriver = new ChromeDriver();
        WebStorage webStorage = (WebStorage) webDriver;
        LocalStorage localStorage = webStorage.getLocalStorage();
        SessionStorage sessionStorage = webStorage.getSessionStorage();

        String someItem = "some_item";
        assertTrue(localStorage.keySet().contains(someItem));

        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) webStorage;
        List<Map<String, Object>> values =
                (List<Map<String, Object>>) javaScriptExecutor.executeScript(
                        "return JSON.parse(arguments[0])",
                        localStorage.getItem(someItem));

    }

    void seleniumGridCI() {
        // https://www.selenium.dev/downloads/
        // java -jar selenium-server-4.5.2.jar standalone -role hub
        // java -jar selenium-server-4.5.2.jar jrole node -hub http://...:4444
        // ...:4444/grid/console
        // java -Dwebdriver.chrome.driver=/usr/lib/chromium-browser/chromedriver -jar selenium-server-standalone-2.31.0.jar
    }
}
