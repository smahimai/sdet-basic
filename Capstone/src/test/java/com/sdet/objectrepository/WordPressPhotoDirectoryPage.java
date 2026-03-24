package com.sdet.objectrepository;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WordPressPhotoDirectoryPage {

    private final WebDriverWait wait;

    private final By searchInput = By.xpath("//input[@id='wp-block-search__input-8']");
    /** Photo grid: each result is a list item under ul with an image inside */
    private final By resultPictures = By.xpath("//main//ul/li[.//img]");

    public WordPressPhotoDirectoryPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
    }

    public void searchPictureByName(String pictureName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(pictureName);
        input.sendKeys(Keys.ENTER);
    }

    public boolean arePicturesDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultPictures));
        List<WebElement> listItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultPictures));
        if (listItems.isEmpty()) {
            return false;
        }
        return listItems.stream()
                .anyMatch(li -> li.findElements(By.tagName("img")).stream().anyMatch(WebElement::isDisplayed));
    }
}
