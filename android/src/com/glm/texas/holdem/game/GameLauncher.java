package com.glm.texas.holdem.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.glm.texas.holdem.game.TexasHoldemGame;
import com.glm.texas.holdem.game.net.MasterNode;

/**
 * Class for launch the Game
 *
 * */
public class GameLauncher extends AndroidApplication {
    private MasterNode mMasterNode;
    private Game mGame;

    private TexasHoldemGame mTexasHoldemGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTexasHoldemGame = new TexasHoldemGame();


        //Init the game
        mMasterNode = new MasterNode(mTexasHoldemGame);
        mMasterNode.init(mGame);
        try {
            //TODO
            Thread.sleep(5000);
            Log.d(this.getClass().getCanonicalName(),"Wait 5Sec for init game");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(mMasterNode.getGame()==null){
            Log.d(this.getClass().getCanonicalName(),"Game is null Server is Down!?");
            //Back to Home Game TODO MESSAGE TO USER
            startActivity(new Intent(getApplicationContext(), AndroidLauncher.class));
            finish();
        }else{
            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
            mGame=mMasterNode.getGame();
            mTexasHoldemGame.setGame(mGame);
            initialize(mTexasHoldemGame, config);
        }



    }
}
