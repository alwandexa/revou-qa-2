# Feature: SauceDemo Product Details

#   Background: I am on the main product screen
#     Given the application is launched
#     And I am on the main product screen

#   Scenario Outline: View details of products
#     When I tap on "<product>"
#     Then the product detail screen for "<product>" should be displayed
#     And the product name "<product>" should be displayed at the top
#     And the product price "<price>" should be displayed under the product name
#     And five review stars should be visible below the product price

#     Examples:
#       | product                     | price   |
#       | Sauce Labs Backpack        | $29.99  |
#       | Sauce Labs Bike Light      | $9.99   |
#       | Sauce Labs Bolt T-Shirt    | $15.99  |
#       | Sauce Labs Fleece T-Shirt  | $49.99  |
#       | Sauce Labs Onesie          | $7.99   |
#       | Test.allTheThings() T-Shirt| $15.99  |

#   Scenario Outline: Select different colors for a product
#     When I tap on "Sauce Labs Backpack"
#     And I tap on the "<color> circle" color option
#     Then the "<color> circle" should be selected

#     Examples:
#       | color  |
#       | black  |
#       | blue   |
#       | gray   |
#       | red    |

#   Scenario Outline: Adjust product quantity
#     When I tap on "Sauce Labs Backpack"
#     And I tap the "<button>" button <times> times
#     Then the quantity should be <expected>

#     Examples:
#       | button | times | expected |
#       | plus   | 2     | 3        |
#       | minus  | 1     | 0        |

#   Scenario: Verify product description
#     When I tap on "Sauce Labs Backpack"
#     Then the product description should contain "carry.allTheThings()"
#     And the product highlights section should be visible
