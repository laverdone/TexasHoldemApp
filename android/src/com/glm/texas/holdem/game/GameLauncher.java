package com.glm.texas.holdem.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.glm.texas.holdem.game.interfaces.GameCallback;
import com.glm.texas.holdem.game.net.MasterNode;

/**
 * Class for launch the Game
 *
 * */
public class GameLauncher extends AndroidApplication implements GameCallback {
    private MasterNode mMasterNode;


    private TexasHoldemAdapter mTexasHoldemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTexasHoldemAdapter = new TexasHoldemAdapter(this);

        //Init the game
        mMasterNode = MasterNode.getInstance();
        mMasterNode.init(mTexasHoldemAdapter);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();


        initialize(mTexasHoldemAdapter, config);
    }

    @Override
    public void startActivity() {
        Intent intent = new Intent(this, AndroidLauncher.class);
        startActivity(intent);
    }
}
