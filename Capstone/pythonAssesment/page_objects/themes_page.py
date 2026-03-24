from __future__ import annotations

from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.remote.webdriver import WebDriver
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

from utils.decorators import log_step


class WordPressThemesPage:
    """Theme directory search and results."""

    _THEME_SEARCH_INPUT = (By.CSS_SELECTOR, "input.wp-block-search__input[placeholder*='Search themes']")
    _THEME_TITLE_HEADINGS = (By.CSS_SELECTOR, "li.wp-block-post h2.wp-block-post-title")

    def __init__(self, driver: WebDriver, wait: WebDriverWait) -> None:
        self._driver = driver
        self._wait = wait

    @log_step("Search themes by name and submit")
    def search_themes(self, query: str) -> None:
        search = self._wait.until(EC.visibility_of_element_located(self._THEME_SEARCH_INPUT))
        self._driver.execute_script("arguments[0].scrollIntoView({block: 'center'});", search)
        search.clear()
        search.send_keys(query)
        search.send_keys(Keys.ENTER)
        self._wait.until(
            lambda d: query.casefold() in d.current_url.casefold()
            or d.find_elements(*self._THEME_TITLE_HEADINGS)
        )

    @log_step("Collect visible theme titles from search results")
    def get_visible_theme_titles(self) -> list[str]:
        self._wait.until(EC.presence_of_all_elements_located(self._THEME_TITLE_HEADINGS))
        elements = self._driver.find_elements(*self._THEME_TITLE_HEADINGS)
        titles = [el.text.strip() for el in elements if el.text.strip()]
        return titles

    def assert_results_contain_query(self, query: str, titles: list[str]) -> None:
        q = query.casefold()
        matches = [t for t in titles if q in t.casefold()]
        assert titles, "Expected at least one theme title in results."
        assert matches, f"No theme titles contained query {query!r}. Got: {titles[:10]}"
