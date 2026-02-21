package com.sdet.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BrowserUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    public WebDriver launchBrowser(String browserName) {
        if ("firefox".equalsIgnoreCase(browserName)) {
            driver = new FirefoxDriver();
        } else if ("chrome".equalsIgnoreCase(browserName)) {
            driver = new ChromeDriver();
        } else {
            throw new IllegalArgumentException("Supported browsers: firefox, chrome");
        }
        driver.manage().window().maximize();
       // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return driver;
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement findElement(By locator, int timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void click(By locator) {
        findElement(locator).click();
    }

    public void sendKeys(By locator, String text) {
        WebElement element = findElement(locator);
        try {
            element.clear();
        } catch (InvalidElementStateException e) {
            // Input may be read-only or custom (e.g. MakeMyTrip city selector) - type without clear
        }
        element.sendKeys(text);
    }

    /**
     * Tries to send keys to an element with a custom timeout. Returns true if successful.
     */
    public boolean trySendKeys(By locator, String text, int timeoutSeconds) {
        try {
            WebElement element = findElement(locator, timeoutSeconds);
            try {
                element.clear();
            } catch (InvalidElementStateException ignored) {}
            element.sendKeys(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Tries to find and click the first matching element from the given locators.
     * Uses extended timeout for first attempt; shorter timeout for fallback locators.
     */
    public void clickFirstMatch(By[] locators, int timeoutSeconds) {
        for (int i = 0; i < locators.length; i++) {
            try {
                int waitTime = (i == 0) ? timeoutSeconds : 5;
                WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
                WebElement element = customWait.until(ExpectedConditions.elementToBeClickable(locators[i]));
                element.click();
                return;
            } catch (Exception e) {
                // Try next locator
            }
        }
        throw new NoSuchElementException("No suggestion element found with any of the provided locators");
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
