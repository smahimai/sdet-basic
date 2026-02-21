# MakeMyTrip Selenium Tests

Selenium WebDriver tests for MakeMyTrip using TestNG and Maven.

## Structure

```
MakeMyTrip/
├── pom.xml
├── README.md
└── src/test/
    ├── java/com/sdet/
    │   ├── utils/BrowserUtils.java    # Generic browser interaction helpers
    │   └── tests/
    │       ├── MakeMyTripLogoTest.java    # Task 3: Firefox - verify logo
    │       └── MakeMyTripFlightsTest.java # Task 4: Chrome - Flights, OneWay, FROM, TO
    └── resources/testng.xml
```

## Tests

| Test | Browser | Description |
|------|---------|-------------|
| MakeMyTripLogoTest | Firefox | Verifies MakeMyTrip logo is present on the page |
| MakeMyTripFlightsTest | Chrome | Clicks Flights, selects OneWay, enters FROM and TO locations |

## Prerequisites

- Java 11+
- Maven 3.6+
- Chrome and Firefox installed
- ChromeDriver and GeckoDriver on PATH (or Selenium 4.6+ auto-manages)

## Maven Commands

```bash
cd MakeMyTrip

# Compile
mvn clean compile

# Run all tests
mvn clean test
```

## Test Reports

After running tests:

- **Surefire reports**: `target/surefire-reports/`
- **Emailable HTML report**: `test-output/emailable-report.html`
- **TestNG XML report**: `test-output/testng-results.xml`
