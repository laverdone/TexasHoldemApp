package com.glm.texas.holdem.game.utils.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.glm.texas.holdem.game.utils.actors.GenericImage;
import com.glm.texas.holdem.game.utils.game.GameWorld;

import java.util.ArrayList;
import java.util.List;

/**
 * impacchetta più layer e li muove a velocità diverse
 * per simulare il parallax scrolling
 * <p>
 * Created by gianluca on 29/07/14.
 */
public class LayerBackground {
    private float mWidth, mHeight;
    private GenericImage mBackgroundGame;

    public LayerBackground(GameWorld gameWorld, float width, float height) {
        mWidth = width;
        mHeight = height;
        mBackgroundGame = new GenericImage("data/background/green.jpg", 0, 0, mWidth, mHeight, true);

    }

    public void render(SpriteBatch spritebatch) {
        spritebatch.begin();
        mBackgroundGame.draw(spritebatch, Gdx.graphics.getDeltaTime());
        spritebatch.end();
    }


    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }


}
