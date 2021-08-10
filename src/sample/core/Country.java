package sample.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Country {
    String london = "London ";
    String birmingham = "Birmingham ";


    private static Country single_instance = null;

    private Country() { }

    public static Country getInstance()
    {
        if (single_instance == null)
            single_instance = new Country();

        return single_instance;
    }
}
