Feature: Create Booking API

  Scenario: Successfully create a booking
    Given I have the booking details
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 200
    And the response body should contain the booking details

  Scenario: Create booking with missing required fields
    Given I have the booking details with missing fields
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 400
    And the response body should contain an error message

  Scenario: Create booking with invalid data types
    Given I have the booking details with invalid data types
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 400
    And the response body should contain an error message