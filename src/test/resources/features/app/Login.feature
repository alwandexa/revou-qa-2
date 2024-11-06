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
    Then I should see an error message "Provided credentials do not match any user in this service."

  Scenario: Login with empty username and password
    When I enter username ""
    And I enter password ""
    And I tap on the login button
    Then I should see a username error message "Username is required"
    And I should see a password error message "Password is required"

  Scenario: Login with invalid username format
    When I enter username "invalidemail"
    And I enter password "10203040"
    And I tap on the login button
    Then I should see an error message "Invalid email format"

  Scenario: Login with invalid password
    When I enter username "bob@example.com"
    And I enter password "short"
    And I tap on the login button
    Then I should see a password error message "Password must be at least 8 characters long"

  Scenario: Login without special characters in password
    When I enter username "bob@example.com"
    And I enter password "10203040"
    And I tap on the login button
    Then I should see a password error message "Password must contain at least one special character"

  Scenario: Login with SQL injection attempt
    When I enter username "' OR '1'='1"
    And I enter password "' OR '1'='1"
    And I tap on the login button
    Then I should see an error message "Invalid input detected"

  Scenario: Login with extremely long username
    When I enter username "verylongusername123456789012345678901234567890123456789012345678901234567890@example.com"
    And I enter password "10203040"
    And I tap on the login button
    Then I should see a username error message "Username cannot exceed 50 characters"

  Scenario: Login with extremely long password
    When I enter username "bob@example.com"
    And I enter password "verylongpassword123456789012345678901234567890123456789012345678901234567890"
    And I tap on the login button
    Then I should see a password error message "Password cannot exceed 50 characters"

  Scenario: Login with password containing spaces
    When I enter username "bob@example.com"
    And I enter password "pass word with spaces"
    And I tap on the login button
    Then I should see a password error message "Password cannot contain spaces"

  Scenario: Login with HTML tags in username
    When I enter username "<script>alert('test')</script>"
    And I enter password "10203040"
    And I tap on the login button
    Then I should see an error message "Invalid characters in username"

  Scenario: Login with case-sensitive username
    When I enter username "BOB@EXAMPLE.COM"
    And I enter password "10203040"
    And I tap on the login button
    Then I should be successfully logged in

    
