package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class RootPage {

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "noteModuleButton")
    private WebElement showNoteModuleButton;

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialTab;

    private WebDriverWait wait;
    private WebDriver driver;
    private final JavascriptExecutor js;


    public RootPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
        this.js = (JavascriptExecutor) driver;
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        js.executeScript("arguments[0].click();", logoutButton);
    }

    public void clickShowNoteModuleButton() {
        showNoteModuleButton.click();
    }

    public void setNoteTitle(String noteTitle) {
        noteTitleField.sendKeys(noteTitle);
    }

    public void setNoteDescription(String noteDescription) {
        noteDescriptionField.sendKeys(noteDescription);
    }

    public void clickOnNoteTab() {
        noteTab.click();
    }

    public void clickSubmitNote() {
        noteSubmitButton.click();
    }

    public void clickCredentialsTab() {
		wait.until(ExpectedConditions.elementToBeClickable(credentialTab));
        js.executeScript("arguments[0].click();", credentialTab);
    }
}
