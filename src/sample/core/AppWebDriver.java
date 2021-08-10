package sample.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AppWebDriver {
    private static WebDriver single_instance = null;
    private static WebDriver single_instance2 = null;

    private AppWebDriver() { }

    public static WebDriver getInstance()
    {
        if (single_instance == null)
            single_instance = new ChromeDriver();

        return single_instance;
    }

    public static WebDriver getInstance2()
    {
        if (single_instance2 == null)
            single_instance2 = new ChromeDriver();

        return single_instance2;
    }
}
