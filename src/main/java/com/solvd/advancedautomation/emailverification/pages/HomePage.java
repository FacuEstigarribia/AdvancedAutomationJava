package com.solvd.advancedautomation.emailverification.pages;

import com.solvd.advancedautomation.functionality.JS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.LoggerFactory;

public class HomePage {
   // private static final Logger LOGGER =  LoggerFactory.getLogger(HomePage.class);
    @FindBy(xpath = "//input[@id='ea2t']")
    private WebElement emailInputField;

    @FindBy(xpath = "//input[@id='send_test_btn']")
    private WebElement sendTestButton;

    @FindBy(xpath = "//div[@id='send_email']//div[contains(@class, 'success')]")
    private WebElement successfullyEmailSentText;
    private final WebDriver driver;
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    public void inputEmail(String email) {
        emailInputField.sendKeys(email);
    }
    public void clickOnSendBtn() {
        JS.scrollToElement(driver, sendTestButton);
        sendTestButton.click();
    }

    public boolean isEmailSuccessfullySent() {
        return successfullyEmailSentText.isDisplayed();
    }

    public String getSentEmailID() {
        return successfullyEmailSentText.getText().replace("Success - A test message has been sent!\n" + "Email ID: ", "");
    }
}
