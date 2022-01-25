package com.openpayd.steps;

import com.openpayd.pages.Locators;
import com.openpayd.utilities.BrowserUtils;
import com.openpayd.utilities.ConfigurationReader;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;

import java.util.*;

import static org.junit.Assert.*;

public class ExecuteStep extends BrowserUtils {

    Locators locators;
    String nonDisCountItem;

    @Step("user navigates to the expected page")
    public void userNavigatesToExpectedPage() {
        getDriver().get(ConfigurationReader.getProperty("base.url"));
    }

    @Step("user ensures that expected title is displayed")
    public void userEnsuresThatExpectedTitleDisplayed(String title) {
        assertEquals(ConfigurationReader.getProperty("amazon.title"), title);
    }

    @Step("user searches an item")
    public void userSearchesAnItem(String item) {
        locators.getMainSearch().waitUntilEnabled();
        locators.getMainSearch().typeAndEnter(item);

    }

    @Step("user selects non-discounted items")
    public void userSelectsNonDiscountedItems() {
        for (int i = 0; i < locators.getPrices().size(); i++) {
            if (!locators.getPrices().get(i).getAttribute("outerHTML").contains("class=\"a-price a-text-price\"")) {
                nonDisCountItem = locators.getItemNames().get(i).getText();
                clickOn(locators.getItem().get(i));
                clickOn(locators.getAddToCart());
                locators.getAddedToCartText().waitUntilVisible();
                break;
            }
        }
    }

    @Step("user navigates to cart page")
    public void userNavigatesToCartPage() {
        clickOn(locators.getCartBox());
    }

    @Step("user ensures that selected item is in the cart page")
    public void userEnsuresSelectedItemIsInTheCartPage() {
        assertEquals(nonDisCountItem, locators.getCartItem().getText());
    }

}
