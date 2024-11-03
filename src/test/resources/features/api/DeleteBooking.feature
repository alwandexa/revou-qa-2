Feature: Delete Booking API

  Scenario: Successfully delete a booking
    Given I have a valid booking ID
    When I send a DELETE request to the "/booking" endpoint
    Then the response status code should be 201
    And the booking should be deleted successfully

  Scenario: Delete booking with invalid token
    Given I have a valid booking ID
    When I send a DELETE request to the "/booking" endpoint with an invalid token
    Then the response status code should be 403
    And the response body should contain an error message

  Scenario: Delete a non-existing booking
    Given I have an invalid booking ID
    When I send a DELETE request to the "/booking" endpoint
    Then the response status code should be 404
    And the response body should contain an error message