package poc.steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jbehave.core.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginSteps {

    private WebDriver driver;

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
    public void enterValidCredentials() {
        try {
            // Use WebDriverWait to wait for the username field to be visible
            /*WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginButton")));*/

            /*WebElement usernameField = driver.findElement(By.id("username"));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.id("loginButton"));*/

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            System.out.println("waiting 20.....");
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            System.out.println("waiting usernameField.....");
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            System.out.println("waiting passwordField.....");
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
            System.out.println("waiting loginButton.....");

            System.out.println("Username field found: " + usernameField.isDisplayed());
            System.out.println("Password field found: " + passwordField.isDisplayed());
            System.out.println("Login button found: " + loginButton.isDisplayed());

            System.out.println("Entering credentials...");

            usernameField.sendKeys("admin");
            passwordField.sendKeys("password");
            loginButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Error interacting with the login form", e);
        }
    }

    //Then("the user is redirected to the welcome page")
    @Then("the user is redirected to the welcome page")
    public void verifyHomepage() {
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        if (!currentUrl.endsWith("login.xhtml")) {
            throw new AssertionError("User was not redirected to the homepage!");
        }
        driver.quit();
    }

    @AfterStory
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
