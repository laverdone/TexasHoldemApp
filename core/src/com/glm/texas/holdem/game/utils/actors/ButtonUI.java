package com.glm.texas.holdem.game.utils.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glm.texas.holdem.game.utils.RearrangeUtils;
import com.glm.texas.holdem.game.utils.game.GameTexasHoldem;

import java.util.Iterator;

/**
 * Created by gianluca on 24/07/14.
 */
public class ButtonUI extends Actor {
    private TextureAtlas textureAtlas;
    private Sprite mSprite;
    private TextureAtlas.AtlasRegion mRegion;
    private float xPos = 0;
    private float yPos = 0;
    private float mWidth;
    private float mHeight;
    private String mType;

    //costruttore
    public ButtonUI(String type, float xpos, float ypos) {
        mType = type;
        //textureAtlas = new TextureAtlas(Gdx.files.internal("data/ui/buttons/"+type+".atlas"));
        textureAtlas = new TextureAtlas(Gdx.files.internal("data/ui/buttons/b_buttons.atlas"));


        mType = type;
        mRegion = textureAtlas.findRegion(mType + "_unsel");
        mSprite = new Sprite(mRegion);

        xPos = xpos;
        yPos = ypos;
        mWidth = mRegion.getRegionWidth();
        mHeight = mRegion.getRegionHeight();


        //btnTexture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
        setWidth(mWidth);
        setHeight(mHeight);
        mSprite.setPosition(xPos, yPos);
        mSprite.setSize(mWidth, mHeight);
    }

    public void draw(Batch batch) {
        if (isVisible()) {
            mSprite.draw(batch);

            //batch.draw(btnTexture,100,50);
            setBounds(xPos, yPos, mWidth, mHeight);
        }
    }

    /**
     * stato unpressed
     */
    public void setUnPressed() {
        mSprite.setRegion(textureAtlas.findRegion(mType + "_unsel"));
    }

    /**
     * stato pressed
     */
    public void setPressed() {
        mSprite.setRegion(textureAtlas.findRegion(mType + "_sel"));
    }

    /**
     * stato pressed
     */
    public void setDisabled() {
        mSprite.setRegion(textureAtlas.findRegion(mType + "_dis"));
    }

    @Override
    public void act(float delta) {
        for (Iterator<Action> iter = this.getActions().iterator(); iter.hasNext(); ) {
            iter.next().act(delta);
        }
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        mSprite.setScale(scaleX, scaleY);
        super.setScale(scaleX, scaleY);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}
