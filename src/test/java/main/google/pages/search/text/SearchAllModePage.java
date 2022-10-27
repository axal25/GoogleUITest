package main.google.pages.search.text;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static main.Constants.TIME_OUT_SECONDS;

public class SearchAllModePage {
    @FindBy(how = How.NAME, using = "q")
    private WebElement searchBar;
    @FindBy(how = How.CSS, using = "[aria-current='page']")
    private WebElement selectedSearchMode;
    @FindBy(how = How.XPATH, using = "//div[@class='hdtb-mitem'] //a")
    private List<WebElement> unSelectedSearchModes;
    @FindBy(how = How.CSS, using = "g-popup")
    private WebElement moreSearchModesGPopUp;

    private final WebDriver webDriver;

    SearchAllModePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getSearchBarTextValue() {
        return getSearchBar().getAttribute("value");
    }

    private WebElement getSearchBar() {
        return new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(searchBar));
    }

    public boolean isSelectedSearchMode(SvgModel svgModel) {
        try {
            getSearchModeSvg(getSelectedSearchMode(), svgModel);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isNotSelectedSearchMode(SvgModel svgModel) {
        try {
            getUnSelectedSearchMode(svgModel);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    protected WebElement getUnSelectedSearchMode(SvgModel svgModel) {
        return getUnSelectedSearchModes().stream()
                .map(unSelectedSearchMode -> {
                    try {
                        return getSearchModeSvg(unSelectedSearchMode, svgModel);
                    } catch (TimeoutException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findAny().orElseThrow(() -> new NoSuchElementException(
                        String.format(
                                "Could not find Un-Selected Search Mode matching %s: {\n%s\n}",
                                SvgModel.class.getSimpleName(),
                                svgModel)));
    }

    ImmutableList<WebElement> getUnSelectedSearchModes() {
        return unSelectedSearchModes.stream().map(unSelectedSearchMode ->
                        new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                                .until(ExpectedConditions.elementToBeClickable(unSelectedSearchMode)))
                .collect(toImmutableList());
    }

    WebElement getSearchModeSvg(WebElement parent, SvgModel svgModel) throws TimeoutException {
        WebElement svgInsideSelectedSearchMode =
                new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                        .until(ExpectedConditions
                                .presenceOfNestedElementLocatedBy(
                                        parent,
                                        svgModel.getSvgCssSelector()));
        ImmutableList<WebElement> pathsInsideSvg =
                svgModel.getPathCssSelectors().stream().map(searchModeAllCssSelector ->
                        new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                                .until(ExpectedConditions
                                        .presenceOfNestedElementLocatedBy(
                                                svgInsideSelectedSearchMode,
                                                searchModeAllCssSelector
                                        ))
                ).collect(toImmutableList());
        return svgInsideSelectedSearchMode;
    }

    private WebElement getSelectedSearchMode() {
        return new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(selectedSearchMode));
    }

    public void clickSearchModeMore() {
        getSearchModeMore().click();
    }

    private WebElement getSearchModeMore() {
        return getSearchModeSvg(
                getMoreSearchModesGPopUp(),
                SearchAllModeConstants.SEARCH_MODE_MORE);
    }

    private WebElement getMoreSearchModesGPopUp() {
        return new WebDriverWait(webDriver, Duration.ofSeconds(main.Constants.TIME_OUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(moreSearchModesGPopUp));
    }
}
