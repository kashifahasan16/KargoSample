package GoogleSearch;

import Common.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Kashifa on 9/26/16.
 */
public class TC1 extends Base {

    private String URL="https://www.google.com";
    private String KargoLocator="a[href='http://www.kargo.com/']";
    private String mainMenu=".nav-button__bar";
    private String aboutUs= "a[href='http://www.kargo.com/about/']";

    @Test
    public void SearchKargo() throws InterruptedException, IOException {
        navigateTo(URL);
        driver.findElement(By.name("q")).sendKeys("Kargo", Keys.ENTER);
        sleepFor(1);
        takeScreenshot("KargoSearch");
        driver.findElement(By.cssSelector(KargoLocator)).click();

        sleepFor(2);
        takeScreenshot("clickOnKargo");
        waitUntilClickAble(By.cssSelector(mainMenu));
        driver.findElement(By.cssSelector(mainMenu)).click();

        waitUntilClickAble(By.cssSelector(aboutUs));
        driver.findElement(By.cssSelector(aboutUs)).click();
        takeScreenshot("ClickOnAboutUS");
        sleepFor(2);   //waits for your view

    }
}
