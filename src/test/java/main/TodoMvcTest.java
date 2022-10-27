package main;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TodoMvcTest {

    @BeforeAll
    static void beforeAll() {
//        Configuration.browser = "firefox";
        Configuration.browser = "chrome";
    }

    @Test
    void todomvc_examples_angularjs() {
        // open("https://www.google.com");
        open("https://todomvc.com/examples/angularjs");

        // j-query like
        $("input.new-todo").sendKeys("Show Selenide");
        $("input.new-todo").pressEnter();

        $(withText("Show Selenide")).shouldBe(visible);
    }
}
