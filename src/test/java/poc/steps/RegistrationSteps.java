package poc.steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jbehave.core.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import poc.utils.WebDriverUtils;

import java.time.Duration;

public class RegistrationSteps {

    private WebDriver driver;
    private final short waitTimeInSeconds = 10;

    @BeforeStory
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Given("the user is on the registration page")
    public void openRegistrationPage() {
        driver.get("http://localhost:8080/JSF-JBehave-Selenium-Jacoco/registration.xhtml");
    }

    @When("the user enters a new username and password")
    public void enterNewUsernameAndPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='username']")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='password']")));
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id$='registrationButton']")));

        WebDriverUtils.clearFields(usernameField, usernameField);
        usernameField.sendKeys("newUser");
        passwordField.sendKeys("newPassword");
        registerButton.click();
    }

    @When("the user enters an existing username and password")
    public void enterExistingUsernameAndPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='username']")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='password']")));
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id$='registrationButton']")));

        WebDriverUtils.clearFields(usernameField, usernameField);
        usernameField.sendKeys("newUser"); // Assuming "admin" already exists
        passwordField.sendKeys("newPassword");
        registerButton.click();
    }

    @Then("Then the user is redirected to the login page after registration")
    public void verifyRedirectToLoginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        //wait.until(ExpectedConditions.urlToBe("http://localhost:8080/JSF-JBehave-Selenium-Jacoco/login.xhtml"));
        wait.until(ExpectedConditions.urlContains("login.xhtml"));

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl == null || !currentUrl.equals("http://localhost:8080/JSF-JBehave-Selenium-Jacoco/login.xhtml")) {
            throw new AssertionError("User was not redirected to the login page after registration!");
        }
    }

    @Then("the user stays on the registration page")
    public void verifyUserStaysOnRegistrationPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        //wait.until(ExpectedConditions.urlToBe("http://localhost:8080/JSF-JBehave-Selenium-Jacoco/registration.xhtml"));
        wait.until(ExpectedConditions.urlContains("registration.xhtml"));
    }

    @AfterStory
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
