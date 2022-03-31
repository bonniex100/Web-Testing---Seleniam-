import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.github.javafaker.Faker;

import static org.apache.commons.lang3.StringUtils.right;


public class Tests {
    protected static Faker faker = new Faker();
    protected String username = faker.name().username();
    protected String password = faker.internet().password();

    public void signUp() {
        String projectPath = System.getProperty("user.dir");

        System.out.println("projectPath : " + projectPath);

        System.setProperty("webdriver.gecko.driver", projectPath + "\\\\Drivers\\\\geckodriver\\\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();

        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        driver.manage().window().maximize();

        driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/div/p[2]/a")).click();

        driver.findElement(By.id("customer.firstName")).sendKeys(faker.name().firstName());

        driver.findElement(By.id("customer.lastName")).sendKeys(faker.name().lastName());

        driver.findElement(By.id("customer.address.street")).sendKeys(faker.address().streetAddress());

        driver.findElement(By.id("customer.address.city")).sendKeys(faker.address().cityName());

        driver.findElement(By.id("customer.address.state")).sendKeys(faker.address().state());

        driver.findElement(By.id("customer.address.zipCode")).sendKeys(faker.address().zipCode());

        driver.findElement(By.id("customer.phoneNumber")).sendKeys("hhhhh");

        driver.findElement(By.id("customer.ssn")).sendKeys(faker.idNumber().ssnValid());

        driver.findElement(By.id("customer.username")).sendKeys(username);

        driver.findElement(By.id("customer.password")).sendKeys(password);

        driver.findElement(By.id("repeatedPassword")).sendKeys(password);

        driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/form/table/tbody/tr[13]/td[2]/input")).click();


        //log out to allow login testing to be effected
        driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/ul/li[8]/a")).click();

        driver.findElement(By.name("username")).sendKeys(username);

        driver.findElement(By.name("password")).sendKeys(password);

//        driver.findElement(By.cssSelector(".button:nth-child(1)")).click();

        driver.findElement(By.xpath("//*[@id=\"loginPanel\"]/form/div[3]/input")).click();

        //ACCOUNT OPENING TEST CASE //


        ArrayList<Integer> accountNumbers = new ArrayList<Integer>();

        for (int i = 0; i < 3; i++) {

            driver.findElement(By.linkText("Open New Account")).click();
            WebElement dropdown = driver.findElement(By.id("type"));
            dropdown.click();

            List<WebElement> itemsInDropdown = driver.findElements(By.id("type"));

            int size = itemsInDropdown.size();

            int randomNumber = ThreadLocalRandom.current().nextInt(0, size);

            itemsInDropdown.get(randomNumber).click();

            driver.findElement(By.id("fromAccountId")).click();

            driver.findElement(By.cssSelector("input.button")).click();
//            String value = driver.findElement(By.id("newAccountId")).getText();

            String accountNumber = right(driver.findElement(By.id("newAccountId")).getAttribute("href"), 5);


            accountNumbers.add(Integer.parseInt(accountNumber));


        }

        ////     APPLY loan test
        driver.findElement(By.linkText("Request Loan")).click();
        driver.findElement(By.id("amount")).sendKeys(faker.commerce().price());
        driver.findElement(By.id("downPayment")).sendKeys(faker.commerce().price());
        WebElement dropdown = driver.findElement(By.id("fromAccountId"));
        dropdown.findElement(By.xpath("//option[. = '" + accountNumbers.get(2).toString() + "']")).click();


        driver.findElement(By.cssSelector(".button:nth-child(1)")).click();

        ///  FUNDS TRANSFER
        driver.findElement(By.linkText("Transfer Funds")).click();

        driver.findElement(By.id("fromAccountId")).click();

        WebElement dropdown1 = driver.findElement(By.id("type"));
        dropdown1.findElement(By.xpath("//option[. = '" + accountNumbers.get(2).toString() + "']")).click();


        driver.findElement(By.id("fromAccountId")).click();
        {
            WebElement dropdown2 = driver.findElement(By.id("type"));
            dropdown2.findElement(By.xpath("//option[. = '" + accountNumbers.get(1).toString() + "']")).click();
        }
        driver.findElement(By.id("amount")).sendKeys(faker.commerce().price());

        driver.findElement(By.cssSelector(".button:nth-child(1)")).click();
    }

}
