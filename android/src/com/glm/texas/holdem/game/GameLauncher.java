package com.glm.texas.holdem.game;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.glm.texas.holdem.game.TexasHoldemGame;
/**
 * Class for launch the Game
 *
 * */
public class GameLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new TexasHoldemGame(), config);
    }
}
