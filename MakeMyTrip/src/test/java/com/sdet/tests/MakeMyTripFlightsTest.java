package com.sdet.tests;

import com.sdet.utils.BrowserUtils;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Task 4: Launch Chrome, click Flights, select OneWay, enter FROM and TO locations.
 * WebElements located using XPaths.
 */
public class MakeMyTripFlightsTest {

    private BrowserUtils browserUtils;
    private static final String BASE_URL = "https://www.makemytrip.com/";

    // XPaths for Flights, OneWay, FROM and TO web elements
    private static final By FLIGHTS_TAB = By.xpath("//li[@data-cy='menu_Flights']");
    private static final By ONE_WAY_OPTION = By.xpath("//li[@data-cy='oneWayTrip']");
    private static final By FROM_INPUT = By.xpath("//input[@id='fromCity']");
    private static final By TO_INPUT = By.xpath("//input[@id='toCity']");
    // Search input inside the city modal (appears after clicking fromCity/toCity)
    private static final By CITY_MODAL_SEARCH = By.xpath("//input[contains(@placeholder,'From') or contains(@placeholder,'To') or contains(@placeholder,'Search') or contains(@placeholder,'city')]");
    // Suggestion dropdown - try multiple selectors (MakeMyTrip may use different class names)
    private static final By[] SUGGESTION_LOCATORS = {
            By.xpath("//div[contains(@class,'revampedSearchSuggestionMain')]"),
            By.xpath("//li[@role='option'][1]"),
            By.xpath("//ul[contains(@class,'suggestions')]//li[1]"),
            By.xpath("//p[contains(@class,'appendBottom5')]")
    };
    private static final By SUBMIT_BUTTON = By.xpath("//p[contains(@data-cy,'submit')]");

    @BeforeMethod
    public void setup() {
        browserUtils = new BrowserUtils();
        browserUtils.launchBrowser("chrome");
    }

    @AfterMethod
    public void teardown() {
        if (browserUtils != null) {
            browserUtils.quit();
        }
    }

    @Test(description = "Click Flights, select OneWay, enter FROM and TO locations in Chrome")
    public void selectFlightsOneWayAndEnterLocations() throws InterruptedException {
        browserUtils.navigateTo(BASE_URL);
        Thread.sleep(25000);

        // Close login/signup popup if present
        closePopupIfPresent();

        // Wait for Flights tab and click
        // browserUtils.waitForElementVisible(FLIGHTS_TAB);
        browserUtils.click(FLIGHTS_TAB);

        // // Wait for OneWay option and select
        // browserUtils.waitForElementVisible(ONE_WAY_OPTION);
        // browserUtils.click(ONE_WAY_OPTION);

        // FROM: click to open modal, type in modal search input (or fromCity), select first suggestion
        browserUtils.click(FROM_INPUT);
        Thread.sleep(2000);
        if (!browserUtils.trySendKeys(CITY_MODAL_SEARCH, "MAA", 8)) {
            browserUtils.sendKeys(FROM_INPUT, "MAA");
        }
        Thread.sleep(2000);
        browserUtils.clickFirstMatch(SUGGESTION_LOCATORS, 25);

        // TO: click to open modal, type in modal search input (or toCity), select first suggestion
        browserUtils.click(TO_INPUT);
        Thread.sleep(2000);
        if (!browserUtils.trySendKeys(CITY_MODAL_SEARCH, "DEL", 8)) {
            browserUtils.sendKeys(TO_INPUT, "DEL");
        }
        Thread.sleep(2000);
        browserUtils.clickFirstMatch(SUGGESTION_LOCATORS, 25);

        // Wait for submit button and click
        browserUtils.click(SUBMIT_BUTTON);
    }

    private void closePopupIfPresent() {
        try {
            By closePopup = By.xpath("//span[contains(@class,'commonModal__close')]");
            if (browserUtils.isElementPresent(closePopup)) {
                browserUtils.click(closePopup);
            }
        } catch (Exception ignored) {
            // Popup may not be present
        }
    }
}
