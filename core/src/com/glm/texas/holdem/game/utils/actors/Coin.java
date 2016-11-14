package com.glm.texas.holdem.game.utils.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

        mFont.setColor(0.0F, 0.0F, 0.0F, 0.0F);

        mSprite = new Sprite(mCoin);
        setHeight(mCoin.getHeight());
        setWidth(mCoin.getWidth());
        mSprite.setPosition(300, 300);
    }

    public void draw(Batch batch) {

        mSprite.draw(batch);
        mFont.draw(batch, mCoinValue,
                500,
                500);
    }
}
