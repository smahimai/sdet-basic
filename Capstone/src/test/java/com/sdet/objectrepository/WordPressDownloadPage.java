package com.sdet.objectrepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WordPressDownloadPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By middleHeading = By.xpath("//h1[contains(text(),'Get WordPress')]");
    private final By communityMenu = By.xpath("//button[.//span[contains(text(),'Community')]]");
    /** Submenu link — visible after Community is clicked */
    private final By photoDirectoryLink = By.xpath("//a[.//span[contains(text(),'Photo Directory')]]");

    public WordPressDownloadPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public String getMiddleHeadingText() {
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(middleHeading));
        return heading.getText().trim();
    }

    public void openPhotoDirectoryFromCommunity() {
        WebElement communityButton = wait.until(ExpectedConditions.elementToBeClickable(communityMenu));

        // Open submenu from Community; brief pause lets the panel render.
        new Actions(driver)
                .moveToElement(communityButton)
                .click()
                .pause(Duration.ofMillis(300))
                .perform();

        WebElement photoDirectory = wait.until(ExpectedConditions.elementToBeClickable(photoDirectoryLink));

        // Keep pointer over the menu: move from trigger to submenu link in one chain so the flyout
        // does not collapse when the cursor would otherwise leave the menu area between actions.
        new Actions(driver)
                .moveToElement(communityButton)
                .pause(Duration.ofMillis(100))
                .moveToElement(photoDirectory)
                .click()
                .perform();
    }
}
