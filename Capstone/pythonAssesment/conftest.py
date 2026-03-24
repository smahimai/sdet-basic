"""Pytest fixtures: WebDriver lifecycle and page objects."""

from __future__ import annotations

import logging
import os

import pytest
from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.support.ui import WebDriverWait
from webdriver_manager.chrome import ChromeDriverManager

from page_objects import WordPressHomePage, WordPressThemesPage


@pytest.fixture(scope="session", autouse=True)
def _configure_logging() -> None:
    logging.basicConfig(level=logging.INFO, format="%(levelname)s %(name)s: %(message)s")


@pytest.fixture
def driver():
    options = webdriver.ChromeOptions()
    if os.getenv("HEADLESS", "").lower() in {"1", "true", "yes"}:
        options.add_argument("--headless=new")
    options.add_argument("--disable-gpu")
    options.add_argument("--window-size=1920,1080")
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")

    service = ChromeService(ChromeDriverManager().install())
    drv = webdriver.Chrome(service=service, options=options)
    drv.maximize_window()
    drv.implicitly_wait(0)
    yield drv
    drv.quit()


@pytest.fixture
def wait(driver):
    return WebDriverWait(driver, 25)


@pytest.fixture
def home_page(driver, wait) -> WordPressHomePage:
    return WordPressHomePage(driver, wait)


@pytest.fixture
def themes_page(driver, wait) -> WordPressThemesPage:
    return WordPressThemesPage(driver, wait)
