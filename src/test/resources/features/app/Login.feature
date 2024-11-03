Feature: SauceDemo Authorization

  Background: I am on the login screen
    Given I am on the login screen

  Scenario: Successful login
    When I enter username "bob@example.com"
    And I enter password "10203040"
    And I tap on the login button
    Then I should be successfully logged in

  Scenario: Login with missing username
    When I enter password "10203040"
    And I tap on the login button
    Then I should see a username error message "Username is required"

  Scenario: Login with missing password
    When I enter username "bob@example.com"
    And I tap on the login button
    Then I should see a password error message "Password is required"

  Scenario: Login with invalid credentials
    When I enter username "wronguser@example.com"
    And I enter password "wrongpassword"
    And I tap on the login button
    Then I should see an error message "Invalid username or password"
