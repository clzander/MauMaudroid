package io.github.clzander.maumaudroid.app.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.clzander.maumaudroid.R;
import io.github.clzander.maumaudroid.app.control.AppControllerInterface;

public class JoinFragment extends Fragment {

    private AppControllerInterface appController;

    public JoinFragment() {
        // Required empty public constructor
    }


    public static JoinFragment newInstance(AppControllerInterface appController) {
        JoinFragment fragment = new JoinFragment();
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
        return inflater.inflate(R.layout.join, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView firstDeviceName = view.findViewById(R.id.join_device_name);
        String name = this.appController.firstDevice();
        firstDeviceName.setText(name);

        view.findViewById(R.id.join_previous_button).setOnClickListener(previous -> {
            TextView deviceName = view.findViewById(R.id.join_device_name);
            String deviceN = this.appController.previousDevice();
            deviceName.setText(deviceN);
        });

        view.findViewById(R.id.join_next_button).setOnClickListener(next -> {
            TextView deviceName = view.findViewById(R.id.join_device_name);
            String deviceN = this.appController.nextDevice();
            deviceName.setText(deviceN);
        });

        view.findViewById(R.id.join_select_button).setOnClickListener(select -> {
            this.appController.connectToCurrentDevice();
        });
    }
}