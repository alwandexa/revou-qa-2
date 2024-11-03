Feature: SauceDemo Cart Functionality
  Scenario: Add a single product to the cart
    Given I am logged into SauceDemo
    When I add a product to the cart
    Then the product should be in the cart

  Scenario: Add multiple products to the cart
    Given I am logged into SauceDemo
    When I add multiple products to the cart
    Then all selected products should be in the cart

  Scenario: Remove a product from the cart
    Given I am logged into SauceDemo
    And I have added a product to the cart
    When I remove the product from the cart
    Then the cart should be empty