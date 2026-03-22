Feature: RenameFiles

  Scenario: rename files in root directory
    Given root directory
    And clean subdirectory "results"
    And config file is "config.yml"
    And timeline file is "timeline.yml"
    When rename all files
    Then in subdirectory "results" 11 files will be found

  Scenario: rename files in Timelaps directory
    Given root directory
    And clean subdirectory "Timelaps1/results"
    And config file is "config.yml"
    And timeline file is "timeline.yml"
    When rename all timelaps files in subdir "Timelaps1"
    Then in subdirectory "Timelaps1/results" 7 files will be found

