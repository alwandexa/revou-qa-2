Feature: Auth - Create Token

  Scenario: Successfully generate an auth token with valid credentials
    Given I have valid user credentials
    When I send a POST request to the "/auth" endpoint
    Then I should receive a token in the response
    And the response status code should be 200

  Scenario: Fail to generate an auth token with invalid credentials
    Given I have invalid user credentials
    When I send a POST request to the "/auth" endpoint
    Then I should not receive a token in the response
    And the response status code should be 401

  Scenario: Validate missing password in the auth request
    Given I have valid username but missing password
    When I send a POST request to the "/auth" endpoint
    Then I should not receive a token in the response
    And the response status code should be 401

  Scenario: Validate missing username in the auth request
    Given I have valid password but missing username
    When I send a POST request to the "/auth" endpoint
    Then I should not receive a token in the response
    And the response status code should be 401
