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

  Scenario: Create booking with future dates
    Given I have booking details with future check-in and check-out dates
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 200
    And the response body should contain the booking details

  Scenario: Create booking with past dates
    Given I have booking details with past dates
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 400
    And the response body should contain an error message

  Scenario: Create booking with check-out date before check-in date
    Given I have booking details where check-out is before check-in
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 400
    And the response body should contain an error message

  Scenario: Create booking with same day check-in and check-out
    Given I have booking details with same day check-in and check-out
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 400
    And the response body should contain an error message

  Scenario: Create booking with maximum stay duration
    Given I have booking details with maximum allowed stay duration
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 200
    And the response body should contain the booking details

  Scenario: Create booking with special requests
    Given I have booking details with special requests
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 200
    And the response body should contain the booking details
    And the response should include the special requests

  Scenario: Create booking with invalid room type
    Given I have booking details with invalid room type
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 400
    And the response body should contain an error message

  Scenario: Create booking with maximum number of guests
    Given I have booking details with maximum allowed guests
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 200
    And the response body should contain the booking details

  Scenario: Create booking with excessive number of guests
    Given I have booking details with excessive number of guests
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 400
    And the response body should contain an error message

  Scenario: Create booking with special characters in guest name
    Given I have booking details with special characters in guest name
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 200
    And the response body should contain the booking details

  Scenario: Verify total price calculation
    Given I have booking details with multiple nights stay
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 200
    And the response body should contain the correct total price

  Scenario: Create booking without authentication token
    Given I have valid booking details but no auth token
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 401
    And the response body should contain an authentication error message

  Scenario: Create booking without Basic Authentication credentials
    Given I have valid booking details but no Basic Auth credentials
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 401
    And the response body should contain an authentication error message

  Scenario: Create booking with invalid Basic Authentication credentials
    Given I have the booking details
    And I have invalid Basic Auth credentials
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 401
    And the response body should contain an authentication error message

  Scenario: Create booking with incorrect Basic Authentication format
    Given I have the booking details
    And I have malformed Basic Auth header
    When I send a POST request to the "/booking" endpoint
    Then the response status code should be 401
    And the response body should contain an authentication error message

    