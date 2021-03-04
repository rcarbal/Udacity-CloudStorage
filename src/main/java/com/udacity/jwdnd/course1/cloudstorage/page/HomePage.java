package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {

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


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
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
}
