package com.glm.texas.holdem.game.utils.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.glm.texas.holdem.game.utils.RearrangeUtils;
import com.glm.texas.holdem.game.utils.game.GameTexasHoldem;

import java.util.Iterator;

/**
 * Created by gianluca on 25/07/14.
 */
public class AlertUI extends Actor {

    public AlertButtonUI mOKButton;
    public AlertButtonUI mCancelButton;
    private Texture mTextureMain;
    private Sprite mSprite;
    private float xPos=0;
    private float yPos=0;
    private float mWidth;
    private float mHeight;
    private BitmapFont mFont;
    private String mTitle;
    private Label mLabel;
    private GameTexasHoldem mGame;
    private float mDuration=2f;

    public AlertUI(GameTexasHoldem game, String title){
        mGame=game;
        mTextureMain = new Texture(Gdx.files.internal("data/ui/"+ RearrangeUtils.getResolutionAssets()+"/gui/alert.png"));



        mSprite = new Sprite(mTextureMain);

        mTitle  = title;
        mFont = new BitmapFont(Gdx.files.internal("data/font/comic.fnt"),
                Gdx.files.internal("data/font/comic.png"), false);
        mFont.setColor(1,1,1,1);
        //mFont.setScale(RearrangeUtils.getScaleFactor());

        mLabel = new Label(mTitle, new Label.LabelStyle(mFont,new Color(1,1,1,1)));
        mLabel.setWrap(true);


        mWidth  =   RearrangeUtils.getActorWidth(mTextureMain.getWidth());
        mHeight =   RearrangeUtils.getActorHeight(mTextureMain.getHeight());
        xPos    =   (Gdx.graphics.getWidth()/2)-(mWidth/2);// RearrangeUtils.getOffsetX((Gdx.graphics.getWidth()/2)-(mWidth/2));
        yPos    =   (Gdx.graphics.getHeight()/2)-(mHeight/2);//RearrangeUtils.getOffsetY((Gdx.graphics.getHeight()/2)-(mHeight/2));

        mOKButton = new AlertButtonUI("Ok",xPos*2, yPos);
        mCancelButton=new AlertButtonUI("Cancel",xPos+(xPos/2), yPos);



        setPosition(xPos, (Gdx.graphics.getHeight()+200));
        mSprite.setPosition(xPos, yPos);
        mSprite.setSize(mWidth, mHeight);

        mLabel.setPosition(xPos+50, yPos+(mHeight/2)-50);
    }

    public void init(){
        setPosition(xPos, (Gdx.graphics.getHeight()+200));
        mOKButton.setPosition(xPos*2, (Gdx.graphics.getHeight()+200));
        mCancelButton.setPosition(xPos+(xPos/2), (Gdx.graphics.getHeight()+200));
        mLabel.setPosition(xPos+50, yPos+(mHeight/2)-50);

        clearActions();
        addAction(Actions.moveTo(xPos,yPos,mDuration,Interpolation.fade));

        mLabel.addAction(Actions.moveTo(xPos+50, yPos+(mHeight/2)-50, mDuration,Interpolation.fade));
        mOKButton.addAction(Actions.moveTo(xPos*2, yPos,mDuration,Interpolation.fade));
        mCancelButton.addAction(Actions.moveTo(xPos+(xPos/2), yPos,mDuration,Interpolation.fade));
    }

    @Override
    public boolean remove() {
        mOKButton.remove();
        mCancelButton.remove();
        mLabel.remove();
        return super.remove();
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {


        batch.draw(mTextureMain, getX(), getY());
        mLabel.addAction(Actions.moveTo(xPos+50, yPos+(mHeight/2)-50, mDuration,Interpolation.fade));
        mLabel.draw(batch,parentAlpha);
        //if( getActions().size==0){
            //Gdx.app.log(this.getClass().getCanonicalName()," No MORE Actions "+getActions().size);
        //}
        //mSprite.draw(batch);


        //mOKButton.draw(batch,parentAlpha);
        //mCancelButton.draw(batch,parentAlpha);

        //addAction(Actions.moveTo(xPos, yPos, 21.7f, Interpolation.bounceIn));
        //Actions.sequence();
        //mOKButton.setBounds(mOKButton.xButtonPos,mOKButton.yButtonPos,
        //                    RearrangeUtils.getActorWidth(mOKButton.mButtonRegion.getRegionWidth()),
        //                            RearrangeUtils.getActorHeight(mOKButton.mButtonRegion.getRegionHeight()));
        //mOkSprite.draw(batch);
        //mCancelSprite.draw(batch);
        // mFont.draw(batch,mTitle,xPos+80, Const.DEFAULT_HEIGHT-yPos);

        //Ok Button
        //setBounds(xPos*2, yPos, RearrangeUtils.getActorWidth(mOkRegion.getRegionWidth()),
        //        RearrangeUtils.getActorWidth(mOkRegion.getRegionHeight()));
        //Cancel Button
        //setBounds(xPos+(xPos/2), yPos, RearrangeUtils.getActorWidth(mCancelRegion.getRegionWidth()),
        //        RearrangeUtils.getActorWidth(mCancelRegion.getRegionHeight()));

    }

    /**
     * stato unpressed
     * */
    public void setUnPressed(){

    }

    /**
     * stato pressed
     * */
    public void setPressed(){
        //Gdx.input.vibrate(50);
        //mGame.oggSound.play();
    }

    @Override
    public void act(float delta){
        for(Iterator<Action> iter = this.getActions().iterator(); iter.hasNext();){
            iter.next().act(delta);
        }
    }

    class AlertButtonUI extends Actor{
        public TextureAtlas.AtlasRegion mButtonRegion;
        //private Sprite mCancelSprite;
        public float xButtonPos;
        public float yButtonPos;
        private TextureAtlas textureAtlas;
        //private TextureAtlas.AtlasRegion mCancelRegion;
        private Sprite mButtonSprite;
        public AlertButtonUI(String type,float x,float y){
            xButtonPos=x;
            yButtonPos=y;

            textureAtlas = new TextureAtlas(Gdx.files.internal("data/ui/"+RearrangeUtils.getResolutionAssets()+"/gui/ok_cancel.atlas"));

            mButtonRegion = textureAtlas.findRegion(type);
            //mCancelRegion = textureAtlas.findRegion("Cancel");

            mButtonSprite = new Sprite(mButtonRegion);
            //mCancelSprite = new Sprite(mCancelRegion);
            mButtonSprite.setPosition(xButtonPos, yButtonPos);

            //mButtonSprite.setPosition(xPos*2, yPos);
            //mCancelSprite.setPosition(xPos+(xPos/2), yPos);

            mButtonSprite.setSize(RearrangeUtils.getActorWidth(mButtonRegion.getRegionWidth()),
                    RearrangeUtils.getActorWidth(mButtonRegion.getRegionHeight()));
            //mCancelSprite.setSize(RearrangeUtils.getActorWidth(mCancelRegion.getRegionWidth()),
            //        RearrangeUtils.getActorWidth(mCancelRegion.getRegionHeight()));
            setPosition(xButtonPos, yButtonPos);
            mButtonSprite.setPosition(xButtonPos, yButtonPos);
        }
        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(mButtonRegion,getX(),getY());

            //mButtonSprite.draw(batch);


            //Ok Button
            setBounds(xButtonPos, yButtonPos, RearrangeUtils.getActorWidth(mButtonRegion.getRegionWidth()),
                    RearrangeUtils.getActorWidth(mButtonRegion.getRegionHeight()));
            //Cancel Button
        }

        @Override
        public void act(float delta){
            for(Iterator<Action> iter = this.getActions().iterator(); iter.hasNext();){
                iter.next().act(delta);
            }
        }
    }
}
