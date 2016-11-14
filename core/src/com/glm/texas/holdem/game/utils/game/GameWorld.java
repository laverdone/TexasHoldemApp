package com.glm.texas.holdem.game.utils.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glm.texas.holdem.game.*;
import com.glm.texas.holdem.game.utils.inputs.KeyProcessor;
import com.glm.texas.holdem.game.utils.layers.LayerBackground;
import com.glm.texas.holdem.game.utils.layers.LayerHub;
import com.glm.texas.holdem.game.utils.layers.LayerLevel;

/**
 * Created by gianluca on 07/08/14.
 * si occupa di caricare tutti i layer di gioco e demanda il disegno alla GameRender
 * gestisce anche la collision detect
 *
 *
 */
public class GameWorld {
    private LayerBackground mGround;
    private LayerLevel mLevel;
    private LayerHub mHub;

    private OrthographicCamera mCameraGame;

    private GameTexasHoldem mGame;
    private SpriteBatch mGameBatch;
    //private TextureRegion mGroundRegion;

    private Stage mStageGame;
    //private ArrayList<Caveman> mCaveMen = new ArrayList<Caveman>();

    //Object for the input touch
    private KeyProcessor mInputProcessor;

    private Thread mGameThread;
    /**Time when start the Game*/
    private float mZeroWorldTime=0f;
    /**Current time Game*/
    private float mCurrentTime=0f;
    private int mGameState=Const.GAME_STOP;
    public GameWorld(GameTexasHoldem game){
        mGame       = game;
        mGameBatch  = mGame.mGameBatch;
        mStageGame  = mGame.mStageGame;

    }

    public void createWorld(){
        mGround     = new LayerBackground(mStageGame.getWidth(),mStageGame.getHeight());
        mLevel      = new LayerLevel(mGame, mStageGame.getWidth(),mStageGame.getHeight());
        mHub        = new LayerHub(mGame, mStageGame.getWidth(),mStageGame.getHeight());
    }

    public void update(float delta) {
        //Find collision detect
        if(mZeroWorldTime==0f) mZeroWorldTime=delta;
        mCurrentTime+=delta-mZeroWorldTime;
        mHub.updateGameTime(mCurrentTime);
//        mHub.updateGameFrequency(mInputProcessor.getAvgFrequency());
    }

    /**
     * Ritorna l'immagine di background
     *
     * @return GenericImage background
     *
     *
    public GenericImage getBackground() {
        return mBackground;
    }
    */


    public void setCamera(OrthographicCamera camera) {
        mCameraGame = camera;
    }

    public LayerBackground getGround() {
        return mGround;
    }


    public LayerLevel getLevel() {
        return mLevel;
    }


    /**return if the game is running*/
    public boolean isGaming() {
        return mGameState != Const.GAME_END;
    }

    public LayerHub getmHub() {
        return mHub;
    }

}
