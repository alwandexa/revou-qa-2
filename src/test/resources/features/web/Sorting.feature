Feature: SauceDemo Product Sorting Functionality

  Background:
    Given I am logged into SauceDemo

  Scenario Outline: Sort products using different criteria
    When I select the "<sort_option>" sorting option
    Then the products should be sorted by "<sort_type>" in "<sort_order>" order

    Examples:
      | sort_option           | sort_type | sort_order  |
      | Name (A to Z)        | name      | ascending   |
      | Name (Z to A)        | name      | descending  |
      | Price (low to high)  | price     | ascending  |
      | Price (high to low)  | price     | descending   |

  # Additional test cases for specific product verifications
  Scenario: Verify lowest priced item appears first when sorting by price ascending
    When I select the "Price (low to high)" sorting option
    Then the first product should be "Sauce Labs Onesie"
    And the price should be "$7.99"

  Scenario: Verify highest priced item appears first when sorting by price descending
    When I select the "Price (high to low)" sorting option
    Then the first product should be "Sauce Labs Fleece Jacket"
    And the price should be "$49.99"