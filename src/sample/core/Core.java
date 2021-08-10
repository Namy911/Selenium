package sample.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static sample.core.Route.routeBirminghamToLondon;

public class Core {
    private final String LINK_LOGIN = "//*[@id=\"navbarLinkLogin\"]/a";

    private final String INPUT_LOGIN_XPATH = "//*[@id=\"UserUsername\"]";
    private final String INPUT_PASS_XPATH = "//*[@id=\"UserPassword\"]";
    private final String BTN_LOGIN_XPATH = "//*[@id=\"buttonSubmit\"]";

    private final String LINK_SEARCH = "//*[@id=\"menu\"]/ul[1]/li[6]/a";
    private final String INPUT_SEARCH_COLLECTION = "//*[@id=\"SearchRouteFromAddress\"]";
    private final String INPUT_SEARCH_DELIVERY = "//*[@id=\"SearchRouteToAddress\"]";
    private final String BUTTON_SEARCH = "//*[@id=\"search-tabs-content-box\"]/div/div/div[2]/div/button[1]";
    private final String BUTTON_ROUTE_SEARCH = "//*[@id=\"SearchRouteTab\"]";
    private final String DROP_DOWN_PER_PAGE = "//*[@id=\"formLimit\"]";
    private final String PER_PAGE = "50";
    private final String LINK_DISTANCE_SORT = "//*[@id=\"formSortDistance\"]";

    private final String TABLE_LIST_ITEMS = "//*[@id=\"SearchTableBody\"]";
    private final String TABLE_BODY = "//*[@id=\"Bids\"]/tbody";
    private final String TABLE = "//*[@id=\"Bids\"]";
    private final String TABLE_ROW_CLASS = "bid-table-tr";
    private final String CELL_BOX = "search-cell-box-content";
    private final String CLASS_QUOTE_LOWEST = "lowest_quote_highlight";

    List<String> list = new ArrayList<>();

    public void openShiply(){
        Thread thread = new Thread(() -> {
            WebDriver driver = AppWebDriver.getInstance();
            driver.manage().window().maximize();
            driver.get("https://www.shiply.com/");

            login(driver);
            setupRoute(driver);
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void openSecond(){
        Thread thread = new Thread(() -> {
            WebDriver driver = AppWebDriver.getInstance2();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get("https://www.shiply.com/");

            login2(driver);
            setupRoute(driver);
        });
        thread.setDaemon(true);
        thread.start();
    }

    void login(WebDriver driver) {
        Manager manager = Manager.getSecond();

        WebElement linkLogin = driver.findElement(By.xpath(LINK_LOGIN));
        linkLogin.click();

        WebElement inputLogin = driver.findElement(By.xpath(INPUT_LOGIN_XPATH));
        WebElement inputPass = driver.findElement(By.xpath(INPUT_PASS_XPATH));
        WebElement btnLogin = driver.findElement(By.xpath(BTN_LOGIN_XPATH));

        inputLogin.sendKeys(manager.email);
        inputPass.sendKeys(manager.password);
        btnLogin.click();
    }

    void login2(WebDriver driver) {
        Manager manager = Manager.getFirst();

        WebElement linkLogin = driver.findElement(By.xpath(LINK_LOGIN));
        linkLogin.click();

        WebElement inputLogin = driver.findElement(By.xpath(INPUT_LOGIN_XPATH));
        WebElement inputPass = driver.findElement(By.xpath(INPUT_PASS_XPATH));
        WebElement btnLogin = driver.findElement(By.xpath(BTN_LOGIN_XPATH));

        inputLogin.sendKeys(manager.email);
        inputPass.sendKeys(manager.password);
        btnLogin.click();
    }


    void setupRoute(WebDriver driver){
        WebElement linkSearch = driver.findElement(By.xpath(LINK_SEARCH));
        linkSearch.click();

        WebElement inputFrom = driver.findElement(By.xpath(INPUT_SEARCH_COLLECTION));
        WebElement inputTo = driver.findElement(By.xpath(INPUT_SEARCH_DELIVERY));
        WebElement btnSearch = driver.findElement(By.xpath(BUTTON_SEARCH));
        routeSearch(driver);
        Select perPageLimit = new Select(driver.findElement(By.xpath(DROP_DOWN_PER_PAGE)));

        Route route = routeBirminghamToLondon();
        inputFrom.clear();
        inputTo.clear();
        inputFrom.sendKeys(route.collection);
        inputTo.sendKeys(route.delivery);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btnSearch.click();
        perPageLimit.selectByValue(PER_PAGE);
//        setMiles(driver);
        createListItems(driver);
        openNewTab(driver);
        findMinPrice(driver);
        setBid(driver);
    }

    void routeSearch(WebDriver driver){
        WebElement routeSearchLink = driver.findElement(By.xpath(BUTTON_ROUTE_SEARCH));
        routeSearchLink.click();
    }

    void setMiles(WebDriver driver){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement sort = driver.findElement(By.xpath(LINK_DISTANCE_SORT));
        sort.click();
    }

    void createListItems(WebDriver driver){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement table = driver.findElement(By.xpath(TABLE_LIST_ITEMS));
        List<WebElement> allRows = table.findElements(By.tagName("tr"));

        for (WebElement row : allRows) {
            WebElement cell = row.findElement(By.className(CELL_BOX));
            WebElement link = cell.findElement(By.tagName("a"));
            list.add(link.getAttribute("href"));
        }
    }

    void openNewTab(WebDriver driver){
        for (String link: list) {
            ((JavascriptExecutor) driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
//            driver.get(list.get(1));
            driver.get(link);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((JavascriptExecutor) driver).executeScript("window.close()");
            driver.switchTo().window(tabs.get(0));
        }
    }

    void setBid(WebDriver driver){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement table = driver.findElement(By.xpath(TABLE_BODY));
        
        List<WebElement> rows = table.findElements(By.className(TABLE_ROW_CLASS));

        for (WebElement row: rows){
            try {
                WebElement element = row.findElement(By.tagName("a"));
                System.out.println(element.getText());
            }catch (Exception e){ }
        }
    }

    void findMinPrice(WebDriver driver){
        WebElement table = driver.findElement(By.xpath(TABLE));
        WebElement rowPrice = table.findElement(By.className(CLASS_QUOTE_LOWEST));

        String price = rowPrice.findElement(By.tagName("span")).getText().substring(1);
        System.out.println("lowest quote " + price);
    }
//
//    public void quit() {
//        try{
//            AppWebDriver.getInstance().close();
//            AppWebDriver.getInstance().quit();
//            AppWebDriver.getInstance2().close();
//            AppWebDriver.getInstance2().quit();
//        }catch (Exception e){
//
//        }
//    }
}
