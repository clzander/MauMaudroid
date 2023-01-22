package io.github.clzander.maumaudroid.app.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.clzander.maumaudroid.R;
import io.github.clzander.maumaudroid.app.control.AppControllerInterface;

public class MainMenuFragment extends Fragment {

    private AppControllerInterface appController;

    public MainMenuFragment() {
    }

    private void setAppController(AppControllerInterface appController) {
        this.appController = appController;
    }

    public static MainMenuFragment newInstance(AppControllerInterface appController) {
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        mainMenuFragment.setAppController(appController);
        return mainMenuFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.imageView).setOnClickListener(view1 -> {
            this.appController.hostingRequested();
        });

        view.findViewById(R.id.imageView2).setOnClickListener(view2 -> {
            this.appController.joiningRequested();
        });
    }
}