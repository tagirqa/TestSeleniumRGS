import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class HomeTest {
    private WebDriver driver;
    WebDriverWait wait;

    @Before
    public void startUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
//        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void mainTest() {
        driver.get("https://www.rgs.ru/");

        String menuLink = "//li[@class='dropdown adv-analytics-navigation-line1-link current']//a[contains(text(), 'Меню')]";
        WebElement menuElement = driver.findElement(By.xpath(menuLink));
        menuElement.click();

        String dmsLink = "//a[contains(text(), 'ДМС')]";
        WebElement dmsElement = driver.findElement(By.xpath(dmsLink));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dmsLink))).click();


        String dmsText = "//div[@class='clearfix']//h1";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dmsText)));

        // Проверить наличие заголовка - Добровольное медицинское страхование
        Assert.assertEquals("Не могу найти заголовок 'ДМС — добровольное медицинское страхование'",
                "ДМС — добровольное медицинское страхование",
                driver.findElement(By.xpath("//h1[@class='content-document-header']")).getText());

        String btnSendFactory = "//a[contains(text(), 'Отправить заявку')]";
        WebElement btnSendFactoryElement = driver.findElement(By.xpath(btnSendFactory));
        wait.until(ExpectedConditions.elementToBeClickable(btnSendFactoryElement));
        btnSendFactoryElement.click();

        String titleFactory = "//b[contains(text(), 'Заявка на добровольное медицинское страхование')]";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(titleFactory)));

        // Проверить, что открылась страница , на которой присутствует текст - Заявка на добровольное медицинское страхование
        Assert.assertEquals("Нет текста: 'Заявка на добровольное медицинское страхование'",
                "Заявка на добровольное медицинское страхование",
                driver.findElement(By.xpath("//b[contains(text(), 'Заявка на добровольное медицинское страхование')]")).getText());

        String fieldLastName = "//input[@name='LastName']";
        String fieldFirstName = "//input[@name='FirstName']";
        String fieldMiddleName = "//input[@name='MiddleName']";
        String fieldTel = "//div[@class='form-group col-md-6 col-xs-12']//label[contains(text(), 'Телефон')]/following-sibling::input";
        String fieldMail = "//div[@class='form-group col-md-6 col-xs-12']//label[contains(text(), 'Эл. почта')]/following-sibling::input";
        String fieldDateContact = "//div[@class='form-group col-md-6 col-xs-12']//label[contains(text(), 'Предпочитаемая дата контакта')]/following-sibling::input";
        String fieldComment = "//div[@class='form-group col-md-12 col-xs-12']//textarea";
        String fieldRegionSelect = "//div[@class='form-group col-md-12 col-xs-12']//select";
        String checkBox = "//input[@class='checkbox']";
        String button = "//button[@id='button-m']";

        WebElement fieldLastNameElement = driver.findElement(By.xpath(fieldLastName));
        WebElement fieldFistNameElement = driver.findElement(By.xpath(fieldFirstName));
        WebElement fieldMiddleNameElement = driver.findElement(By.xpath(fieldMiddleName));
        WebElement fieldTelElement = driver.findElement(By.xpath(fieldTel));
        WebElement fieldMailElement = driver.findElement(By.xpath(fieldMail));
        WebElement fieldDateContactElement = driver.findElement(By.xpath(fieldDateContact));
        WebElement fieldCommentElement = driver.findElement(By.xpath(fieldComment));
        WebElement fieldRegionSelectElement = driver.findElement(By.xpath(fieldRegionSelect));
        WebElement checkBoxElement = driver.findElement(By.xpath(checkBox));

        WebElement moscowRegionSelect = driver.findElement(By.xpath("//option[@value='77']"));
        WebElement buttonElement = driver.findElement(By.xpath(button));


        fieldLastNameElement.sendKeys("Пупкин");
        fieldFistNameElement.sendKeys("Василий");
        fieldMiddleNameElement.sendKeys("Владимирович");
        fieldMailElement.sendKeys("qwertyqwerty");
        fieldCommentElement.sendKeys("Я коментарий!");
        fieldRegionSelectElement.click();
        moscowRegionSelect.click();
        checkBoxElement.click();
        fieldTelElement.click();
        fieldTelElement.sendKeys("9999999999");
        fieldDateContactElement.click();
        fieldDateContactElement.sendKeys("24052020");
        buttonElement.click();


        // Проверки на вводимые значения

        Assert.assertEquals("Неверное поле Фамилия",
                "Пупкин",
                fieldLastNameElement.getAttribute("value"));

        Assert.assertEquals("Неверное поле Имя",
                "Василий",
                fieldFistNameElement.getAttribute("value"));

        Assert.assertEquals("Неверное поле Отчество",
                "Владимирович",
                fieldMiddleNameElement.getAttribute("value"));

        Assert.assertEquals("Неверное поле Эл почта",
                "qwertyqwerty",
                fieldMailElement.getAttribute("value"));

        Assert.assertEquals("Неверное поле Коментарий",
                "Я коментарий!",
                fieldCommentElement.getAttribute("value"));

        Assert.assertEquals("Неверное поле Регион",
                "77",
                fieldRegionSelectElement.getAttribute("value"));

        Assert.assertEquals("Неверное поле Телефон",
                "+7 (999) 999-99-99",
                fieldTelElement.getAttribute("value"));

        Assert.assertEquals("Неверное поле Дата",
                "24.05.2020",
                fieldDateContactElement.getAttribute("value"));

        Assert.assertEquals("Неверное поле CheckBox",
                "on",
                checkBoxElement.getAttribute("value"));



        // Проверить, что у Поля - Эл. почта присутствует сообщение об ошибке - Введите корректный email
        Assert.assertEquals("Нет ошибки о вводе неверной почты",
                "Введите адрес электронной почты",
                driver.findElement(By.xpath("//span[contains(text(), 'Введите адрес электронной почты')]")).getText());


    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
