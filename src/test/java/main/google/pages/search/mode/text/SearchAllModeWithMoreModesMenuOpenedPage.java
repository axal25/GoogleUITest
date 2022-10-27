package main.google.pages.search.mode.text;

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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static main.Constants.TIME_OUT_SECONDS;

public class SearchAllModeWithMoreModesMenuOpenedPage extends SearchAllModePage {

    @FindBy(how = How.CSS, using = "g-menu.cF4V5c.zriOQb.UU8UAb.gLSAk.rShyOb g-menu-item.ErsxPb a")
    private List<WebElement> moreSearchModeMenuItems;

    private final WebDriver webDriver;

    public SearchAllModeWithMoreModesMenuOpenedPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void navigateToSearchMode(SvgModel svgModel) {
        getUnSelectedSearchMode(svgModel).click();
    }

    @Override
    protected WebElement getUnSelectedSearchMode(SvgModel svgModel) {
        return Stream.of(super.getUnSelectedSearchModes(), getSafelyMoreSearchModeMenuItems())
                .flatMap(Collection::stream)
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

    private ImmutableList<WebElement> getSafelyMoreSearchModeMenuItems() {
        clickSearchModeMoreIfMenuItemsNotPresent();
        return getMoreSearchModeMenuItems();
    }

    private void clickSearchModeMoreIfMenuItemsNotPresent() {
        try {
            getMoreSearchModeMenuItems();
        } catch (TimeoutException e) {
            clickSearchModeMore();
            getMoreSearchModeMenuItems();
        }
    }

    private ImmutableList<WebElement> getMoreSearchModeMenuItems() {
        return moreSearchModeMenuItems.stream().map(moreSearchModesOption ->
                        new WebDriverWait(webDriver, Duration.ofSeconds(TIME_OUT_SECONDS))
                                .until(ExpectedConditions.elementToBeClickable(moreSearchModesOption)))
                .collect(toImmutableList());
    }
}
