import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class warmUp2 {






    @Test

    public void testingDice() throws InterruptedException {


        WebDriver driver= new ChromeDriver();

        driver.get("https://www.dice.com/");

        Thread.sleep(500);

        driver.findElement(By.id("typeaheadInput")).sendKeys("SDET", Keys.ENTER);

        Thread.sleep(500);

        List<WebElement> elements = driver.findElements(By.linkText("SDET"));

        Thread.sleep(500);

        for (WebElement each:elements) {
            Assert.assertEquals(each.getText(), "SDET");
        }


    }
}
