package com.openpayd.pages;

import lombok.Getter;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.*;

@Getter
public class Locators extends PageObject {

    @FindBy(id = "twotabsearchtextbox")
    private WebElementFacade mainSearch;

    @FindBy(xpath = "//div[@class='a-section a-spacing-small a-spacing-top-small']//div[@class='a-row a-size-base a-color-base']")
    private List<WebElementFacade> prices;

    @FindBy(xpath = "//*[@class='a-section a-spacing-small a-spacing-top-small']//h2[@class='a-size-mini a-spacing-none a-color-base s-line-clamp-2']")
    private List<WebElementFacade> itemNames;

    @FindBy(css = ".a-section.aok-relative.s-image-fixed-height")
    private List<WebElementFacade> item;

    @FindBy(id = "add-to-cart-button")
    private WebElementFacade addToCart;

    @FindBy(xpath = "//span[normalize-space()='Added to Cart']")
    private WebElementFacade addedToCartText;

    @FindBy(id = "nav-cart")
    private WebElementFacade cartBox;

    @FindBy(css = ".a-truncate-cut")
    private WebElementFacade cartItem;

    @FindBy(xpath = "//a[@id='nav-hamburger-menu']")
    private WebElementFacade allButton;

    @FindBy(css = "a.hmenu-item.hmenu-compressed-btn")
    private List<WebElementFacade> seeAllButton;

    @FindBy(css = "a.hmenu-item.hmenu-back-button")
    private WebElementFacade backMainMenu;
}
