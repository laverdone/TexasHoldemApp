// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.glm.texas.holdem.game.utils.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glm.texas.holdem.game.utils.RearrangeUtils;

public class GenericImage extends Actor {

    private Texture TextureMain;
    private TextureRegion imgTextureRegion;
    private float mHeight;
    //private Sprite mSprite;
    private float mWidth;
    private float xPos;
    private float yPos;

    /**
     * Image
     */
    public GenericImage(String image, int xpos, int ypos, float width, float height) {
        xPos = xpos;
        yPos = ypos;
        TextureMain = new Texture(Gdx.files.internal(image));
        TextureMain.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        //mSprite = new Sprite(TextureMain);

        mWidth = width;
        mHeight = height;
        setWidth(mWidth);
        setHeight(mHeight);
        setColor(Color.BLUE);
        //mSprite.setPosition(xPos, yPos);
        //mSprite.setSize(mWidth, mHeight);
    }

    public GenericImage(String image, int xpos, int ypos, float width, float height, boolean isrepeat) {
        xPos = xpos;
        yPos = ypos;
        TextureMain = new Texture(Gdx.files.internal(image));
        if (isrepeat) {
            TextureMain.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
            imgTextureRegion = new TextureRegion(TextureMain);
            imgTextureRegion.setRegion(0, 0, width, height);
        } else {
            imgTextureRegion = new TextureRegion(TextureMain);
            imgTextureRegion.setRegion(0, 0, TextureMain.getWidth(), TextureMain.getHeight());
        }
        //mSprite = new Sprite(TextureMain);


        setHeight(height);
        setWidth(width);
        //setColor(Color.BLUE);

        /**mSprite.setPosition(xPos, yPos);
         mSprite.setSize(width,height);
         mSprite.setColor(Color.BLUE);*/
    }

    public void draw(Batch batch, float f) {
        batch.draw(imgTextureRegion, xPos, yPos);
        //mSprite.draw(batch);
    }

}
