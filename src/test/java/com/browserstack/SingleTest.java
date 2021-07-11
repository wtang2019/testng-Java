package com.browserstack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.JavascriptExecutor;


public class SingleTest extends BrowserStackTestNGTest {

    @Test
    public void test() throws Exception {
        String username = System.getenv("BROWSERSTACK_USERNAME");
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", buildName); // CI/CD job name using BROWSERSTACK_BUILD_NAME env variable
        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), capabilities);

        driver.manage().window().maximize(); //Maximize browser window

        driver.get("https://www.browserstack.com/");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("App & Browser Testing for Teams", driver.findElement(By.tagName("h1")).getText()); //First assertion if text is shown as expected in assertEquals
        WebElement contactUs = driver.findElement(By.xpath("//*[@id=\"post-26\"]/div[1]/div[1]/div/div/article/div/div/div/div[1]/div/div/div/div/span[2]/a"));
        boolean isVisible = contactUs.isDisplayed(); //Second asseertion to check if Contact Us Button is visible
        contactUs.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.findElement(By.id("support-name")).sendKeys("Wilmer Tang");  //Third assertion if form can be filled in line 38-47
        driver.findElement(By.id("support-email")).sendKeys("wtang88@gmx.de");

        WebElement type = driver.findElement(By.id("support_query_type_chosen"));
        js.executeScript("window.scrollBy(0,350)", "");
        type.click();

        driver.findElement(By.xpath("//*[@id=\"support_query_type_chosen\"]/div/ul/li[1]")).click();
        driver.findElement(By.id("support-message")).sendKeys("It was an enjoyable exercise. Thanks!");
        driver.findElement(By.id("sales-company")).sendKeys("MyCompany");

    }
}
