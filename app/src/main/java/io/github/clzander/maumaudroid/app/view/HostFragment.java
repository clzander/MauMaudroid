package io.github.clzander.maumaudroid.app.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import io.github.clzander.maumaudroid.R;

public class HostFragment extends Fragment {

    public HostFragment() {
        // Required empty public constructor
    }

    public static HostFragment newInstance() {
        return new HostFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.host, container, false);
    }
}