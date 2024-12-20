package poc.steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import poc.utils.WebDriverUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class LoginSteps {

    private WebDriver driver;
    private final short waitTimeInSeconds = 10;

    @BeforeStory
    public void setup() {
        WebDriverManager.chromedriver().setup(); // Automatically downloads the latest ChromeDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    //@Given("the user is on the login page")
    @Given("the user is on the login page")
    public void openLoginPage() {
        // Use WebDriver to open the page
        driver.get("http://localhost:8080/JSF-JBehave-Selenium-Jacoco/login.xhtml");
    }

    //@When("the user enters valid credentials")
    @When("the user enters valid credentials")
    public void enterValidCredentials() throws IOException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));

            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='username']")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='password']")));
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id$='loginButton']")));

            WebDriverUtils.clearFields(usernameField, usernameField);
            usernameField.sendKeys("admin");
            passwordField.sendKeys("password");
            loginButton.click();

        } catch (Exception e) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("error-screenshot.png"));
            throw new RuntimeException("Error interacting with the login form", e);
        }
    }

    @When("the user enters invalid credentials")
    public void enterInvalidCredentials() throws IOException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));

            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='username']")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='password']")));
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id$='loginButton']")));


            WebDriverUtils.clearFields(usernameField, usernameField);
            usernameField.sendKeys("invalidUser");
            passwordField.sendKeys("wrongPassword");
            loginButton.click();

        } catch (Exception e) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("error-screenshot.png"));
            throw new RuntimeException("Error interacting with the login form", e);
        }
    }


    //Then("the user is redirected to the welcome page")
    @Then("the user is redirected to the welcome page")
    public void verifyHomepage() {
        // Wait for the URL to contain 'welcome.xhtml'
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        wait.until(ExpectedConditions.urlContains("welcome.xhtml")); // Replace with the actual path of your welcome page

        // Assert that the URL is correct after the redirection
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl == null || !currentUrl.contains("welcome.xhtml")) {
            throw new AssertionError("User was not redirected to the welcome page!");
        }
    }

    @Then("the user stays on the login page")
    public void verifyUserStaysOnLoginPage() {
        // Wait for the URL to remain the same (login page URL)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/JSF-JBehave-Selenium-Jacoco/login.xhtml"));

        // Assert that the current URL is still the login page URL
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl == null || !currentUrl.equals("http://localhost:8080/JSF-JBehave-Selenium-Jacoco/login.xhtml")) {
            throw new AssertionError("User was redirected away from the login page!");
        }
    }

    @Given("the user is logged in")
    public void ensureUserIsLoggedIn() throws IOException {
        openLoginPage();
        enterValidCredentials();
        verifyHomepage();
    }

    @When("the user clicks the logout button")
    public void clickLogoutButton() throws IOException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));

            WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id$='logoutButton']")));
            logoutButton.click();

        } catch (Exception e) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("error-logout-screenshot.png"));
            throw new RuntimeException("Error interacting with the logout button", e);
        }
    }

    @Then("the user is redirected to the login page")
    public void verifyUserIsRedirectedToLoginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/JSF-JBehave-Selenium-Jacoco/login.xhtml"));

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl == null || !currentUrl.equals("http://localhost:8080/JSF-JBehave-Selenium-Jacoco/login.xhtml")) {
            throw new AssertionError("User was not redirected to the login page after logout!");
        }
    }

    @AfterStory
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
