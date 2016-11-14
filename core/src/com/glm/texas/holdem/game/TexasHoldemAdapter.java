package com.glm.texas.holdem.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.glm.texas.holdem.game.interfaces.GameCallback;
import com.glm.texas.holdem.game.net.node.CommunicationTunnel;
import com.glm.texas.holdem.game.utils.game.GameTexasHoldem;

/**
 * Classe di inizializzazione del gioco
 */
public class TexasHoldemAdapter extends ApplicationAdapter {
    public CommunicationTunnel mCommunicationChannel = null;

    /**
     * Identify the games Status
     * -1=No connection to Server
     * 0=initial timeout "NO CARD AVAIABLE"
     * 1=start the game "SHUFFLING CARD"
     * 2=first bet
     * 3=turn over players
     * 4=final bet
     * 5=find winner
     */
    private int mGameStatus = 0;

    private boolean isJustTouched = false;

    private GameTexasHoldem mGameTexasHoldem;
    private GameCallback mGameLauncher;

    public TexasHoldemAdapter(GameCallback gameLauncher) {
        mGameLauncher = gameLauncher;
    }


    @Override
    public void create() {

    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (mGameTexasHoldem == null) {
            mGameTexasHoldem = new GameTexasHoldem(mCommunicationChannel, mGameLauncher);
            mGameTexasHoldem.create();
        } else {
            mGameTexasHoldem.render();
        }

        /**
         * Main Loop get game status
         *
         *
         if(mGameStatus==-1){
         //No server connection, message to user to back
         }else if(mGameStatus==0){
         //Initial timeout, show waiting message

         }else if(mGameStatus==1){
         //start the game "SHUFFLING CARD"

         }else if(mGameStatus==2){
         //first bet

         }else if(mGameStatus==3){
         //turn over players

         }else if(mGameStatus==4){
         //final bet

         }else if(mGameStatus==4){
         //find winner
         }else{

         }*/
    }

    public void setOnLineGameStatus(int onLineStatus) {
        mGameStatus = onLineStatus;
    }

    public void setCommunicationChannel(CommunicationTunnel communicationChannel) {
        mCommunicationChannel = communicationChannel;
    }
}
