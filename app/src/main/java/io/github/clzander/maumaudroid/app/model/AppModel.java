package io.github.clzander.maumaudroid.app.model;

import java.util.ArrayList;
import java.util.List;

import io.github.clzander.maumaudroid.app.view.AppStateChangedListener;

public class AppModel implements AppModelInterface, AppStateObservable {

    AppState currentState;
    List<AppStateChangedListener> appStateChangedListeners;

    public AppModel() {
        this.appStateChangedListeners = new ArrayList<>();
    }

    public void registerObserver(AppStateChangedListener listener) {
        this.appStateChangedListeners.add(listener);
    }

    @Override
    public void setState(AppState state) {
        this.currentState = state;
        this.appStateChangedListeners.forEach(listener -> {
            listener.stateUpdate(this.currentState);
        });
    }

    @Override
    public AppState getCurrentState() {
        return this.currentState;
    }
}
