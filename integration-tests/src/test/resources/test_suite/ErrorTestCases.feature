Feature: Test Suite for error adding passenger to flight

 # @ignore
 # Scenario: Wrong flight number is selected
 #   Given passenger selects flight "NON-EXISTANT"
 #   And flight catalog doesn't have this flight
 #   And service is initialized
 #   When agent pass all information to the system and continue "no"
 #   And system is called
 #   Then no data is added to file
 #   And agent is requested to retry

  Scenario: Register a passenger in a non-existing flight
    Given passenger selects flight "UQAM007"
    And flight catalog doesn't have this flight
    And service is initialized
    When agent pass all information to the system and continue "no"
    Then error message "Flight not found. Please verify the flight number." is displayed

  Scenario: Booking fails if passenger information is incomplete
    Given passenger selects flight "UQAM005"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 10, number of business class seats is 20 and number of economy seats is 50
    And service is initialized
    And a passenger with passport number ""
    And name "Alice Smith"
    And age 25
    And looking to buy in "first" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then file contains an entry with missing passenger information
    And agent is requested to retry

  Scenario: Booking fails when exceeding available economy seats
    Given passenger selects flight "UQAM032"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 3, number of business class seats is 5 and number of economy seats is 2
    And service is initialized
    And a passenger with passport number:
      | PASSPORT_OVER001 |
      | PASSPORT_OVER002 |
      | PASSPORT_OVER003 |
    And name:
      | OverPassenger_1 |
      | OverPassenger_2 |
      | OverPassenger_3 |
    And age:
      | 30 |
      | 35 |
      | 40 |
    And looking to buy in class:
      | economy |
      | economy |
      | economy |
    When agent pass all information to the system and continue "yes"
    And system is called
    Then passenger "OverPassenger_1" is added to fly "UQAM032"
    Then passenger "OverPassenger_2" is added to fly "UQAM032"
    Then passenger "OverPassenger_3" is not added to fly "UQAM032"

  Scenario: Booking fails if no seats are available in any class
    Given passenger selects flight "UQAM999"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 0, number of business class seats is 0 and number of economy seats is 0
    And service is initialized
    And a passenger with passport number "FULLPLANE123"
    And name "Blocked Passenger"
    And age 50
    And looking to buy in "economy" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then an error message "No seats available" is displayed

  Scenario: Booking fails if age exceeds -1
    Given passenger selects flight "UQAM777"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 5, number of business class seats is 10 and number of economy seats is 30
    And service is initialized
    And a passenger with passport number "PASSPORT_AGE999"
    And name "Old Grandpa"
    And age -1
    And looking to buy in "economy" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then system prevents booking due to invalid age
