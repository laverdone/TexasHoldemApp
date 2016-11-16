// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.glm.texas.holdem.game.utils.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glm.texas.holdem.game.utils.actors.GenericImage;
import com.glm.texas.holdem.game.utils.actors.WaitGame;
import com.glm.texas.holdem.game.utils.game.GameRenderer;
import com.glm.texas.holdem.game.utils.game.GameTexasHoldem;
import com.glm.texas.holdem.game.utils.game.GameWorld;

public class GameScreen
        implements Screen {

    /*private GenericImage mBackground;
    private OrthographicCamera mCameraGame;
    private Caveman mCaveMan1;
    private Caveman mCaveMan2;
    private Caveman mCaveMan3;
    private ArrayList mClouds;
    private BitmapFont mFont;

    private SpriteBatch mGameBatch;
    private TextureRegion mGroundRegion;
    private ParallaxSky mSky;
    private ParallaxGround mGround;
    private Stage mStageGame;
    private float x;
    private float xPos;
    private float mElapsedTime = 0;*/

    private GameTexasHoldem mGame;
    /**
     * Objects for Game Logic and Draw
     */
    private GameWorld mGameWorld;
    private GameRenderer mGameRenderer;
    private Stage mStageGame;


    public GameScreen(GameTexasHoldem game) {
        mGame = game;
        mStageGame = game.mStageGame;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
    }

    public void dispose() {
        dispose();
    }

    public void hide() {
    }

    public void pause() {
    }

    public void render(float f) {

        if (mGameWorld == null && mGameRenderer == null) {
            /**create main object*/
            mGameWorld = new GameWorld(mGame);
            mGameRenderer = new GameRenderer(mGameWorld, mGame);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mGame.mStageGame.act(Gdx.graphics.getDeltaTime());
        mGame.mStageGame.draw();

        if (mGameRenderer != null && mGameRenderer.isAvailble()) mGameRenderer.render();


    }

    public void resize(int i, int j) {
    }

    public void resume() {
    }

    public void show() {
        /**create main object*/
        mGameWorld = new GameWorld(mGame);
        mGameRenderer = new GameRenderer(mGameWorld, mGame);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //Init the world assets onto another thread
                /**create main object*/
                mGameWorld = new GameWorld(mGame);
                mGameRenderer = new GameRenderer(mGameWorld, mGame);
            }
        });
    }

    public void shuffleCard() {
        mGameRenderer.shuffleCard();
    }

    public void openGame() {
        mGameRenderer.openGame();
    }

    public void firstBet() {
        mGameRenderer.firstBet();
    }

    public void changeCard() {
        mGameRenderer.changeCard();
    }
}
