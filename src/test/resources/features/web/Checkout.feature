Feature: SauceDemo Checkout Functionality

  Background: User is Logged In
    Given I am logged into SauceDemo

  Scenario: Proceed to checkout with items in the cart
    Given I add multiple products to the cart
    When I proceed to checkout
    Then I should see the checkout information page

  Scenario: Complete checkout process successfully
    Given I am on the checkout information page
    When I fill out my personal information and continue
    And I complete the checkout process
    Then I should see the order confirmation page with a success message

  Scenario: Attempt to checkout without adding any items
    Given I am logged into SauceDemo
    When I navigate to the cart page without adding any items
    And I attempt to proceed to checkout
    Then I should see an error message or be unable to proceed with an empty cart