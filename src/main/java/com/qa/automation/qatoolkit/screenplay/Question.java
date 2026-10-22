package com.qa.automation.qatoolkit.screenplay;

/**
 * Represents a query about the system state as observed by an Actor.
 * <p>
 * Questions are used to ask the Actor for information such as element text, URL,
 * presence, selected value, cookie value, or boolean state checks. The question should
 * be implemented in terms of the Actor and the abilities it has, rather than by reading
 * local variables or external state directly.
 * <p>
 * Example:
 * - Text.of(By.id("message"))
 * - Url.contains("/login")
 * - Value.of(By.id("username"))
 * - CookieValue.of("session_token")
 */
public interface Question<T> {
    T answeredBy(Actor actor);
}
