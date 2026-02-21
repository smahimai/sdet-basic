package com.sdet.tests;

import com.sdet.utils.BrowserUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Task 3: Launch Firefox and verify MakeMyTrip logo is present on the page.
 */
public class MakeMyTripLogoTest {

    private BrowserUtils browserUtils;
    private static final String BASE_URL = "https://www.makemytrip.com/";

    @BeforeMethod
    public void setup() {
        browserUtils = new BrowserUtils();
        browserUtils.launchBrowser("firefox");
    }

    @AfterMethod
    public void teardown() {
        if (browserUtils != null) {
            browserUtils.quit();
        }
    }

    @Test(description = "Verify MakeMyTrip logo is present on the page in Firefox")
    public void verifyMakeMyTripLogoIsPresent() throws InterruptedException {
        browserUtils.navigateTo(BASE_URL);
        Thread.sleep(6000);


        // Close popup if present (span with class commonModal__close)
        By closePopup = By.xpath("//span[contains(@class,'commonModal__close')]");
        if (browserUtils.isElementPresent(closePopup)) {
            browserUtils.click(closePopup);
        }

        // XPath for MakeMyTrip logo - common selectors
        By logoLocator = By.xpath(
                "//img[contains(@alt,'Make My Trip')]"
        );

        boolean logoPresent = browserUtils.isElementPresent(logoLocator);
        Assert.assertTrue(logoPresent, "MakeMyTrip logo should be present on the page");
    }
}
