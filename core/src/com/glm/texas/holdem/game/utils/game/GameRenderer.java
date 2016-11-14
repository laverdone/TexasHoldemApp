package com.glm.texas.holdem.game.utils.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.utils.inputs.KeyProcessor;

/**
 * Created by gianluca on 07/08/14.
 */
public class GameRenderer {
    private GameTexasHoldem mGame;
    private Stage mStageGame;
    private SpriteBatch mGameBatch;
    private GameWorld mWorld;
    private OrthographicCamera mCameraGame;
    private ShapeRenderer shapeRenderer;

    //private BitmapFont mFont;
    private float mElapsedTime = 0;
    private float x;
    /**
     * set the avaibility on the Render at the end of load resource
     */
    private boolean isAvailble = false;

    //private KeyProcessor mInputProcessor;
    public GameRenderer(GameWorld world, GameTexasHoldem game) {
        mGame = game;
        mStageGame = mGame.mStageGame;
        mGameBatch = mGame.mGameBatch;
        mWorld = world;
        x = 0.0F;

        mCameraGame = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mCameraGame.position.x = Gdx.graphics.getWidth() / 2;
        mCameraGame.position.y = Gdx.graphics.getHeight() / 2;
        mCameraGame.update();

        if (Const.DEBUG) {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(mCameraGame.combined);
        }

/*        mFont = new BitmapFont(Gdx.files.internal("data/font/comic.fnt"), Gdx.files.internal("data/font/comic.png"), false);
        mFont.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        mFont.setScale(RearrangeUtils.getScaleFactor());*/

        //Set Input Processor
        //mInputProcessor =   new KeyProcessor();
        Gdx.input.setInputProcessor(mStageGame);

        /**init world inside WorldRenderer*/
        mWorld.setCamera(mCameraGame);
        //mWorld.setInputProcessor(mInputProcessor);
        mWorld.createWorld();

        mGameBatch.setProjectionMatrix(mCameraGame.combined);

        isAvailble = true;

    }

    /**
     * define is the game resource are loaded
     */
    public boolean isAvailble() {
        return isAvailble;
    }

    public void render() {
        //if(Const.DEBUG) Gdx.app.log(this.getClass().getCanonicalName(), "render GameRender");

        /*
         * 1. We draw a black background. This prevents flickering.
         */

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            if (Const.DEBUG)
                Gdx.app.log(this.getClass().getCanonicalName(), "Back Pressed do anything");
        }
        //Disegna e anima tutti gli sprites e background di gioco
        if (mGameBatch != null) {
            //Delta Time
            mElapsedTime += Gdx.graphics.getDeltaTime();


            //mGameBatch.begin();
            //mStageGame.draw();
            //mGameBatch.end();


            mWorld.getGround().render(mGameBatch);

            mWorld.getLevel().render(mGameBatch);

            mWorld.getmHub().render(mGameBatch);
            //mGameBatch.begin();
            //if(Const.DEBUG) mFont.draw(mGameBatch, "fps: "+Gdx.graphics.getFramesPerSecond(), 50F, 100F);
            //mGameBatch.end();

            /** mGameBatch.begin();
             Call the method for game logic
             mStageGame.act(mElapsedTime);
             mStageGame.draw();
             mGameBatch.end();**/
        }

        /**Usa la classe GameWorld per applicare la logica di gioco e verificare la collision detect*/
        mWorld.update(Gdx.graphics.getDeltaTime());
    }

    public void shuffleCard() {
        mWorld.getLevel().initMyCard();
    }

    public void openGame() {
        mWorld.getmHub().openGame();
    }

    public void firstBet() {
        mWorld.getmHub().firstBet();
    }
}
