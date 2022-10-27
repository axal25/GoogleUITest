# GoogleUITest

Selenium Project

A recruitment task done in 5 working days.

Tests google search page.

#### Required tools

Chrome

### Tech stack

Java (11), Maven (3.6.3), Selenium

#### How to run

1. To run the tests
   `mvn clean test`
2. To test with self-provided Chrome Driver  
   `mvn test -Dmvn.webdriver.chrome.driver="path/to/chromedriver"`  
   example  
   `mvn test -Djvm.arg.webdriver.chrome.driver="/home/user/webdrivers/unix/x64/chromedriver_106.0.5249.61"`
4. To test and get the jar package
   `mvn clean test`  
   `mvn clean package -DskipTests`
   or
   `mvn clean package`

##### Comments

1. It's my first project using Selenium.
2. I tested it only on Ubuntu with Chrome.
3. Main test class is  
   `src/test/java/main/google/web/driver/custom/GoogleCustomWebDriverTest.java`
4. There are also smoke test class for browsers other than Chrome defined in  
   `src/test/resources/json/browsers.json`.  
   Browser smoke class testing on google.com  
   `src/test/java/main/google/web/driver/selenium/GoogleSeleniumJupiterWebDriverTest.java`  
   Browser smoke class testing on local (included page)  
   `src/test/java/main/helloWorld/HelloWorldTest.java`