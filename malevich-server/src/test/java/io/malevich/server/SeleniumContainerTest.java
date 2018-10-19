package io.malevich.server;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

import static org.junit.Assert.assertTrue;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

import java.io.File;

public class SeleniumContainerTest {

    @Rule
    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome())
            .withRecordingMode(RECORD_ALL, new File("target"));

    @Test
    public void simplePlainSeleniumTest() {
        RemoteWebDriver driver = chrome.getWebDriver();

        driver.get("https://wikipedia.org");
        WebElement searchInput = driver.findElementByName("search");

        searchInput.sendKeys("Rick Astley");
        searchInput.submit();

        WebElement otherPage = driver.findElementByPartialLinkText("Rickrolling");
        otherPage.click();

        boolean expectedTextFound = driver.findElementsByCssSelector("p")
                .stream()
                .anyMatch(element -> element.getText().contains("meme"));

        assertTrue("The word 'meme' is found on a page about rickrolling", expectedTextFound);
    }

}
