package com.glm.texas.holdem.game.utils.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.utils.actors.GenericImage;
import com.glm.texas.holdem.game.utils.actors.WaitGame;
import com.glm.texas.holdem.game.utils.game.GameTexasHoldem;
import com.glm.texas.holdem.game.utils.inputs.KeyProcessor;

public class WaitScreen implements Screen {


    private GameTexasHoldem mGame; // Note it's "GameTexasHoldem" not "Game"
    private WaitGame mWaitGame;
    private GenericImage mBackgroundGame;
    private SpriteBatch mMainBatch;
    private Stage mStageGame;
    private KeyProcessor mInputProcessor;

    // constructor to keep a reference to the main Game class
    public WaitScreen(GameTexasHoldem game) {
        mGame = game;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        mStageGame = mGame.mStageGame;
        mMainBatch = mGame.mGameBatch;

        mWaitGame = new WaitGame(mStageGame.getWidth(), mStageGame.getHeight());
        mBackgroundGame = new GenericImage("data/background/green.jpg", 0, 0, mStageGame.getWidth(), mStageGame.getHeight(), true);

        //mInputProcessor =   new KeyProcessor();
        //Gdx.input.setInputProcessor(mInputProcessor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (mMainBatch != null && mStageGame != null) {
            mMainBatch.begin();
            mStageGame.act(Gdx.graphics.getDeltaTime());
            mStageGame.draw();
            mMainBatch.end();
        }
    }


    @Override
    public void resize(int width, int height) {
        if (Const.DEBUG)
            Gdx.app.log(this.getClass().getCanonicalName(), "width:" + width + " height:" + height);
    }


    @Override
    public void show() {
        mStageGame.addActor(mBackgroundGame);
        mStageGame.addActor(mWaitGame);
    }


    @Override
    public void hide() {
        // called when current screen changes from this to a different screen
        this.dispose();
        new AssetManager().dispose();
        mWaitGame.remove();
        mBackgroundGame.remove();
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {
        // never called automatically
//        dispose();
    }
}