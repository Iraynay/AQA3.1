import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();

    }

    ChromeOptions options = new ChromeOptions();

    @BeforeEach

    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldReturnOkMessage() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий Иванов");
        driver.findElement(By.cssSelector("[name='phone']")).sendKeys("+70001234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldShowPromtInCaseOfWrongName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("ndjkl");
        driver.findElement(By.cssSelector("[name='phone']")).sendKeys("+70001234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldShowPromtInCaseOfAbsenceName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[name='phone']")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldShowPromtInCaseOfAbsencePhone() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий Иванов");
        driver.findElement(By.cssSelector("[name='phone']")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldShowPromtInCaseOfWrongPhone() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Василий Иванов");
        driver.findElement(By.cssSelector("[name='phone']")).sendKeys("123");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
}
