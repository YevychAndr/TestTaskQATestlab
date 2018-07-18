import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SitePage {

    private ChromeDriver driver;

    static Logger log = Logger.getLogger(SitePage.class.getName());

    public SitePage(ChromeDriver driver){
        this.driver = driver;
    }

    public void checkCurrency() {
        char cur = driver.findElement(By.xpath("//div[@id='_desktop_currency_selector']/div/span[2]")).getText().charAt(4);
        log.info("Перевіряємо, чи ціна в секції \"Популярні товари\" вказана у відповідності з встановленою валютою в шапці сайту.");
        List<WebElement> elements = driver.findElements(By.className("price"));
        for (int i = 0; i < elements.size(); i++) {
            String price = elements.get(i).getText();
            int last = price.length() - 1;
            char curPrice = price.charAt(last);
            if (cur != curPrice){
                System.out.println("Валюти товару " + (i + 1) + " не співпадають!");
                break;
            }
            if (i == elements.size() - 1)
                System.out.println("Валюта товарів співпадає з встановленою валютою в шапці.");
        }
    }

    public void openListCurrency() throws InterruptedException {
        driver.findElement(By.xpath("//div[@id='_desktop_currency_selector']/div/a/i")).click();
        log.info("Відкриваємо випадаючий список валют.");
        Thread.sleep(2000);

    }

    public  void  chooseCurrencyUSD() throws InterruptedException {
        driver.findElement(By.xpath("//a[contains(text(),'USD $')]")).click();
        log.info("Встановлюємо валюту USD.");


    }

    public void search (String text) {
        driver.findElement(By.name("s")).sendKeys(text);
        driver.findElement(By.cssSelector("i.material-icons.search")).click();
        log.info("Виконуємо пошук в каталозі.");

    }


    public void checkSerchPage() {
        String numGoods = driver.findElement(By.xpath("//p[contains(text(),'Товаров: ')]")).getAttribute("textContent");
        log.info("Перевіряємо, чи сторінка \"Результати пошуку\"  містить напис \"Товаров: Х\".");
        if (numGoods != null) {
            System.out.println("Сторінка містить напис: \"Товаров: Х\".");
        } else {
            System.out.println("Сторінка не містить напис: \"Товаров: Х\"!");
        }
        int numGoodsOnPage = 0;
        List<WebElement> GoodsOnPage = driver.findElements(By.className("price"));
        log.info("Перевіряємо, чи в написі \"Товаров: Х\" Х відповідає дійсно знайденим товарам.");
        for (int i = 0; i < GoodsOnPage.size(); i++) {
            numGoodsOnPage++;
        }
        int realTovInSt = Integer.parseInt(numGoods.substring(9, numGoods.length() - 1));
        if (numGoodsOnPage == realTovInSt) {
            System.out.println("В написі \"Товаров: Х\"  Х відповідає дійсно найденій кількості товарів.");
        }
        else {
            System.out.println("В написі \"Товаров: Х\"  Х не відповідає дійсно найденій кількості товарів!");
        }
    }

    public void checkCurrencyUSD() {
        List<WebElement> goodsOnPage2 = driver.findElements(By.cssSelector("span.price"));
        log.info("Перевіряємо, чи ціна показаних товарів відображається в доларах.");
        for (int i = 0; i < goodsOnPage2.size(); i++) {
            String price2 = goodsOnPage2.get(i).getText();
            int last = price2.length() - 1;
            char curPrice = price2.charAt(last);
            if (curPrice != '$') {
                System.out.println("Валютa товару " + (i + 1) + " не відображається в доларах!");
                break;
            }
            if (i == goodsOnPage2.size() - 1)
                System.out.println("Ціна всіх показаних результатів вказана в доларах.");
        }
    }

    public void openListSort() throws InterruptedException {
        driver.findElement(By.cssSelector("i.material-icons.pull-xs-right")).click();
        log.info("Відкриваємо випадаючий список з варіантами сортування товару.");
        Thread.sleep(2000);
    }


    public void chooseSortUpDown() throws InterruptedException {
        driver.findElement(By.xpath("//a[contains(text(),'Цене: от высокой к низкой')]")).click();
        log.info("Вибираємо сортування \"Від високої до низької\".");
        Thread.sleep(2000);
    }

    public void checkCorrectSort() {
        List<WebElement> elements2 = driver.findElements(By.xpath("//article[*]/div[1]/div[1]/div[1]/span[1]"));
        log.info("Перевіряємо, чи товари відсортовані правильно по ціні, з урахуванням того що у товарів зі знижкою, ціна при сортуванні використовується без знижки");
        List<String> elements3 = new ArrayList<>();
        List<Float> elemens4 = new ArrayList<>();
        for (int i = 0; i < elements2.size(); i++) {
            elements3.add(i, elements2.get(i).getText().substring(0, 4).replace(",", "."));
        }
        for (int i = 0; i < elements3.size(); i++) {
            elemens4.add(i, Float.parseFloat(elements3.get(i)));
        }
        for (int i = 0; i < elemens4.size() - 1; i++) {
            if (elemens4.get(i) < elemens4.get(i + 1)) {
                System.out.println("Товари не відсортовані по ціні!");
                break;
            }
            if (i == elemens4.size() - 2)
                System.out.println("Товари відсортовані по ціні.");
        }
    }

    public void checkCorrectDiscount() {
        List<WebElement> elements5 = driver.findElements(By.xpath("//div[span[@class='discount-percentage']]"));
        log.info("Перевіряємо, що в товарів зі знижкою, ціна до і після знижки співпадає з вказаним розміром знижки.");
        List<String> elements6 = new ArrayList<>();
        List<Float> element7 = new ArrayList<>();
        for (int i = 0; i < elements5.size(); i++) {
            elements6.add(i, elements5.get(i).getText().replace("\n", " ").replace(",", ".").replace(" $", "").replace("-", "").replace("%", ""));
        }
        for (int i = elements6.size()-1; i >=0; i--) {
            String strArr[] = elements6.get(i).split(" ");
            for (int j = strArr.length-1; j >=0; j--) {
                element7.add(0, Float.parseFloat(strArr[j]));

            }
        }
        for (int i = 1; i < element7.size(); i=i+3) {
            if (element7.get(i) != Math.round(100-(element7.get(i+1)*100/element7.get(i-1)))) {
                System.out.println("Ціна до і після знижки не співпадає з вказаним розміром знижки!");
                break;
            }
            if (i == element7.size() - 2)
                System.out.println("Ціна до і після знижки співпадає з вказаним розміром знижки.");

        }

    }

}
