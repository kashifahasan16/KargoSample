package GoogleSearch;

import Common.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.testng.annotations.Test;
import utility.DataRead;

import java.io.IOException;

/**
 * Created by Kashifa on 9/26/16.
 */
public class TC2 extends Base {
    @Test
    public void keyWordSearch() throws IOException, InterruptedException {
        navigateTo("https://www.google.com");
        DataRead dataRead = new DataRead("Testsuite/data/externalKeywords.xlsx");
        int x = dataRead.getRowCount("Sheet1");
        for (int i=2; i<=x; i++) {
            driver.findElement(By.name("q")).clear();
            driver.findElement(By.name("q")).sendKeys(dataRead.getCellData("Sheet1", "Keywords", i), Keys.ENTER);
            sleepFor(2);
        }
    }
}
