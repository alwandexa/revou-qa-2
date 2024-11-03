Feature: SauceDemo Login Functionality

  Scenario: Successful Login
    Given I am on the SauceDemo login page
    When I enter a valid username and password
    And I click the login button
    Then I should see the products page

  Scenario: Logout from SauceDemo
    Given I am logged into SauceDemo
    When I logout from the application
    Then I should see the login page