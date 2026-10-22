package com.qa.automation.qatoolkit.screenplay;

import java.util.HashMap;
import java.util.Map;

/**
 * The central Screenplay participant. An Actor is given {@link Ability abilities} and then
 * performs {@link Performable performables} (tasks and actions) or asks {@link Question questions}.
 */
public class Actor {
    private final String name;
    private final Map<Class<? extends Ability>, Ability> abilities = new HashMap<>();

    public Actor(String name) {
        this.name = name;
    }

    /**
     * Grants this actor an ability. Abilities are keyed by their concrete class, so granting
     * a second ability of the same class replaces the first.
     */
    public <T extends Ability> void can(T ability) {
        abilities.put(ability.getClass(), ability);
    }

    /**
     * Returns the ability of the given class.
     *
     * @throws IllegalStateException if the actor was never granted that ability
     */
    @SuppressWarnings("unchecked")
    public <T extends Ability> T abilityTo(Class<T> abilityClass) {
        T ability = (T) abilities.get(abilityClass);
        if (ability == null) {
            throw new IllegalStateException("Actor '" + name + "' does not have ability " + abilityClass.getSimpleName());
        }
        return ability;
    }

    public void attemptsTo(Performable... performables) {
        for (Performable performable : performables) {
            performable.performAs(this);
        }
    }

    public <T> T asksFor(Question<T> question) {
        return question.answeredBy(this);
    }

    public String getName()
    {
        return name;
    }
}
