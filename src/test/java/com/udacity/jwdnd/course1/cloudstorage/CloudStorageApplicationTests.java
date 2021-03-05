package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	private String username = "rcarbal";
	private String password = "123456";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	public void signupUser(){
		driver.get("http://localhost:" + port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signUp("Ricardo", "Carballo", username, password);
		signupPage.clickSubmit();
	}

	public void loginUser(){
		driver.get("http://localhost:" + port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		loginPage.clickLogin();
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testHomePageIsNotAvailable(){
		driver.get("http://localhost:" + this.port + "/home");
		String title = driver.getTitle();
		Assertions.assertEquals("Login", title);
	}

	@Test
	public void checkHomePageIsAvailable(){
		signupUser();
		loginUser();
		WebElement logoutButton = driver.findElement(By.id("logoutButton"));
		boolean displayed = logoutButton.isDisplayed();

		Assertions.assertTrue(displayed);

		HomePage homePage = new HomePage(driver);
		homePage.logout();
		String loggedOutTitle = driver.getTitle();
		Assertions.assertEquals("Login", loggedOutTitle);
	}

	@Test
	public void checkNoteFunctionality(){
		String noteTitle = "This is the first note";
		String noteDescription = "This the description of the note.";

		signupUser();
		loginUser();
		String loggedIn = driver.getTitle();
		Assertions.assertEquals("Home", loggedIn);


		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("noteModuleButton"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("fake-submit-button"))).click();

		String inResultPage = driver.getTitle();
		Assertions.assertEquals("Result", inResultPage);
	}
}
