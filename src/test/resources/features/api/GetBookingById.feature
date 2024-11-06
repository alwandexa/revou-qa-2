Feature: Booking - Retrieve Booking by ID

  Scenario: Successfully retrieve a booking by ID
    Given I have access to the booking service
    When I send a GET request to the "/booking" endpoint with booking ID 1
    Then I should receive the booking details for booking ID 1
    And the response status code should be 200

  Scenario: Fail to retrieve a booking with non-existing ID
    Given I have access to the booking service
    When I send a GET request to the "/booking" endpoint with non-existing booking ID -1
    Then I should not receive any booking details
    And the response status code should be 404

  Scenario: Fail to retrieve a booking without authentication
    Given I have access to the booking service
    And I do not provide any authentication credentials
    When I send a GET request to the "/booking" endpoint with booking ID 1
    And the response status code should be 401
    
  Scenario: Fail to retrieve a booking with invalid basic authentication
    Given I have access to the booking service
    And I am authenticated with invalid basic auth credentials
    When I send a GET request to the "/booking" endpoint with booking ID 1
    Then I should not receive any booking details
    And the response status code should be 401
