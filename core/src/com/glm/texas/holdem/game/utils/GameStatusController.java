package com.glm.texas.holdem.game.utils;

import com.glm.texas.holdem.game.Game;
import com.glm.texas.holdem.game.TexasHoldemAdapter;

/**
 * Created by gianluca on 04/11/16.
 */

public class GameStatusController implements Runnable {
    /**
     * Oggetto Thread utilizzato per la gestione dei client connessi
     */
    private Thread mMyThread;
    private boolean isRun = true;
    private TexasHoldemAdapter mTexasHoldemAdapter;
    private int mOnLineStatus = -2;
    private int mPreOnLineStatus = -3;

    public GameStatusController(TexasHoldemAdapter texasHoldemAdapter) {
        mTexasHoldemAdapter = texasHoldemAdapter;

        mMyThread = new Thread(this, "GameStatusController");
        mMyThread.start();
    }

    @Override
    public void run() {
        while (isRun) {
            if (mOnLineStatus == -2) {
                mTexasHoldemAdapter.setOnLineGameStatus(Game.getInstance().getGameStatus());
                mPreOnLineStatus = mOnLineStatus;
            } else if (mOnLineStatus != mPreOnLineStatus) {
                mTexasHoldemAdapter.setOnLineGameStatus(Game.getInstance().getGameStatus());
                mPreOnLineStatus = mOnLineStatus;
            }
        }
    }

    public void stopController() {
        isRun = false;
    }

}
