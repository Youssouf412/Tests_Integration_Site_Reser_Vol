Feature: Test Suite for successfully adding passenger to flight

  Scenario Outline: Add First Class Passengers
    Given passenger selects flight "<flightNumber>"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 5, number of business class seats is 12 and number of economy seats is 30
    And service is initialized
    And a passenger with passport number "<passengerPassport>"
    And name "<passengerName>"
    And age <passengerAge>
    And looking to buy in "<passengerClass>" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then passenger is added to fly "<flightNumber>"
    Examples:
      | passengerPassport | passengerName | passengerAge | passengerClass | flightNumber |
      | PASSPORT_CAD001   | Passenger_1   | 10           | first          | UQAM005      |
      | PASSPORT_CAD002   | Passenger_2   | 20           | first          | UQAM005      |
      | PASSPORT_CAD003   | Passenger_3   | 30           | first          | UQAM005      |
      | PASSPORT_CAD004   | Passenger_4   | 40           | first          | UQAM005      |
      | PASSPORT_CAD005   | Passenger_5   | 50           | first          | UQAM005      |

  Scenario: Don't overbook the flight
    Given passenger selects flight "UQAM005"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 0, number of business class seats is 0 and number of economy seats is 2
    And service is initialized
    And a passenger with passport number:
      | PASSPORT_CAD001 |
      | PASSPORT_CAD002 |
      | PASSPORT_CAD003 |
    And name:
      | Passenger_1 |
      | Passenger_2 |
      | Passenger_3 |
    And age:
      | 10 |
      | 20 |
      | 30 |
    And looking to buy in class:
      | first |
      | first |
      | first |
    When agent pass all information to the system and continue "yes"
    And system is called
    Then passenger "Passenger_1" is added to fly "UQAM005"
    Then passenger "Passenger_2" is added to fly "UQAM005"
    Then passenger "Passenger_3" is not added to fly "UQAM005"

  Scenario Outline: Add Business Class Passengers
    Given passenger selects flight "<flightNumber>"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 5, number of business class seats is 12 and number of economy seats is 30
    And service is initialized
    And a passenger with passport number "<passengerPassport>"
    And name "<passengerName>"
    And age <passengerAge>
    And looking to buy in "<passengerClass>" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then passenger is added to fly "<flightNumber>"
    Examples:
      | passengerPassport | passengerName | passengerAge | passengerClass | flightNumber |
      | PASSPORT_CAD006   | Passenger_6   | 28           | business       | UQAM010      |
      | PASSPORT_CAD007   | Passenger_7   | 35           | business       | UQAM010      |
      | PASSPORT_CAD008   | Passenger_8   | 42           | business       | UQAM010      |
      | PASSPORT_CAD009   | Passenger_9   | 50           | business       | UQAM010      |
      | PASSPORT_CAD010   | Passenger_10  | 60           | business       | UQAM010      |

  Scenario: Do Not Overbook Economy Class
    Given passenger selects flight "UQAM030"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 5, number of business class seats is 12 and number of economy seats is 3
    And service is initialized
    And a passenger with passport number:
      | PASSPORT_CAD018 |
      | PASSPORT_CAD019 |
      | PASSPORT_CAD020 |
      | PASSPORT_CAD021 |
    And name:
      | Passenger_18 |
      | Passenger_19 |
      | Passenger_20 |
      | Passenger_21 |
    And age:
      | 22 |
      | 34 |
      | 46 |
      | 55 |
    And looking to buy in class:
      | economy |
      | economy |
      | economy |
      | economy |
    When agent pass all information to the system and continue "yes"
    And system is called
    Then passenger "Passenger_18" is added to fly "UQAM030"
    Then passenger "Passenger_19" is added to fly "UQAM030"
    Then passenger "Passenger_20" is added to fly "UQAM030"
    Then passenger "Passenger_21" is not added to fly "UQAM030"

  Scenario: Passenger books a flight with personal details
    Given passenger selects flight "UQAM005"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 10, number of business class seats is 20 and number of economy seats is 50
    And service is initialized
    And a passenger with passport number "PASSPORT_ABC456"
    And name "Youssouf Abdou"
    And age 25
    And looking to buy in "first" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then passenger "Youssouf Abdou" is added to fly "UQAM005"


  Scenario: Booking succeeds if age is valid
    Given passenger selects flight "UQAM124"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 10, number of business class seats is 20 and number of economy seats is 50
    And service is initialized
    And a passenger with passport number "PASSPORT_ABC456"
    And name "Youssouf Abdou"
    And age 25
    And looking to buy in "business" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then passenger "Youssouf Abdou" is added to fly "UQAM124"
    And confirmation message is displayed



  Scenario: Register a passenger in an existing flight
    Given flight "UQAM005" exists in the flight catalog
    And plane catalog is mocked where number of first class seats is 5, number of business class seats is 10 and number of economy seats is 30
    And service is initialized
    And a passenger with passport number "PASSPORT_789XYZ"
    And name "Pap Diop"
    And age 32
    And looking to buy in "business" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then passenger "Pap Diop" is added to fly "UQAM005"
    And confirmation message is displayed

    # Nouveaux ajouts au scénarios

  Scenario: Passenger data is saved in passengerData.csv
    Given passenger selects flight "UQAM099"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 2, number of business class seats is 2 and number of economy seats is 2
    And service is initialized
    And a passenger with passport number "PASSPORT_789XYZ"
    And name "Abdou Youssouf"
    And age 25
    And looking to buy in "economy" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then passenger is added to fly "UQAM099"


  Scenario: Add passenger at maximum valid age
    Given passenger selects flight "UQAM005"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 1, number of business class seats is 1 and number of economy seats is 1
    And service is initialized
    And a passenger with passport number "PASSPORT_789XYZ4"
    And name "Vieux"
    And age 120
    And looking to buy in "business" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then passenger is added to fly "UQAM005"

  Scenario: Add passenger First Class when name is empty
    Given passenger selects flight "UQAM005"
    And flight catalog is mocked
    And plane catalog is mocked where number of first class seats is 2, number of business class seats is 5 and number of economy seats is 6
    And service is initialized
    And a passenger with passport number "PASSPORT_789XYZ"
    And name ""
    And age 25
    And looking to buy in "economy" class
    When agent pass all information to the system and continue "no"
    And system is called
    Then passenger is not added to fly "UQAM005"