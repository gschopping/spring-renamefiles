Feature: read config yaml file

  Scenario: count fileTypes
    Given config file "config.yml"
    Then number of fileTypes should be 7
    And regexMedia should be "^(.*\.MP4|.*\.AVI|.*\.M2TS|.*\.JPG|.*\.ARW|.*\.DNG|.*\.HIF)$"

  Scenario: 4th item
    Given config file "config.yml"
    When get item 4
    Then fileType should be "JPEG"
    And extension should be "JPG"
    And datetime should be "DateTimeOriginal"
    And timezone should be null
    And gpslatitude should be "GPSLatitude"
    And gpslongitude should be "GPSLongitude"
    And isWritable should be true
    And isPhotoFormat should be true

  Scenario: get path settings
    Given config file "config.yml"
    When get path settings
    Then pathResults should be "results"
    And pathTimeLaps should be "^(Timelaps\d+)$"
    And pathGps should be "^(GPS\d+)$"

  Scenario: get exif settings
    Given config file "config.yml"
    When get the author exif tags
    Then tags should contain "XMP:CaptionWriter"

    Given config file "config.yml"
    When get the location exif tags"
    Then tags should contain "IPTC:Sub-location"

    Given config file "config.yml"
    When get the title exif tags
    Then tags should contain "EXIF:XPTitle"

    Given config file "config.yml"
    When get the description exif tags
    Then tags should contain "XMP:Headline"

  Scenario: get OpenStreetMap settings
    Given config file "config.yml"
    When get the title osm tags
    Then tags should contain "village"

    Given config file "config.yml"
    When get the description osm tags
    Then tags should contain "display_name"

  Scenario: get exif settings
    Given config file "config_empty.yml"
    Then config should be empty

