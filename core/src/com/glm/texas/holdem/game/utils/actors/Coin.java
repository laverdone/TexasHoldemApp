package com.glm.texas.holdem.game.utils.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.StringBuilder;
import com.glm.texas.holdem.game.bean.Player;
import com.glm.texas.holdem.game.utils.RearrangeUtils;

/**
 * Created by gianluca on 04/11/16.
 */

public class Coin extends Actor {
    private Sprite mSprite;
    private BitmapFont mFont;
    private Texture mCoin;
    private String mCoinValue = "5";

    public Coin(int coinValue) {
        mCoinValue = String.valueOf(coinValue);
        /**load font for game progress*/
        mFont = new BitmapFont(Gdx.files.internal("data/font/comic.fnt"),
                Gdx.files.internal("data/font/comic.png"), false);
        mCoin = new Texture(Gdx.files.internal("data/coins/green.png"));

        mFont.setColor(Color.BLACK);

        mSprite = new Sprite(mCoin);
        setHeight(mCoin.getHeight());
        setWidth(mCoin.getWidth());
        mSprite.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getWidth() / 2);
        setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getWidth() / 2);

    }

    public void draw(Batch batch) {
        //batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

        mSprite.draw(batch);

        mFont.draw(batch, "COIN VALUES IS" + mCoinValue,
                (Gdx.graphics.getWidth() / 2) - mCoin.getWidth(), (Gdx.graphics.getWidth() / 2) + mCoin.getHeight());

    }
}
