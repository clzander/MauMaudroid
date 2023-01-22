package io.github.clzander.maumaudroid.app.model;

import io.github.clzander.maumaudroid.app.view.AppStateChangedListener;

public interface AppStateObservable {

    void registerObserver(AppStateChangedListener listener);
}
