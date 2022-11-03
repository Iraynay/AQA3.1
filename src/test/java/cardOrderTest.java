import com.codeborne.selenide.Condition;
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
import io.github.bonigarcia.wdm.WebDriverManager;

public class cardOrderTest {
    private WebDriver driver;
    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\tmp\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();

    }
    ChromeOptions options = new ChromeOptions();

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldReturnOkMessage() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Василий");
        elements.get(1).sendKeys("+70001234567");
        driver.findElement(By.className("checkbox__control")).click();
        driver.findElement(By.className("button_text")).click();
        String text = driver.findElement(By.className("alert-success")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
//    void shouldReturnOkMessage () {
//       open("http://localhost:9999");
    // SelenideElement form = $("['.input']");
//    public static By cssSelector(['name=\"name\"']).setValue(("Иванов Иван"));
//    $("['[data-test-id=\"phone\"]']").setValue(("+79991234567"));
//    $("['[data-test-id=\"agreement\"]']").click();
//    $("['.button__content']").click();
//      $("").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

}
