Feature: read timeline yaml file

  Scenario: count timelines
    Given timeline file "timeline.yml"
    Then number of timelines should be 506

  Scenario: 4th item
    Given timeline file "timeline.yml"
    When get element 4
    Then copyright should be "Guido Schöpping (2025)"
    And country should be "Tsjechië"
    And description should be "Hradčany"
    And startdate should be "2015-03-21 05:00:00"
    And enddate should be "2015-03-23 09:00:00"

  Scenario: get timeline for certain date and time
    Given timeline file "timeline.yml"
    When date and time is "2019-10-25 10:00:00"
    Then description should be "Wandeling langs het stuwmeer van de Gileppe"

    Given timeline file "timeline.yml"
    When date and time is "2019-10-25 23:00:00"
    Then description should be "Monschau bij nacht"

    Given timeline file "timeline.yml"
    When date and time is "2019-06-28 12:00:00"
    Then description should be "Wandeling langs de Warche"

    Given timeline file "timeline.yml"
    When date and time is "2010-10-25 23:00:00"
    Then an error "2010-10-25 23:00:00 is outside range timelines" should be shown

  Scenario: faulty yaml file
    Given timeline file "not_a_yaml.yml"
    Then an error "Error on line 11, column 5: -startdate: \"2019-04-13 10:00:00\"" should be shown

  Scenario: space missing
    Given timeline file "not_a_yaml2.yml"
    Then an error "Error on line 12, column 7: undefined alias title:Tuin" should be shown

  Scenario: invalid character
    Given timeline file "not_a_yaml3.yml"
    Then an error "Error on line 14, column 17: undefined alias location: `Thuis" should be shown

  Scenario: incorrect date format
    Given timeline file "incorrect_date.yml"
    Then an error "Error in timeline 1, startdate: 2019-04 10:00:00 is not valid" should be shown

  Scenario: 2 timelines with same startdate
    Given timeline file "same_date.yml"
    Then an error "Error in timeline 2, startdate: 2019-04-05 10:00:00 already exists" should be shown

  Scenario: incorrect countrycode is used
    Given timeline file "invalid_countrycode.yml"
    Then an error "Error in timeline 3, countrycode: BX is not valid" should be shown

  Scenario: invalid variable is used
    Given timeline file "invalid_variable.yml"
    Then an error "Error on line 13, column 11: undefined alias <<: *default" should be shown

  Scenario: startdate not filled
    Given timeline file "null_date.yml"
    Then an error "Error in timeline 1, startdate is not filled" should be shown

  Scenario: enabled is set to false
    Given timeline file "disabled.yml"
    Then variable enabled should be set to false