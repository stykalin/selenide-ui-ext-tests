package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.webdriver.ChromeDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Simple Selenide Test with PageObjects
 */
class WithExtensionTest {

    @Test
    void openWithChromeExtensionBySelenide() {
        String extPath = this.getClass().getClassLoader().getResource("chrome_cryptopro_ext.crx").getFile();
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File(extPath));

        Configuration.browser = CustomChromeDriverFactory.class.getName();
        Configuration.browserCapabilities = options;

        open("https://www.cryptopro.ru/sites/default/files/products/cades/demopage/main.html");
        $(byText("ЭЦП в браузере – попробуйте прямо сейчас!")).should(visible);

    }

    @Test
    void openWithChromeExtensionByChromeDriver() {
        String extPath = this.getClass().getClassLoader().getResource("chrome_cryptopro_ext.crx").getFile();
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File(extPath));

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);

        open("https://www.cryptopro.ru/sites/default/files/products/cades/demopage/main.html");
        $(byText("ЭЦП в браузере – попробуйте прямо сейчас!")).should(visible);
        WebDriverRunner.closeWebDriver();
    }

    @Test
    void openWithFirefoxExtensionBySelenide() {
        FirefoxOptions options = new FirefoxOptions();
        String extPath = this.getClass().getClassLoader().getResource("ff_cryptopro_ext.xpi").getFile();
        FirefoxProfile profile = new FirefoxProfile();
        profile.addExtension(new File(extPath));
        options.setProfile(profile);

        Configuration.browser = "firefox";
        Configuration.browserCapabilities = options;

        open("https://www.cryptopro.ru/sites/default/files/products/cades/demopage/main.html");
        $(byText("ЭЦП в браузере – попробуйте прямо сейчас!")).should(visible);
    }
}

class CustomChromeDriverFactory extends ChromeDriverFactory {
    @Override
    protected String[] excludeSwitches() {
        return new String[]{};
    }
}
