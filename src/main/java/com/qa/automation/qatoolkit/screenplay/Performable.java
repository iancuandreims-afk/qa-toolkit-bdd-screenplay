package com.qa.automation.qatoolkit.screenplay;

/**
 * Contract for anything that can be executed by an Actor.
 *
 * Screenplay tasks and interactions implement this contract so they can be passed to
 * Actor.attemptsTo(...) or Given/When/Then wrappers.
 *
 * In this toolkit, the concrete implementations are typically Action and TimedAction
 * subclasses, but the contract remains intentionally minimal and framework-neutral.
 */
public interface Performable {
    void performAs(Actor actor);
}
