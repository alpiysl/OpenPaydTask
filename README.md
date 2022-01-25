#This project used for OpenPayd Qa Task

##Tasks:

`Acceptance criteria implementation`  
Using Java and Selenium/Webdriver, implement the following tests on the Chrome browser. • Steps:
* Enter amazon.com and check the homepage
* Search by word “laptop”
* Add the non-discounted products in stock on the first page of the search results to
  the cart
* Go to cart and check if the products is the right  

`get serenity report`
- mvn clean verify

`page object class`  
- BaseClass extends PageObject abstract class which comes with Serenity itself
- PageInitialize class initialize the page objects
- BaseClass runs these objects with the method which initializes with @Before annotation in test every run
- BrowserUtils class has ready methods and also extends PageInitialize class

`steps`  
* Serenity Step Libraries  
In Serenity, tests are broken down into reusable steps. An important principle behind Serenity is the idea that it is easier to maintain a test that uses several layers of abstraction to hide the complexity behind different parts of a test.

* In an automated acceptance test, test steps represent the level of abstraction between the code that interacts with your application (for example, Page Objects in an automated web test, which are designed in terms of actions that you perform on a given page) and higher-level stories (sequences of more business-focused actions that illustrate how a given user story has been implemented). If your automated test is not UI-oriented (for example, if it calls a web service), steps orchestrate other more technical components such as REST clients. Steps can contain other steps, and are included in the Serenity reports. Whenever a step is executed, a screenshot is stored and displayed in the report.

```java
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
```

`features`

Feature file contains test steps based on Gherkin language.
```gherkin
Feature: Task 1

@task1
  Scenario: User ensures that cart has correct items in Amazon
          Given user is on the main page
          Then the page title should be "Amazon.com. Spend less. Smile more."
          When user searches "laptop"
          And user selects non-discount items
          And user navigates to cart box page
          Then selected non-discount item should be in the cart page
```

`step defintions`

Here we store code implementations. Please don't use page objects here, instead use @Steps:
```java
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
```
`page classes`

Please use *WebElementFacade* instead of *WebElement* and *ListOfWebElementFacades* instead of List\<WebElement\>.
These are the in-built Serenity wrapper classes that can help to make our life easier.

`WebElementFacade`
```java
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
```

`Runner Class`
* Runs feature file with tag.
```java
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features",
        glue = "com/openpayd/stepDefs",
        dryRun = false,
        tags = "@task1"
)
public class TestRunner {
}
```

`Simple Site Crawl`  
Write a crawler that opens up the “Shop By Department” dropdown menu on the amazon website, obtains a list of all department links and visits them to make sure that there are no dead links.
Your crawler should keep a list of visited links in a text file in the form (link, page title, status) , where status can be “OK” or “Dead link”.
After finishing, the crawler should name the file <timestamp>_results.txt.
```java
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
```

`API Test`  
https://jsonplaceholder.typicode.com/posts (GET) is a mock API endpoint which simulates the retrievals of blog posts. Implement the following test scenarios:
Scenario: Counting posts for user <user> When I get a list of blog posts using the API endpoint Then user <user> should have <numposts> posts.
The scenario should execute with the following
values of (<user>,<numposts>): (5,10), (7,10),
(9,10)
Scenario: Unique ID per post When I get a list of blog posts using the API endpoint Then each blog post should have a unique ID

```java
public class ApiTask extends BaseAPI {

    @Test
    public void task1() {
        Response response = given().
                contentType(ContentType.JSON).
                when().
                baseUri(BASE_URI).
                get();
        response.then().log().ifError();

        List<Integer> userIds = lastResponse().jsonPath().getList("userId");
        System.out.println(userIds.size());
        for (int i = 1; i <= userIds.size()/10; i++) {
            Response response1 = given().
                    contentType(ContentType.JSON).
                    when().
                    baseUri(BASE_URI).
                    queryParam("userId",i).
                    get();
            response1.then().log().ifError();
            Map<Integer,Integer> outPut = new HashMap<>();
            outPut.put(i,lastResponse().jsonPath().getList("id").size());
            System.out.println(outPut);
            Map<Integer,Integer> expected = new HashMap<>();
            expected.put(i,10);
            assertEquals(expected, outPut);
        }
    }

    @Test
    public void task2(){
        Response response = given().
                contentType(ContentType.JSON).
                when().
                baseUri(BASE_URI).
                get();
        response.then().log().ifError();
        List<Integer> ids = lastResponse().jsonPath().getList("id");
        for (int i = 0; i < ids.size()-1; i++) {
            for (int j = 1+i; j < ids.size(); j++) {
                System.out.println(ids.get(i)+" "+ids.get(j));
                assertNotEquals(ids.get(i),ids.get(j));
            }
        }
    }
}

```

`resolve conflicts`
- git add . (stage changes)
- git commit -m "message" (commit)
- git push (and push it to github)
- open pull request
