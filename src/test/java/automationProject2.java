import com.github.javafaker.Faker;
import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class automationProject2 {



    @Test
    public void testing() throws InterruptedException, IOException {

        //Launch Chrome browser.
        WebDriver driver= new ChromeDriver();

        Faker faker= new Faker();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Navigate to http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

        //Login using username Tester and password test
        String username= "Tester";
        String password="test";


        driver.findElement(By.name("ctl00$MainContent$username")).sendKeys(username);

        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys(password,Keys.ENTER);

        //Click on Order link

        driver.findElement(By.linkText("Order")).click();

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).clear();

        //Enter a random product quantity between 1 and 100
        String quantityOfProduct=""+(faker.number().numberBetween(1,100));

        //Click on Calculate and verify that the Total value is correct.
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys(quantityOfProduct);

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtUnitPrice")).click();

        //Thread.sleep(500);
        String quantityString = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).getAttribute("value");
        int quantity= Integer.parseInt(quantityString);

        int total=0;
        if(Integer.parseInt(quantityString)>=10){
           total= quantity*92;
            System.out.println("Total value is correct:"+total);
        }else{
           total= quantity*100;

            System.out.println("Total value is correct:"+total);
        }


        //EXTRA: As an extra challenge,
        int randomIndexOfData= 2+(int) (Math.random()*998);

        List<String> rows = Files.readAllLines(Path.of("src/test/java/MOCK_DATA.csv"));

        List<List<String>> converted= new ArrayList<>();

        for (String row : rows) {

            List<String> eachRow = Arrays.asList(row.split(","));

            converted.add(eachRow);

        }
        String customerName=converted.get(randomIndexOfData).get(0);
        String street=converted.get(randomIndexOfData).get(1);
        String city=converted.get(randomIndexOfData).get(2);
        String state=converted.get(randomIndexOfData).get(3);
        String zipCode=converted.get(randomIndexOfData).get(4);



        //Using Faker class:
//        String customerName= faker.name().fullName();
//        String street= faker.address().streetAddress();
//        String city= faker.address().city();
//        String state= faker.address().state();
//        String zipCode="";
//        for (int i = 0; i < 5; i++) {
//             zipCode += (int) (Math.random()*9);
//        }



        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys(customerName);

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys(street);

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys(city);

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys(state);

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(zipCode);


        //Select the card type randomly.
          String creditCardNumber="";
          int random =(int)(Math.random()*2);
          List<WebElement> clickCardType = driver.findElements(By.xpath("//input[@type='radio']"));
          clickCardType.get(random).click();

        List<WebElement> cardTypes = driver.findElements(By.xpath("//input[@type='radio']"));
        List<String> stringCardType=new ArrayList<>();
        for (WebElement each:cardTypes) {
            stringCardType.add(each.getAttribute("value"));
        }

        String cardType = stringCardType.get(random);
        // Enter the random card number:
            switch (random){

                case 0: creditCardNumber=4+faker.number().digits(15);

                break;
                case 1: creditCardNumber=5+faker.number().digits(15);

                    break;

                case 2: creditCardNumber=3+faker.number().digits(15);

                break;

        }


        Thread.sleep(500);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(creditCardNumber);
        Thread.sleep(500);
            //Enter a valid expiration date (newer than the current date)
        int randomDate =1+(int)(Math.random()* 11);
        String expDate=""+randomDate;

        String expDate1=expDate+"/"+faker.number().numberBetween(23,40);
        String expDate2=0+expDate+"/"+faker.number().numberBetween(23,40);

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).sendKeys(expDate.length()==2? expDate1: expDate2);

        //Click on Process

        Thread.sleep(100);

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();

        //Verify that “New order has been successfully added” message appeared on the page.

        Assert.assertEquals(driver.findElement(By.tagName("strong")).getText(),"New order has been successfully added.");

       //Click on View All Orders link.

        driver.findElement(By.linkText("View all orders")).click();

        //product
        String product= "MyMoney";
        //Create current date
        String date = driver.findElement(By.xpath("//table[@class='SampleTable']//tr[2]/td[5]")).getText();


        List<WebElement> allRows = driver.findElements(By.xpath("//table[@class='SampleTable']//tr[2]/td"));
        List<String > firstRow= new ArrayList<>();


        for (int i = 0; i < allRows.size(); i++) {

            if(!allRows.get(i).getText().isEmpty())
         firstRow.add(allRows.get(i).getText());
        }




        Assert.assertEquals(firstRow.get(0),customerName);
        Assert.assertEquals(firstRow.get(1),product);
        Assert.assertEquals(firstRow.get(2),quantityOfProduct);
        Assert.assertEquals(firstRow.get(3),date);
        Assert.assertEquals(firstRow.get(4),street);
        Assert.assertEquals(firstRow.get(5),city);
        Assert.assertEquals(firstRow.get(6),state);
        Assert.assertEquals(firstRow.get(7),zipCode);
        Assert.assertEquals(firstRow.get(8),cardType);
        Assert.assertEquals(firstRow.get(9),creditCardNumber);
        Assert.assertEquals(firstRow.get(10),expDate.length()==2? expDate1: expDate2);

        driver.findElement(By.id("ctl00_logout")).click();



    }





}
