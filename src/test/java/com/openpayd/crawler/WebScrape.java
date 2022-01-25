package com.openpayd.crawler;

import com.openpayd.utilities.BrowserUtils;
import com.openpayd.utilities.ConfigurationReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WebScrape extends BrowserUtils {

    @Test
    public void crawler() {
        List<String> urls = new ArrayList<>();

        getDriver().get(ConfigurationReader.getProperty("base.url"));
        clickOn($("//a[@id='nav-hamburger-menu']"));
        clickOn($("//a[@class='hmenu-item hmenu-compressed-btn']"));
        for (int i = 5; i < 27; i++) {
            clickWithJS(($("//a[@data-menu-id='" + i + "']")));
            List<WebElement> webElement = getDriver().findElements(By.xpath("//ul[@data-menu-id='" + i + "']//a[contains(@href,'/s?')]"));
            for (int j = 0; j < webElement.size(); j++) {
                urls.add(webElement.get(j).getAttribute("href"));
            }
            clickOn($("(//a[@class='hmenu-item hmenu-back-button'])[" + (i - 1) + "]"));
        }

        try {
            String fileName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date()) + "_results.txt";
            FileWriter fileWriter = new FileWriter(fileName);
            for (String url : urls) {
                System.out.println("urls-> " + url);
                Document document = Jsoup.connect(url).userAgent("Chrome/97.0.4692.99").timeout(10000).get();
                String title = document.title();
                System.out.println("title = " + title);
               String data = "";
                if (title.isBlank()) {
                    data = "Link -> " + url + "\n" + "status -> " + "Dead Link";

                } else {
                    data = "Link -> " + url + "\n" + "title -> " + title + "\n" + "status -> " + "OK";
                }
                fileWriter.write(data);
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("No url");
        }
    }
}
