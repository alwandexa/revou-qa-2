Feature: SauceDemo Cart Functionality

  Background:
    Given I am logged into SauceDemo

  Scenario: Add a single product to the cart
    When I add a product to the cart
    Then the product should be in the cart
    And the cart badge should show "1"

  Scenario: Add multiple products to the cart
    When I add multiple products to the cart
    Then all selected products should be in the cart
    And the cart badge should show "3"

  Scenario: Remove a product from the cart
    Given I have added a product to the cart
    When I remove the product from the cart
    Then the cart should be empty
    And the cart badge should not be visible

  Scenario Outline: Add specific products to cart
    When I add "<product_name>" to the cart
    Then the product "<product_name>" should be in the cart
    And the price should match "<price>"

    Examples:
      | product_name                   | price  |
      | Sauce Labs Backpack           | $29.99 |
      | Sauce Labs Bike Light         | $9.99  |
      | Sauce Labs Bolt T-Shirt       | $15.99 |
      | Sauce Labs Fleece Jacket      | $49.99 |

  Scenario: Verify cart persistence after logout and login
    Given I have added a product to the cart
    When I logout from the application
    And I login again as "standard_user"
    Then the cart should retain the previously added items

  Scenario: Update product quantity in cart
    Given I have added a product to the cart
    When I update the quantity to "2"
    Then the cart total should be updated accordingly