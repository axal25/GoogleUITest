package main;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static class Urls {
        public static final String GOOGLE_HOME = "https://www.google.com/";
        public static final String HELLO_WORLD_FILE = Paths
                .get("src/test/resources/html/helloWorld/helloWorld.html").toUri().toString();
        public static final String BROWSERS_JSON = "classpath:json/browsers.json";
    }

    public static final long TIME_OUT_SECONDS = 10L;
    public static class JvmArgs {
        public static class KeyNames {
            public static class DriverLocations {
                public static final String WEB_DRIVER_CHROME = "jvm.arg.webdriver.chrome.driver";
            }
        }
    }
    public static class EnvVars {
        public static class KeyNames {
            public static class DriverLocations {
                public static final String WEB_DRIVER_CHROME = "webdriver.chrome.driver";
            }
            public static final String WEB_DRIVER_CHROME_WHITE_LISTED_IPS = "webdriver.chrome.whitelistedIps";
        }
    }
}
