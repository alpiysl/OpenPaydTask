package com.openpayd.utilities;

import lombok.NoArgsConstructor;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.Set;

@NoArgsConstructor
public class BrowserUtils extends PageObject {

    public void switchToNewWindow(String oldWindowHandle) {
        Set<String> windows = getDriver().getWindowHandles();
        for (String w : windows) {
            if (!w.equals(oldWindowHandle)) {
                getDriver().switchTo().window(w);
                break;
            }
        }
    }

    public void scrollToElement(WebElementFacade element) {
        getJavascriptExecutorFacade().executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickWithJS(WebElementFacade element) {
        getJavascriptExecutorFacade().executeScript("arguments[0].scrollIntoView(true);", element);
        getJavascriptExecutorFacade().executeScript("arguments[0].click();", element);
    }

}
