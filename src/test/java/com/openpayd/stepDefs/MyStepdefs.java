package com.openpayd.stepDefs;

import com.openpayd.steps.ExecuteStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class MyStepdefs {

    @Steps
    ExecuteStep executeStep;

    @Given("user is on the main page")
    public void userIsOnTheMainPage() {
        executeStep.userNavigatesToExpectedPage();
    }

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String title) {
        executeStep.userEnsuresThatExpectedTitleDisplayed(title);
    }

    @When("user searches {string}")
    public void userSearches(String searchItem) {
        executeStep.userSearchesAnItem(searchItem);
    }

    @And("user selects non-discount items")
    public void userSelectsNonDiscountItems() {
        executeStep.userSelectsNonDiscountedItems();
    }

    @And("user navigates to cart box page")
    public void userNavigatesToCartBoxPage() {
        executeStep.userNavigatesToCartPage();
    }

    @Then("selected non-discount item should be in the cart page")
    public void selectedNonDiscountItemShouldBeInTheCartPage() {
        executeStep.userEnsuresSelectedItemIsInTheCartPage();
    }
}
