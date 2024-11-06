Feature: SauceDemo Checkout Process

  Background: I am ready to checkout
    Given the application is launched
    Given I am on the login screen
    When I enter username "bob@example.com"
    And I enter password "10203040"
    And I tap on the login button
    And I am on the main product screen
    And I have added items to my shopping cart
    And I tap on the cart badge

  Scenario: Complete checkout process with valid information
    When I tap on the "Proceed To Checkout" button
    And I enter valid shipping information
      | fullName       | address        | city   | state    | zipCode | country        |
      | Rebecca Winter | Mandorley 112  | Truro  | Cornwall | 89750   | United Kingdom |
    And I tap on the "To Payment" button
    And I enter valid payment information
      | cardHolder    | cardNumber           | expiryDate | securityCode |
      | Rebecca Winter| 1231 2312 3123 1231  | 12/34      | 123          |
    And I tap on the "Review Order" button
    And I tap on the "Review Order" button
    And I tap on the "Place Order" button
    Then I should see the "Checkout Complete" screen
    And I should see the message "Thank you for your order"
    And I should see the message "Your new swag is on its way"

  Scenario: Verify order total on review screen
    When I proceed to checkout review
    Then I should see the total number of items
    And I should see the total price including shipping
    And the total price should match the sum of items plus shipping

  Scenario: Return to shopping from checkout complete
    Given I have completed a checkout
    When I tap on "Continue Shopping" button
    Then I should be returned to the main product screen
    And my shopping cart should be empty

  Scenario: Validate required shipping fields
    When I leave the full name field empty
    And I tap on the "To Payment" button
    Then I should see the error message "Please provide your full name"
    When I leave the address field empty
    Then I should see the error message "Please provide your address"
    When I leave the zip code empty
    Then I should see the error message "Please provide your zip code"

  Scenario: Verify shipping address display on review screen
    When I enter shipping information
    And I proceed to the review screen
    Then the shipping address should display correctly
    And the payment method should display correctly
    And I should see the order summary with correct items

  Scenario: Cancel checkout process
    When I am on the shipping information screen
    And I tap on the back button
    Then I should return to the shopping cart
    And my cart items should remain unchanged 