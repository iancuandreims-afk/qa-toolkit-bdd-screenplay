package com.qa.automation.qatoolkit.screenplay;

/**
 * A high-level Screenplay task that an Actor can perform.
 * <p>
 * Use Task for meaningful user workflows or business-level actions that are composed of
 * smaller interactions, such as "log in", "submit checkout", "register account", or
 * "subscribe to newsletter".
 * <p>
 *  Example:
 *  when(theUser).attemptsTo(
 *      SubscribeToNewsletter.withData("Jane", "Doe", "jane@example.com")
 *  );
 * <p>
 * Tasks sit above low-level Action and TimedAction implementations. They are meant to be
 * readable and scenario-oriented, emphasizing the user story rather than raw Selenium calls.
 */
public interface Task extends Performable {
}
