package com.glm.texas.holdem.game.utils.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.utils.RearrangeUtils;

/**
 * Created by gianluca on 04/11/16.
 */

public class WaitGame extends Actor {
    public static Sound mp3RunningSound;
    private TextureAtlas textureAtlas;
    private Sprite mSprite;
    private TextureAtlas.AtlasRegion mRegion;
    private float xPos=0;
    private float yPos=0;
    private float mWidth;
    private float mHeight;
    private int currentFrame=1;
    private String currentAtlasKey="001";
    private float mElapsedTime = 0;

    public WaitGame(float width, float height){
        //textureAtlas = new TextureAtlas(Gdx.files.internal("data/ui/"+ RearrangeUtils.getResolutionAssets()+"/characters/runner.atlas"));
        textureAtlas = new TextureAtlas(Gdx.files.internal("data/ui/loading.atlas"));

        mRegion = textureAtlas.findRegion("001");
        mSprite = new Sprite(mRegion);

        mp3RunningSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/fx/backgroud_music.mp3"));

        mWidth  =   mRegion.getRegionWidth();//RearrangeUtils.getActorWidth(mRegion.getRegionWidth());
        mHeight =   mRegion.getRegionHeight();//RearrangeUtils.getActorHeight(mRegion.getRegionHeight());
        if(Const.DEBUG) Gdx.app.log(this.getClass().getCanonicalName(),"mWidth: "+mWidth+
            "mHeight"+mHeight+" width:"+width+" height:"+height);
        setWidth(mWidth);
        setHeight(mHeight);
        mSprite.setSize(mWidth,mHeight);
        mSprite.setPosition(0, 0);
        Timer.schedule(new Timer.Task() {
                           @Override
                           public void run() {
                               currentFrame++;
                               if (currentFrame > 23)
                                   currentFrame = 1;

                               // ATTENTION! String.format() doesnt work under GWT for god knows why...
                               currentAtlasKey = String.format("%03d", currentFrame);
                               //Gdx.app.log(Runner.class.getCanonicalName(), "current frame is: " + currentAtlasKey );
                               mSprite.setRegion(textureAtlas.findRegion(currentAtlasKey));
                           }
                       }
                , 0, 1 / 30.0f);
        //mp3RunningSound.play();
    }

    public void draw(Batch batch, float f)
    {
        //Delta Time
        mElapsedTime += Gdx.graphics.getDeltaTime();

        //batch.draw(mAnimation.getKeyFrame(mElapsedTime,true),xPos,yPos);
        mSprite.draw(batch);

    }
    public Sprite getmCavemanSprite() {
        return mSprite;
    }
}
