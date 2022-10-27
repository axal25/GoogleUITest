package main.google.pages.search.mode.text;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.By;

import java.util.Arrays;

import static com.google.common.collect.ImmutableList.toImmutableList;

class SvgModel {

    private final By.ByCssSelector svgCssSelector;
    private final ImmutableList<By.ByCssSelector> pathCssSelectors;

    SvgModel(String svgCssSelectorString, String... pathCssSelectorStrings) {
        svgCssSelector = (By.ByCssSelector) By.cssSelector(svgCssSelectorString);
        pathCssSelectors = Arrays.stream(pathCssSelectorStrings).map(cssSelector ->
                (By.ByCssSelector) By.cssSelector(cssSelector)).collect(toImmutableList());
    }

    By.ByCssSelector getSvgCssSelector() {
        return svgCssSelector;
    }

    ImmutableList<By.ByCssSelector> getPathCssSelectors() {
        return pathCssSelectors;
    }

    static String getPathCssSelector(String dValue) {
        return String.format("path[d='%s']", dValue);
    }

    @Override
    public String toString() {
        return "SvgModel{" +
                "svgCssSelector=" + svgCssSelector +
                ", pathCssSelectors=" + pathCssSelectors +
                '}';
    }
}
