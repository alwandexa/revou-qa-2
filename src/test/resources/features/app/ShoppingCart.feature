# Feature: SauceDemo Shopping Cart

#   Background: I have items in my cart
#     Given the application is launched
#     And I am on the main product screen
#     And I have added items to my shopping cart

#   Scenario: View items in the shopping cart
#     When I tap on the cart badge
#     Then the shopping cart screen should be displayed
#     And each item in the cart should show a name, quantity, and price

#   Scenario: Add a product to the shopping cart
#     Given I am on the product detail screen for "Sauce Labs Backpack"
#     When I tap on the "Add To Cart" button
#     Then the cart badge should display the updated item count
