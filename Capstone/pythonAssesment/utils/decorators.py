"""Reusable decorators for test and page-object step logging."""

from __future__ import annotations

import functools
import logging
import time
from typing import Any, Callable, TypeVar

F = TypeVar("F", bound=Callable[..., Any])

_logger = logging.getLogger("wordpress_capstone")


def log_step(step_name: str) -> Callable[[F], F]:
    """Log a human-readable step before running a function (methods or test helpers)."""

    def decorator(func: F) -> F:
        @functools.wraps(func)
        def wrapper(*args: Any, **kwargs: Any) -> Any:
            _logger.info("STEP: %s", step_name)
            return func(*args, **kwargs)

        return wrapper  # type: ignore[return-value]

    return decorator


def retry(
    *,
    attempts: int = 3,
    delay_seconds: float = 0.5,
    exceptions: tuple[type[BaseException], ...] = (Exception,),
) -> Callable[[F], F]:
    """Retry a callable on failure (useful for flaky UI timing)."""

    def decorator(func: F) -> F:
        @functools.wraps(func)
        def wrapper(*args: Any, **kwargs: Any) -> Any:
            last: BaseException | None = None
            for i in range(attempts):
                try:
                    return func(*args, **kwargs)
                except exceptions as e:  # noqa: PERF203 — explicit retry loop
                    last = e
                    if i == attempts - 1:
                        raise
                    time.sleep(delay_seconds)
            assert last is not None
            raise last

        return wrapper  # type: ignore[return-value]

    return decorator
