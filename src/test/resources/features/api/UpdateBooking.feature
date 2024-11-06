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

  Scenario: Update booking with missing required fields
    Given I have a valid booking ID
    When I update the booking with missing required fields
    Then the booking update should fail with bad request error

  Scenario: Update booking with invalid date format
    Given I have a valid booking ID
    When I update the booking with invalid check-in/check-out dates
    Then the booking update should fail with bad request error

  Scenario: Partial update of booking details
    Given I have a valid booking ID
    When I perform a partial update with only first name and last name
    Then the booking should be updated successfully
    And other booking details should remain unchanged

  Scenario: Update booking with dates in the past
    Given I have a valid booking ID
    When I update the booking with past dates
    Then the booking update should fail with bad request error

  Scenario: Update booking with check-out date before check-in date
    Given I have a valid booking ID
    When I update the booking with check-out date before check-in date
    Then the booking update should fail with bad request error

  Scenario: Update booking with invalid price values
    Given I have a valid booking ID
    When I update the booking with negative or zero price values
    Then the booking update should fail with bad request error

  Scenario: Concurrent update of the same booking
    Given I have a valid booking ID
    When multiple users try to update the same booking simultaneously
    Then only one update should succeed
    And other updates should fail with conflict error

    