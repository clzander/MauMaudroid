package io.github.clzander.maumaudroid.app.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import io.github.clzander.maumaudroid.R;
import io.github.clzander.maumaudroid.app.control.AppControllerInterface;

public class GameFragment extends Fragment implements GameModelListener {

    private View view;
    private AppControllerInterface appController;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(AppControllerInterface appController) {
        GameFragment gameFragment = new GameFragment();
        gameFragment.appController = appController;
        return gameFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.appController.registerGameModelListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.view = view;

        this.appController.getStartView();

        view.findViewById(R.id.game_next_button).setOnClickListener(button -> {
            this.appController.nextCardRequested();
        });

        view.findViewById(R.id.game_select_button).setOnClickListener(button -> {
            this.appController.playSelectedCard();
        });

        view.findViewById(R.id.game_previous_button).setOnClickListener(button -> {
            this.appController.previousCardRequested();
        });

    }


    @Override
    public void newHandCard(int i) {
        ImageView imageView = this.view.findViewById(R.id.game_hand_image);
        imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), i));
    }

    @Override
    public void newDiscardPileCard(int i) {
        ImageView imageView = this.view.findViewById(R.id.game_discard_pile_image);
        imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), i));
    }
}