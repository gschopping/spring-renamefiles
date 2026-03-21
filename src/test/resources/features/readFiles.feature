Feature: Read Files

  Scenario: Read all media files from given directory
    Given root directory "../files"
    When read all media files
    Then the number of files should be 11
    And the first file should be "DJIOsmoPlus.DNG"
    And the last file should be "SonyFX30.HIF"

  Scenario: Read subdirectories with TimeLaps files
    Given root directory "../files"
    When read all timeLaps directories
    Then the number of directories should be 3