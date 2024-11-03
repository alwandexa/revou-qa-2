Feature: SauceDemo Product Details

  Background: I am on the main product screen
    Given the application is launched
    And I am on the main product screen

  Scenario: View details of a specific product
    When I tap on "Sauce Labs Backpack"
    Then the product detail screen for "Sauce Labs Backpack" should be displayed
    And the product name "Sauce Labs Backpack" should be displayed at the top
    And the product price "$29.99" should be displayed under the product name
    And five review stars should be visible below the product price
