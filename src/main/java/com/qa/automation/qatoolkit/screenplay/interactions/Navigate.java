package com.qa.automation.qatoolkit.screenplay.interactions;

import com.qa.automation.qatoolkit.screenplay.Action;
import com.qa.automation.qatoolkit.screenplay.Actor;

public class Navigate extends Action {
    private final Direction direction;
    private final String url;

    private Navigate(Direction direction, String url) {
        this.direction = direction;
        this.url = url;
    }

    public static Navigate to(String url) {
        return new Navigate(Direction.TO, url);
    }

    public static Navigate back() {
        return new Navigate(Direction.BACK, null);
    }

    public static Navigate forward() {
        return new Navigate(Direction.FORWARD, null);
    }

    @Override
    protected void perform(Actor actor) {
        switch (direction) {
            case TO -> driver(actor).get(url);
            case BACK -> driver(actor).navigate().back();
            case FORWARD -> driver(actor).navigate().forward();
            default -> throw new IllegalStateException("Unsupported navigation direction: " + direction);
        }
    }

    private enum Direction {
        TO,
        BACK,
        FORWARD
    }
}
