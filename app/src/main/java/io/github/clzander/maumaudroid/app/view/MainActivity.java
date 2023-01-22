package io.github.clzander.maumaudroid.app.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.clzander.maumaudroid.R;
import io.github.clzander.maumaudroid.app.control.AppController;
import io.github.clzander.maumaudroid.app.control.AppControllerInterface;
import io.github.clzander.maumaudroid.app.model.AppState;

public class MainActivity extends AppCompatActivity implements AppStateChangedListener, AppViewInterface,
        BluetoothUpdateListener {

    private AppControllerInterface appController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.appController = new AppController(this);
        onAppStart();
    }

    @Override
    public void stateUpdate(AppState newState) {
        switch (newState) {
            case MAIN_MENU:
                this.onMainMenu();
                break;
            case HOSTING:
                this.onHosting();
                break;
            case JOINING:
                this.doJoining();
                break;
            case IN_GAME:
                this.onInGame();
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                         Start View                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void onAppStart() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerView, StartDisclaimerFragment.newInstance(this.appController))
                .commit();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                       Main Menu View                                       //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void onMainMenu() {
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, MainMenuFragment.newInstance(this.appController))
                .commit();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          Host View                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void onHosting() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, HostFragment.newInstance())
                .commit();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          Join View                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void doJoining() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, JoinFragment.newInstance(this.appController))
                .commit();
    }

    @Override
    public void newDeviceFound() {
        String text = "New device in range!";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          Game View                                         //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void onInGame() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, GameFragment.newInstance(this.appController))
                .commit();
    }

    @Override
    public void hostingRejected() {
        String text = "Discoverability is needed!";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void bluetoothRejected() {
        String text = "Bluetooth configuration incomplete!";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
