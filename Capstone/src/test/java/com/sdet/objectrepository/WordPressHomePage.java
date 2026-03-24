package com.sdet.objectrepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WordPressHomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By getWordPressTopLink = By.xpath("//a[contains(text(),'Get WordPress')]"
    );

    public WordPressHomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void launchHomePage() {
        driver.get("https://wordpress.org/");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickGetWordPress() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(getWordPressTopLink));
        link.click();
    }
}
