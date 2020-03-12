import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Nokia 7 plus");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("app", "/Users/melnik/Documents/GitHub/MobileAutoHomeTask04/apks/wiki.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void saveTwoArticlesTest() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                10);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[@class='android.widget.TextView']"),
                "Cannot find 'Search' button",
                10);
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot send text in 'Search' field",
                10);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java']"),
                "Cannot find article with text 'Java'",
                15);
        waitForElementAndClick(
                By.id("org.wikipedia:id/article_menu_bookmark"),
                "Cannot find 'Save to reading list' button",
                10);
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Onboarding' button",
                10);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Saved']"),
                "Cannot find list for saved articles",
                10);
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find 'Navigate up' button",
                10);
        waitForElementAndClick(
                By.id("org.wikipedia:id/dialog_checkbox"),
                "Cannot find checkbox",
                10);
        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='NO THANKS']"),
                "Cannot find 'No thanks' button",
                10);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[@class='android.widget.TextView']"),
                "Cannot find 'Search' button",
                10);
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Appium",
                "Cannot send text in search field",
                10);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find article with text 'Appium'",
                15);
        waitForElementAndClick(
                By.id("org.wikipedia:id/article_menu_bookmark"),
                "Cannot find 'Save to reading list' button",
                10);
        waitForElementAndClick(
                By.id("org.wikipedia:id/article_menu_bookmark"),
                "Cannot find 'Save to reading list' button",
                10);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Saved']"),
                "Cannot find list for saved articles",
                10);
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_toolbar_button_show_overflow_menu"),
                "Cannot find 'Menu' button",
                10);
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_action_overflow_reading_lists"),
                "Cannot find 'My lists' button",
                10);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Saved']"),
                "Cannot find list for saved articles",
                10);
        int countOfArticles = waitAndReturnListOfElements(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find list of articles",
                10).size();
        swipeElementToLeft(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java']"),
                "Cannot swipe element to left");
        int countOfArticlesAfterSwipe = waitAndReturnListOfElements(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find list of articles",
                10).size();
        Assert.assertEquals("Error in count of articles", countOfArticles - 1, countOfArticlesAfterSwipe);
        String titleOfArticleInSavedList = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/page_list_item_title"),
                "text",
                "Cannot get attribute text",
                10);
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot click of article",
                10);
        String titleOfArticle = waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='content']//android.view.View[@index='0']"),
                "text",
                "Cannot get attribute text",
                20);
        Assert.assertEquals("Error in title of article", titleOfArticleInSavedList, titleOfArticle);
    }

    private void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(
                by,
                errorMessage,
                10);
        int leftX = (int) (element.getLocation().getX() * 0.9);
        int rightX = (int) (leftX + element.getSize().getWidth() * 0.9);
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(rightX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform();
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage) {
        return waitForElementsPresent(by, errorMessage, 5);
    }

    private List<WebElement> waitAndReturnListOfElements(By by, String errorMessage, long timeoutInSeconds) {
        List<WebElement> listOfElements = waitForElementsPresent(by, errorMessage, timeoutInSeconds);
        return listOfElements;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

}