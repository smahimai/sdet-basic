Feature: WordPress photo directory validation

  Scenario: Validate WordPress get page and photo directory search
    Given I launch the WordPress URL
    Then I verify the page title contains "WordPress.org"
    When I click on Get WordPress option on top left
    Then I verify middle page text as "Get WordPress"
    And I click on Community and then Photo Directory
    When I search for picture name "nature"
    Then I verify pictures are displayed
