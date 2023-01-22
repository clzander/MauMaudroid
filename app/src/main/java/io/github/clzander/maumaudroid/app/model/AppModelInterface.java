package io.github.clzander.maumaudroid.app.model;

/**
 * Model for saving the state of the app.
 */
public interface AppModelInterface extends AppStateObservable {

    /**
     * Update the state of the app
     */
    void setState(AppState state);

    /**
     * Returns the current state of the app
     * @return the app state
     */
    AppState getCurrentState();
}
