Feature: Unittest write tags

  Scenario: Write Author to Sony RAW file
    Given file to read "SonyA77.ARW"
    When write Author "Peter Bergé"
    Then tag "XMP:CaptionWriter" should contain author "Peter Bergé"
    And tag "EXIF:Artist" should contain author "Peter Bergé"
    And tag "EXIF:XPAuthor" should contain author "Peter Bergé"
    And tag "XMP:Creator" should contain author "Peter Bergé"
    And tag "IPTC:By-line" should contain author "Peter Bergé"
    And tag "IPTC:Writer-Editor" should contain author "Peter Bergé"

  Scenario: Write City to Sony RAW file
    Given file to read "SonyA77.ARW"
    When write City "Воронеж"
    Then tag "XMP:City" should contain city "Воронеж"

  Scenario: Write Title to Sony MP4 file
    Given file to read "SonyA6300.MP4"
    When write Title "Воронеж"
    Then tag "XMP:Title" should contain title "Воронеж"
    And tag "XMP:ImageDescription" should contain title "Воронеж"

  Scenario: Write Keys to DNG file
    Given file to read "DJIOsmoPlus.DNG"
    When write Keys "Sleutel 1,Sleutel 2,Дача"
    Then tag "EXIF:XPKeywords" should contain keys "Sleutel 1,Sleutel 2,Дача"
    And tag "XMP:XPSubject" should contain keys "Sleutel 1,Sleutel 2,Дача"
    And tag "IPTC:Keywords" should contain keys "Sleutel 1,Sleutel 2,Дача"
    And tag "XMP:LastKeywordXMP" should contain keys "Sleutel 1,Sleutel 2,Дача"
    And tag "XMP:LastKeywordIPTC" should contain keys "Sleutel 1,Sleutel 2,Дача"

  Scenario: Write Country to OnePlus2 MP4 file
    Given file to read "OnePlus2.mp4"
    When write Country "Israël"
    Then tag "XMP:Country" should contain country "Israël"

