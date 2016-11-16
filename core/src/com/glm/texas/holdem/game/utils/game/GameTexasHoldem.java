package com.glm.texas.holdem.game.utils.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.interfaces.GameCallback;
import com.glm.texas.holdem.game.net.node.CommunicationTunnel;
import com.glm.texas.holdem.game.utils.screens.GameScreen;
import com.glm.texas.holdem.game.utils.screens.WaitScreen;


/**
 * Gestione completa della navigazione tra Screen di Gioco
 */
public class GameTexasHoldem extends Game {

    public static Sound oggSound;
    public static CommunicationTunnel mCommunicationChannel = null;
    public GameCallback mGameCallback;
    /**
     * Identify the games Status
     * -1=No connection to Server
     * 0=initial timeout "NO CARD AVAIABLE"
     * 1=start the game "SHUFFLING CARD"
     * 2=first bet
     * 3=turn over players
     * 4=final bet
     * 5=find winner
     *
     * */

    public SpriteBatch mGameBatch;
    public Stage mStageGame;
    /**
     * Definizione delle finestre di gioco
     */
    public WaitScreen mWaitScreen;
    public GameScreen mGameScreen;
    private float mElapsedTime = 0;

    public GameTexasHoldem(CommunicationTunnel communicationChannel, GameCallback gameLauncher) {
        mCommunicationChannel=communicationChannel;
        mGameCallback=gameLauncher;
    }


    @Override
    public void create() {
        mGameBatch = new SpriteBatch();
        mStageGame = new Stage(new ScreenViewport());
        oggSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg"));

        //init of screens games
        mWaitScreen   = new WaitScreen(this);
        mGameScreen   = new GameScreen(this);


        Gdx.input.setInputProcessor(mStageGame);

        setScreen(mWaitScreen);
    }

    @Override
    public void resize(int width, int height) {
        mStageGame.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        //Delta Time
        mElapsedTime += Gdx.graphics.getDeltaTime();



        if( com.glm.texas.holdem.game.Game.getInstance().getGameStatus()==0){
            mWaitScreen.render(mElapsedTime);
            if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
                if(mCommunicationChannel!=null) mCommunicationChannel.stopCommunicationChannel();
                Gdx.app.exit();
                mGameCallback.startActivity();
            }
        }else if( com.glm.texas.holdem.game.Game.getInstance().getGameStatus()==-10000){
            if(mCommunicationChannel!=null) mCommunicationChannel.stopCommunicationChannel();
            Gdx.app.exit();
            //Todo Server is Down or not reatchable back to home screen
            mGameCallback.startActivity();
        }else if( com.glm.texas.holdem.game.Game.getInstance().getGameStatus()==1){
            //Open Game Shufflig cards
            mGameScreen.render(mElapsedTime);
            mGameScreen.openGame();
        }else if( com.glm.texas.holdem.game.Game.getInstance().getGameStatus()==2){
            //first bet
            mGameScreen.render(mElapsedTime);
            mGameScreen.firstBet();
        }else if( com.glm.texas.holdem.game.Game.getInstance().getGameStatus()==3){
            //turn card
            mGameScreen.render(mElapsedTime);
            mGameScreen.changeCard();
        }else if( com.glm.texas.holdem.game.Game.getInstance().getGameStatus()==4){
            //final bet
            mGameScreen.render(mElapsedTime);
            //mGameScreen.shuffleCard();
        }else{
            mGameScreen.render(mElapsedTime);
        }
        //if(Const.DEBUG) Gdx.app.log(this.getClass().getCanonicalName(),"Game On Line Status: "+mOnLineGameStatus);
    }

    @Override
    public void dispose() {
        oggSound.dispose();
        mStageGame.dispose();

    }


    public com.glm.texas.holdem.game.Game getGame(){
        return com.glm.texas.holdem.game.Game.getInstance();
    }
}
