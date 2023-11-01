package tech.romashov;

import com.codeborne.selenide.WebDriverProvider;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SelenoidWebDriverProvider implements WebDriverProvider {
    @NotNull
    @Override
    public WebDriver createDriver(@NotNull Capabilities capabilities) {
        try {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("selenoid:options", new HashMap<String, Object>() {{
                /* How to set timezone */
                put("env", new ArrayList<String>() {{
                    add("TZ=UTC");
                }});
                /* How to add "trash" button */
                put("labels", new HashMap<String, Object>() {{
                    put("manual", "true");
                }});
                /* How to enable video recording */
                put("enableVideo", true);
                put("enableVNC", true);
            }});
            capabilities = capabilities.merge(chromeOptions);
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

//    @NotNull
//    @Override
//    public WebDriver createDriver(DesiredCapabilities capabilities) {
//        try {
//            ChromeOptions chromeOptions = new ChromeOptions();
//            chromeOptions.setCapability("enableVNC", true);
//            capabilities = capabilities.merge(chromeOptions);
//            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
