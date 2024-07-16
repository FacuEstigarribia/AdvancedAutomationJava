package com.solvd.advancedautomation;

import base.BaseTest;
import com.solvd.advancedautomation.util.EmailSender;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SendEmailTest extends BaseTest {
    @Test
    public void testSendSuccessfullyEmail() throws Exception {
        EmailSender emailSender = new EmailSender();
        homePage.inputEmail(properties.getProperty("email_from"));
        homePage.clickOnSendBtn();
        Assert.assertTrue(homePage.isEmailSuccessfullySent(), "Success message sent message is not displayed");
        String sentEmailID = homePage.getSentEmailID();
        String idReceivedEmail = emailSender.readEmail(properties.getProperty("email_from"), properties.getProperty("app_password"));
        Assert.assertEquals(sentEmailID, idReceivedEmail, "Sent and received email's ID do not match");
    }
}
