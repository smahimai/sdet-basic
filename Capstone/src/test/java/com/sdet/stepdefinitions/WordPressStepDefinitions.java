package com.sdet.stepdefinitions;

import com.sdet.base.BaseTest;
import com.sdet.objectrepository.WordPressDownloadPage;
import com.sdet.objectrepository.WordPressHomePage;
import com.sdet.objectrepository.WordPressPhotoDirectoryPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class WordPressStepDefinitions extends BaseTest {

    private WordPressHomePage homePage;
    private WordPressDownloadPage downloadPage;
    private WordPressPhotoDirectoryPage photoDirectoryPage;

    @Before
    public void setUp() {
        initializeDriver();
        homePage = new WordPressHomePage(driver, wait);
        downloadPage = new WordPressDownloadPage(driver, wait);
        photoDirectoryPage = new WordPressPhotoDirectoryPage(driver, wait);
    }

    @After
    public void tearDown() {
        closeDriver();
    }

    @Given("I launch the WordPress URL")
    public void iLaunchTheWordPressUrl() {
        homePage.launchHomePage();
    }

    @Then("I verify the page title contains {string}")
    public void iVerifyThePageTitleContains(String expectedTitlePart) {
        Assert.assertTrue(
                homePage.getPageTitle().contains(expectedTitlePart),
                "Page title does not contain expected text: " + expectedTitlePart
        );
    }

    @When("I click on Get WordPress option on top left")
    public void iClickOnGetWordPressOptionOnTopLeft() {
        homePage.clickGetWordPress();
    }

    @Then("I verify middle page text as {string}")
    public void iVerifyMiddlePageTextAs(String expectedText) {
        Assert.assertEquals(
                downloadPage.getMiddleHeadingText(),
                expectedText,
                "Middle page heading text mismatch"
        );
    }

    @And("I click on Community and then Photo Directory")
    public void iClickOnCommunityAndThenPhotoDirectory() {
        downloadPage.openPhotoDirectoryFromCommunity();
    }

    @When("I search for picture name {string}")
    public void iSearchForPictureName(String pictureName) {
        photoDirectoryPage.searchPictureByName(pictureName);
    }

    @Then("I verify pictures are displayed")
    public void iVerifyPicturesAreDisplayed() {
        Assert.assertTrue(photoDirectoryPage.arePicturesDisplayed(), "No pictures are displayed for the search.");
    }
}
