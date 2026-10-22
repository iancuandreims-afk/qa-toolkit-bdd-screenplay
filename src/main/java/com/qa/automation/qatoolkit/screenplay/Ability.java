package com.qa.automation.qatoolkit.screenplay;

/**
 * Marker interface for capabilities an Actor can possess.
 * <p>
 * Use an Ability for reusable browser or application capabilities, such as browsing,
 * authentication state, cookie management, or user context. An Ability is not a user task:
 * it describes what the Actor can do with a given environment or state.
 * <p>
 * Example:
 * - BrowseTheWeb
 * - ManageCookies
 * - ManageAuthentication
 * - ManageAlerts
 */
public interface Ability {
}
