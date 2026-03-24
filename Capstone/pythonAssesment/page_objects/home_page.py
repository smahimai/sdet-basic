from __future__ import annotations

from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webdriver import WebDriver
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

from utils.decorators import log_step

HOME_URL = "https://wordpress.org/"


class WordPressHomePage:
    """WordPress.org home page interactions."""

    _EXTEND_TOGGLE = (By.CSS_SELECTOR, "button[aria-label='Extend submenu']")
    _THEMES_IN_EXTEND = (
        By.XPATH,
        "//button[@aria-label='Extend submenu']/ancestor::li[contains(@class,'wp-block-navigation-item')]"
        "//a[contains(@href,'wordpress.org/themes')][.//span[contains(normalize-space(),'Themes')]]",
    )

    def __init__(self, driver: WebDriver, wait: WebDriverWait) -> None:
        self._driver = driver
        self._wait = wait

    @log_step("Open WordPress.org home page")
    def open(self) -> None:
        self._driver.get(HOME_URL)

    @log_step("Read browser page title")
    def get_title(self) -> str:
        return self._driver.title

    @log_step("Hover Extend, open submenu if needed, scroll to Themes, and click")
    def hover_extend_and_open_themes(self) -> None:
        extend = self._wait.until(EC.presence_of_element_located(self._EXTEND_TOGGLE))
        ActionChains(self._driver).move_to_element(extend).pause(0.2).perform()

        themes = self._wait_for_themes_link_clickable()

        self._driver.execute_script("arguments[0].scrollIntoView({block: 'center'});", themes)
        themes.click()

    def _wait_for_themes_link_clickable(self):
        """Extend uses click-to-open; hover alone may not reveal the submenu."""
        try:
            return self._wait.until(EC.element_to_be_clickable(self._THEMES_IN_EXTEND))
        except TimeoutException:
            extend = self._driver.find_element(*self._EXTEND_TOGGLE)
            extend.click()
            return self._wait.until(EC.element_to_be_clickable(self._THEMES_IN_EXTEND))
