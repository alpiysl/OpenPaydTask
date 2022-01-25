Feature: Task 1

  @task1
  Scenario: User ensures that cart has correct items in Amazon
    Given user is on the main page
    Then the page title should be "Amazon.com. Spend less. Smile more."
    When user searches "laptop"
    And user selects non-discount items
    And user navigates to cart box page
    Then selected non-discount item should be in the cart page
