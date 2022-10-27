package main;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.StringUtils;

public class EnvVarSetter {

    /**
     * If Chrome Driver location was passed as argument to Maven execution then
     * set this location as execution environment variable,
     * else use Chrom Driver provided by WebDriverManager.
     * {@code mvn test -Dmvn.webdriver.chrome.driver="path/to/chromedriver"}
     * example:
     * {@code mvn test -Djvm.arg.webdriver.chrome.driver="/home/user/webdrivers/unix/x64/chromedriver_106.0.5249.61"}
     */
    public static void chromeDriverSetup() {
        String envVarValueWebDriverChromeDriver = System.getProperty(
                Constants.JvmArgs.KeyNames.DriverLocations.WEB_DRIVER_CHROME);
        if (!StringUtils.isNullOrEmptyOrBlank(envVarValueWebDriverChromeDriver)) {
            setCustomWebDriverLocation(envVarValueWebDriverChromeDriver);
        } else {
            WebDriverManager.chromedriver().setup();
        }
        whiteListAllIps();
    }

    /**
     * Sets environment variable for the location of Chrome Driver.
     * @param pathToChromeDriver "path/to/chromedriver"
     */
    private static void setCustomWebDriverLocation(String pathToChromeDriver) {
        System.setProperty(
                Constants.EnvVars.KeyNames.DriverLocations.WEB_DRIVER_CHROME,
                pathToChromeDriver
        );
    }

    private static void whiteListAllIps() {
        System.setProperty(
                Constants.EnvVars.KeyNames.WEB_DRIVER_CHROME_WHITE_LISTED_IPS,
                StringUtils.EMPTY);
    }
}
