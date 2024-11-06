Feature: SauceDemo Login Functionality

  Scenario Outline: Login with different user types
    Given I am on the SauceDemo login page
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should see "<expected_result>"

    Examples:
      | username                | password       | expected_result          |
      | standard_user          | secret_sauce   | products page            |
      | locked_out_user        | secret_sauce   | locked out error message |
      | problem_user           | secret_sauce   | products page            |
      | performance_glitch_user| secret_sauce   | products page            |
      | error_user            | secret_sauce    | products page            |
      | visual_user           | secret_sauce    | products page            |
      | standard_user          | wrong_password | login error message      |
      | invalid_user           | secret_sauce   | login error message      |

  Scenario: Successful Login
    Given I am on the SauceDemo login page
    When I enter a valid username and password
    And I click the login button
    Then I should see the products page

  Scenario: Logout from SauceDemo
    Given I am logged into SauceDemo
    When I logout from the application
    Then I should see the login page