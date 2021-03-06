package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.RootPage;
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

	private String username = "rcarbaleq2";
	private String password = "123456";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver()
				.setup();
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

	private boolean checkIfIDExists(String elementId){
		WebElement element = null;

		try {

			element = driver.findElement(By.id(elementId));

		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Order(1)
	@Test
	public void testHomePageIsNotAvailable(){
		driver.get("http://localhost:" + this.port + "/home");
		String title = driver.getTitle();
		Assertions.assertEquals("Login", title);
	}


	@Order(2)
	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Order(3)
	@Test
	public void checkThatYouCanLogoutUser(){
		signupUser();
		loginUser();
		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement logoutButton = driver.findElement(By.id("logoutButton"));
		boolean displayed = logoutButton.isDisplayed();

		Assertions.assertTrue(displayed);

		RootPage homePage = new RootPage(driver);
		homePage.logout();
		wait.until(ExpectedConditions.titleContains("Login"));
		String loggedOutTitle = driver.getTitle();
		Assertions.assertEquals("Login", loggedOutTitle);
	}

	@Order(4)
	@Test
	public void addNoteTest(){
		loginUser();
		String noteTitle = "Just a note";
		String noteDescription = "This the description of the note.";

		String loggedIn = driver.getTitle();
		Assertions.assertEquals("Home", loggedIn);


		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("noteModuleButton"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);

		// CLICK FAKE SUBMIT BUTTON
		wait.until(ExpectedConditions.elementToBeClickable(By.id("fake-notes-submit-button"))).click();

		String inResultPage = driver.getTitle();
		Assertions.assertEquals("Result", inResultPage);


		// verify that note is visible
		driver.get("http://localhost:" + this.port + "/home");
		WebElement noteTab2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteTab2);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-edit-button"))).click();
		WebElement element1 = driver.findElement(By.id("note-title"));
		wait.until(ExpectedConditions.elementToBeClickable(element1)).click();
		String retrievedTitle = element1.getAttribute("value");
		WebElement element2 = driver.findElement(By.id("note-description"));
		wait.until(ExpectedConditions.elementToBeClickable(element2)).click();
		String retrievedDescription = element2.getAttribute("value");

		Assertions.assertTrue(retrievedTitle.equals(noteTitle) && retrievedDescription.equals(noteDescription));
	}

	@Order(5)
	@Test()
	public void testEditNote(){
		signupUser();
		loginUser();

		driver.get("http://localhost:" + this.port + "/home");

		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-edit-button"))).click();

		//Update Description text
		String textToUpdate = "This is updated Text for the description!!!!!!!";
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(textToUpdate);


		wait.until(ExpectedConditions.elementToBeClickable(By.id("fake-notes-submit-button"))).click();

		String inResultPage = driver.getTitle();
		Assertions.assertEquals("Result", inResultPage);

		// verify that note is visible
		driver.get("http://localhost:" + this.port + "/home");
		WebElement noteTab2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteTab2);

		//verify note was changed
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-edit-button"))).click();
		WebElement element1 = driver.findElement(By.id("note-title"));
		wait.until(ExpectedConditions.elementToBeClickable(element1)).click();
		String retrievedTitle = element1.getAttribute("value");
		WebElement element2 = driver.findElement(By.id("note-description"));
		wait.until(ExpectedConditions.elementToBeClickable(element2)).click();
		String retrievedDescription = element2.getAttribute("value");

		Assertions.assertTrue(retrievedDescription.equals(textToUpdate));
	}

	@Order(6)
	@Test
	public void deleteNote(){
		loginUser();

		driver.get("http://localhost:" + this.port + "/home");

		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteNode"))).click();

		String inResultPage = driver.getTitle();
		Assertions.assertEquals("Result", inResultPage);

		// Verify delete does not exist
		driver.get("http://localhost:" + this.port + "/home");

		WebElement navElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", navElement);

		boolean verifyDeletedNote = checkIfIDExists("deleteNode");

		Assertions.assertFalse(verifyDeletedNote);
	}

	@Order(7)
	@Test
	public void createCredential(){
		loginUser();

		driver.get("http://localhost:" + this.port + "/home");

		String url = "https://google.com";
		String username = "username";
		String password = "password";

		// ADD CREDENTIAL
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

		//OPEN MODAL
		wait.until(ExpectedConditions.elementToBeClickable(By.id("showCredentialButton"))).click();

		// SET FIELDS IN MODAL
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(url);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(username);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(password);

		// CLICK ON THE SUBMIT BUTTON
		wait.until(ExpectedConditions.elementToBeClickable(By.id("fake-credentials-button"))).click();
		String inResultPage = driver.getTitle();
		Assertions.assertEquals("Result", inResultPage);

		// GO TO HOME PAGE
		driver.get("http://localhost:" + this.port + "/home");
		String backToHomePage = driver.getTitle();
		Assertions.assertEquals("Home", backToHomePage);

		//GO TO CREDENTIALS TAB
		WebElement backToCredentials = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("nav-credentials-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", backToCredentials);

		// CLICK THE CREDENTIAL EDIT BUTTON
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialEditButton"))).click();

		// GET VALUES FROM CREDENTIAL FIELD
		String retrievedUrl = driver.findElement(By.id("credential-url")).getAttribute("value");
		String retrievedUsername = driver.findElement(By.id("credential-username")).getAttribute("value");
		String retrievedPassword = driver.findElement(By.id("credential-password")).getAttribute("value");

		Assertions.assertTrue(retrievedUrl.equals(url)
				&& retrievedUsername.equals(username)
				&& retrievedPassword.equals(password));
	}

	@Order(8)
	@Test
	public void updateExistingCredentials(){
		loginUser();
		WebDriverWait wait = new WebDriverWait(driver, 5);

		String urlUpdated = "http://udpatedUrl.com";
		String userNameUpdated ="UpdatedUserName";
		String passwordUpdated ="UpdatedPassword1234";

		// get back to home page
		driver.get("http://localhost:" + this.port + "/home");
		String onHomePage = driver.getTitle();
		Assertions.assertEquals("Home", onHomePage);

		// got to credential tab
		WebElement backToCredentials2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("nav-credentials-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", backToCredentials2);

		// Click the credential edit button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialEditButton"))).click();

		// UPDATE CRED
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(urlUpdated);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(userNameUpdated);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(passwordUpdated);

		// CLICK ON THE SUBMIT BUTTON
		wait.until(ExpectedConditions.elementToBeClickable(By.id("fake-credentials-button"))).click();
		String backToResult = driver.getTitle();
		Assertions.assertEquals("Result", backToResult);

		// get back to home page
		driver.get("http://localhost:" + this.port + "/home");
		String backToRoot = driver.getTitle();
		Assertions.assertEquals("Home", backToRoot);

		// got to credential tab
		WebElement backToCredentials3 = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("nav-credentials-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", backToCredentials3);

		// Click the credential edit button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialEditButton"))).click();

		// Get values of the updated credential.
		String urlUpdatedRetrieved = driver.findElement(By.id("credential-url")).getAttribute("value");
		String usernameUpdatedRetrieved = driver.findElement(By.id("credential-username")).getAttribute("value");
		String passwordUpdatedRetrieved = driver.findElement(By.id("credential-password")).getAttribute("value");

		Assertions.assertTrue(urlUpdated.equals(urlUpdatedRetrieved)
				&& userNameUpdated.equals(usernameUpdatedRetrieved)
				&& passwordUpdated.equals(passwordUpdatedRetrieved));
	}

	@Order(9)
	@Test
	public void deleteCredential(){
		loginUser();

		WebDriverWait wait = new WebDriverWait(driver, 5);

		// get back to home page
		driver.get("http://localhost:" + this.port + "/home");
		String homeForeDelete = driver.getTitle();
		Assertions.assertEquals("Home", homeForeDelete);

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteCred"))).click();

		String inResultPage = driver.getTitle();
		Assertions.assertEquals("Result", inResultPage);

		// Verify delete does not exist
		driver.get("http://localhost:" + this.port + "/home");

		WebElement navElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", navElement);

		boolean deleteCred = checkIfIDExists("deleteCred");

		Assertions.assertFalse(deleteCred);
	}
}
