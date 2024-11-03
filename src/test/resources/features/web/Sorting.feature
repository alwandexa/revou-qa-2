Feature: SauceDemo Product Sorting Functionality
  Scenario: Sort items by price in ascending order
    Given I am logged into SauceDemo
    When I select the "Price (low to high)" sorting option
    Then the products should be displayed in ascending order of price

  Scenario: Sort items by price in descending order
    Given I am logged into SauceDemo
    When I select the "Price (high to low)" sorting option
    Then the products should be displayed in descending order of price

  Scenario: Sort items by name in ascending order
    Given I am logged into SauceDemo
    When I select the "Name (A to Z)" sorting option
    Then the products should be displayed in alphabetical order

  Scenario: Sort items by name in descending order
    Given I am logged into SauceDemo
    When I select the "Name (Z to A)" sorting option
    Then the products should be displayed in reverse alphabetical order