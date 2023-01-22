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

public class StartDisclaimerFragment extends Fragment {

    private AppControllerInterface appController;

    public StartDisclaimerFragment() {
        // Required empty public constructor
    }

    public static StartDisclaimerFragment newInstance(AppControllerInterface appController) {
        StartDisclaimerFragment fragment = new StartDisclaimerFragment();
        fragment.appController = appController;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.start_disclaimer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.disclaimer_start_button).setOnClickListener(startButton -> {
            appController.doBluetoothPreparation();
        });
    }
}