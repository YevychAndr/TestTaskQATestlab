import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.concurrent.TimeUnit;



public class PestashopTests {

    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void start() {
        DOMConfigurator.configure("log4j.xml");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to("http://prestashop-automation.qatestlab.com.ua/ru/");
        wait = new WebDriverWait(driver, 10);
    }
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestSuite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println("\nTEST NAME: " + failure.getTestHeader());
            System.out.println("\nERROR: " + failure.getMessage() + "\n");
            System.out.println(failure.getTrace());
            System.exit(1);
        }
    }



    @Test
    public void test() throws InterruptedException {
        SitePage page = new SitePage((ChromeDriver) driver);
        Logger log = Logger.getLogger(PestashopTests.class.getName());
        log.info("Відкриваємо головну сторінку сайту.");
        page.checkCurrency();
        page.openListCurrency();
        page.chooseCurrencyUSD();
        page.search("dress");
        page.checkSerchPage();
        page.checkCurrencyUSD();
        page.openListSort();
        page.chooseSortUpDown();
        page.checkCorrectSort();
        page.checkCorrectDiscount();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
