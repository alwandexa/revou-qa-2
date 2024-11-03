Feature: Booking - Retrieve Bookings

  Scenario: Successfully retrieve all bookings
    Given I have access to the booking service
    When I send a GET request to the "/booking" endpoint
    Then I should receive a list of bookings
    And the response status code should be 200

  Scenario: Fail to retrieve bookings with incorrect endpoint
    Given I have access to the booking service
    When I send a GET request to an incorrect endpoint "/bookings"
    Then I should not receive any bookings
    And the response status code should be 404
