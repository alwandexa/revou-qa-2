Feature: Update Booking

  Scenario: Update an existing booking with valid details
    Given I have a valid booking ID
    When I update the booking with new valid details
    Then the booking should be updated successfully

  Scenario: Update an existing booking with invalid token
    Given I have a valid booking ID
    When I update the booking with an invalid token
    Then the booking update should fail with unauthorized error

  Scenario: Update a non-existing booking
    Given I have an invalid booking ID
    When I update the booking with new details
    Then the booking update should fail with not found error