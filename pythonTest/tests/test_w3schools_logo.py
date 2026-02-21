"""
Task 10: Launch Chrome and verify W3Schools logo is present on the page.
Uses Selenium WebDriver with pytest fixtures.
"""
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


BASE_URL = "https://www.w3schools.com/"
LOGO_ID = "w3-logo"


def test_w3schools_logo_present(chrome_driver):
    """
    Verify W3Schools logo is present on https://www.w3schools.com/
    Uses chrome_driver fixture for browser setup/teardown.
    """
    driver = chrome_driver
    driver.get(BASE_URL)

    logo = WebDriverWait(driver, 15).until(
        EC.visibility_of_element_located((By.ID, LOGO_ID))
    )
    assert logo.is_displayed(), "W3Schools logo should be present on the page"
