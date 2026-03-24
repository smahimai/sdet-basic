"""WordPress.org theme directory flow: title, Extend → Themes, search."""

from __future__ import annotations

import pytest

from utils.decorators import log_step


@log_step("Verify home page title contains expected branding")
def _assert_title_contains_wordpress(title: str) -> None:
    assert "WordPress" in title, f"Unexpected title: {title!r}"


@pytest.mark.wordpress
@pytest.mark.smoke
def test_launch_wordpress_and_title_contains_wordpress(home_page) -> None:
    """1. Launch wordpress.org and verify the page title."""
    home_page.open()
    _assert_title_contains_wordpress(home_page.get_title())


@pytest.mark.wordpress
def test_extend_menu_opens_themes(driver, home_page) -> None:
    """2. Hover Extend, open submenu, scroll to Themes, and navigate to theme directory."""
    home_page.open()
    home_page.hover_extend_and_open_themes()
    assert "themes" in driver.current_url.casefold()


@pytest.mark.wordpress
@pytest.mark.parametrize("theme_query", ["Astra", "Kadence"])
def test_search_theme_and_results_show_titles(driver, home_page, themes_page, theme_query) -> None:
    """3. Search for a theme and verify result cards show titles (parametrized queries)."""
    home_page.open()
    home_page.hover_extend_and_open_themes()

    themes_page.search_themes(theme_query)
    titles = themes_page.get_visible_theme_titles()
    themes_page.assert_results_contain_query(theme_query, titles)


@pytest.mark.wordpress
def test_end_to_end_theme_directory_flow(driver, home_page, themes_page) -> None:
    """Full flow combining steps 1–3 in one scenario."""
    home_page.open()
    _assert_title_contains_wordpress(home_page.get_title())

    home_page.hover_extend_and_open_themes()
    assert "themes" in driver.current_url.casefold()

    query = "Twenty Twenty-Five"
    themes_page.search_themes(query)
    titles = themes_page.get_visible_theme_titles()
    themes_page.assert_results_contain_query(query, titles)
