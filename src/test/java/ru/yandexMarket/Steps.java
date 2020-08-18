package ru.yandexMarket;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class Steps extends WebDriverSettings {

    private final By SELECTNOVOSIBIRSK = By.cssSelector("[class=\"_2EPSjI-GdM _2s55WErgIp BCVQlNQsVv\"]");
    private final By PRODUCTNAME = By.xpath("//h3/a[contains(@title, '')]");
    private final By COMPUTERCHAPTER = By.cssSelector("[href=\"/catalog--kompiuternaia-tekhnika/54425\"]");
    private final By TABLETCHAPTER = By.cssSelector("[href=\"/catalog--planshety-v-novosibirske/54545/list?hid=6427100\"]");
    private final By PRICEFILTER = By.cssSelector("[data-autotest-id=\"dprice\"]");
    private final By SEARCHFIELD = By.id("header-search");
    private final By SUBMITBUTTON = By.xpath("//button[@type='submit']");

    private String secondElement = "";
    private String price = "";

    @Test
    public void openYandexMarketPage() {
        driver.get("https://market.yandex.ru/");
        waitFor(SELECTNOVOSIBIRSK).click();
        logger.info(driver.getTitle());
    }

    @Test
    public void selectComputer() {
        try {
            waitFor(COMPUTERCHAPTER).click();
            logger.info("selectChapter successful");

        } catch (Exception e) {
            logger.error("Chapter not found");
        }
    }

    @Test
    public void selectTablet() {
        try {
            waitFor(TABLETCHAPTER).click();
            logger.info("selectChapter successful");

        } catch (Exception e) {
            logger.error("Chapter not found");
        }
    }

    @Test
    public void setManufacturers() {
        try {
            WebElement samsung = driver.findElement(By.cssSelector("[name=\"Производитель Samsung\"]"));
            WebElement samsungParent = samsung.findElement(By.xpath(".."));
            samsungParent.click();
            driver.findElement(By.cssSelector("[data-autotest-id=\"dprice\"]")).click();

            logger.info("setManufacturers successful");

        } catch (Exception e) {
            logger.error("Manufacturers not found");
        }
    }

    @Test
    public void priceFilter() {
        try {
            waitFor(PRICEFILTER).click();
            driver.findElement(By.cssSelector("[data-autotest-id=\"dprice\"]")).click();
            wait(10);
        } catch (Exception e) {
            logger.error("priceFilter failed");
        }
    }

    @Test
    public void selectFiveElements() {
        List<WebElement> fifthElements = new ArrayList<WebElement>();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCTNAME));
            fifthElements = driver.findElements(PRODUCTNAME);

            logger.info("1-я позиция: " + fifthElements.get(0).getText());
            logger.info("2-я позиция: " + fifthElements.get(1).getText());
            logger.info("3-я позиция: " + fifthElements.get(2).getText());
            logger.info("4-я позиция: " + fifthElements.get(3).getText());
            logger.info("5-я позиция: " + fifthElements.get(4).getText());

            secondElement = fifthElements.get(1).getText();

            WebElement secondElementParent = fifthElements.get(1);
            WebElement firstUpLevel = secondElementParent.findElement(By.xpath(".."));
            WebElement secondUpLevel = firstUpLevel.findElement(By.xpath(".."));
            WebElement thirdUpLevel = secondUpLevel.findElement(By.xpath(".."));
            WebElement fourthUpLevel = thirdUpLevel.findElement(By.xpath(".."));

            price = fourthUpLevel.findElement(By.cssSelector("[data-zone-name=\"price\"]")).getText();

        } catch (Exception e) {
            logger.error("select first five elements failed");
        }
    }

    @Test
    public void assertFirstElement() {

        try {
            waitFor(SEARCHFIELD).sendKeys(secondElement);
            waitFor(SUBMITBUTTON).click();
            String newFirstElementOnPage = waitFor(PRODUCTNAME).getText();
            logger.info("newFirstElementOnPage = " + newFirstElementOnPage);
            Assert.assertEquals(secondElement, newFirstElementOnPage, "Elements are not equal");

        } catch (Exception e) {
            logger.error("assertFirstElement failed");
        }
    }

    private WebElement waitFor(By selector) {
        return wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(selector)));
    }
}