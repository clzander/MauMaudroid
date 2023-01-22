package io.github.clzander.maumaudroid.app.view;

import io.github.clzander.maumaudroid.app.model.AppState;

public interface AppStateChangedListener {

    void stateUpdate(AppState newState);
}
