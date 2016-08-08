package com.glm.texas.holdem.game.bean;

import com.glm.texas.holdem.game.Game;

/**
 * Created by gianluca on 12/07/16.
 */
public class Message {
    private Game mCurrentGame=null;

    private Player mPlayer=null;

    private String mMessage="";

    public Game getCurrentGame() {
        return mCurrentGame;
    }

    public void setCurrentGame(Game mCurrentGame) {
        this.mCurrentGame = mCurrentGame;
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public void setPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
