// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.glm.texas.holdem.game.utils.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.Game;
import com.glm.texas.holdem.game.bean.Player;
import com.glm.texas.holdem.game.utils.RearrangeUtils;
import com.glm.texas.holdem.game.utils.actors.ButtonUI;
import com.glm.texas.holdem.game.utils.actors.Coin;
import com.glm.texas.holdem.game.utils.game.GameTexasHoldem;

/**
 * Contains the level games with distance, difficult ad so on
 *
 *
 * */
public class LayerHub
{
    private BitmapFont mFont;

    /**Game is On or Off*/
    private boolean mInGame=false;
    private float mCurrentTime=0;
    private double mAVGFrequency=0;

    private ButtonUI mBetButton;
    private ButtonUI mPlayButton;
    private ButtonUI mMinusButton;
    private ButtonUI mPlusButton;
    private ButtonUI mNoButton;
    private ButtonUI mSoundButton;
    private ButtonUI mNoSoundButton;

    private Preferences mPrefs;
    private GameTexasHoldem mGame;
    private float mWidth, mHeight;

    private int mMoneyToBet=0;

    private Coin mCoin;
    /**
     *
     * */
    public LayerHub(GameTexasHoldem game, float width, float height)
    {
        mWidth  = width;
        mHeight = height;
        mGame   = game;

        mPrefs = Gdx.app.getPreferences(Const.PREF_FILE);
        /**load font for game progress*/
        mFont = new BitmapFont(Gdx.files.internal("data/font/comic.fnt"),
                Gdx.files.internal("data/font/comic.png"), false);

        mFont.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        //mFont.setScale(RearrangeUtils.getScaleFactor());

        if(Const.DEBUG) {
            Gdx.app.log(this.getClass().getCanonicalName(),"Init Layer Hub");
        }

        initButtons();

    }
    /**
     * b_play
     * b_minus
     * b_no
     * b_nosound
     * b_plus
     * b_sound
     * */
    private void initButtons() {
        mPlayButton = new ButtonUI("b_play",0,0);
        //mPlayButton.setScale(Const.SCALE_FACTOR,Const.SCALE_FACTOR);
        mPlayButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mPlayButton.setPressed();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mPlayButton.setUnPressed();
                //TODO Sent Message To Server
                GameTexasHoldem.mCommunicationChannel.sendMessageToServer(3);
            }
        });



        mBetButton      = new ButtonUI("b_play",0,0);
        //mBetButton.setScale(Const.SCALE_FACTOR,Const.SCALE_FACTOR);
        mBetButton.setVisible(false);
        mBetButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mBetButton.setPressed();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mBetButton.setUnPressed();
                Game.getInstance().getDeck().setOpen(true);
                Player.getInstance().setMoney(Player.getInstance().getMoney()-mMoneyToBet);
                //TODO Sent Message To Server
                GameTexasHoldem.mCommunicationChannel.sendMessageToServer(2);
            }
        });

        mNoButton       = new ButtonUI("b_no",0,mPlayButton.getHeight()+Const.PADDING_Y);

        //mNoButton.setScale(Const.SCALE_FACTOR,Const.SCALE_FACTOR);
        //mNoButton.setVisible(false);
        mNoButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mNoButton.setPressed();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mNoButton.setUnPressed();
                Player.getInstance().setFold(true);
                //TODO Sent Message To Server
                GameTexasHoldem.mCommunicationChannel.sendMessageToServer(99);
            }
        });

        mMinusButton    = new ButtonUI("b_minus",0,mNoButton.getHeight()+mBetButton.getHeight()+Const.PADDING_Y);
        //mMinusButton.setScale(Const.SCALE_FACTOR,Const.SCALE_FACTOR);
        //mMinusButton.setVisible(false);
        mMinusButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mMinusButton.setPressed();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mMinusButton.setUnPressed();
                if( mMoneyToBet>1){
                    mMoneyToBet-=1;
                    //Player.getInstance().setMoney(Player.getInstance().getMoney()+1);
                }

                //TODO Sent Message To Server
                //mGame.mCommunicationChannel.sendMessageToServer(3);
                Game.getInstance().getDeck().setMoney(mMoneyToBet);
                Game.getInstance().getDeck().setCurrentBet(mMoneyToBet);

            }
        });

        mPlusButton     = new ButtonUI("b_plus",0,mMinusButton.getHeight()+mNoButton.getHeight()+mBetButton.getHeight()+Const.PADDING_Y);
        //mPlusButton.setScale(Const.SCALE_FACTOR,Const.SCALE_FACTOR);
        //mPlusButton.setVisible(false);
        mPlusButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mPlusButton.setPressed();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mPlusButton.setUnPressed();
                if(Player.getInstance().getMoney()>=mMoneyToBet) {
                    mMoneyToBet += 1;
                    //Player.getInstance().setMoney(Player.getInstance().getMoney()-mMoneyToBet);

                }
                //TODO Sent Message To Server
                //mGame.mCommunicationChannel.sendMessageToServer(3);
            }
        });

        mNoSoundButton = new ButtonUI("b_nosound",mWidth,mHeight);
        //mNoSoundButton.setScale(Const.SCALE_FACTOR,Const.SCALE_FACTOR);
        //mNoSoundButton.setVisible(false);
        mNoSoundButton.setPosition((mWidth- mNoSoundButton.getWidth()),(mHeight- mNoSoundButton.getHeight()));


        mSoundButton    = new ButtonUI("b_sound",(mWidth-mNoSoundButton.getWidth()),(mHeight- mNoSoundButton.getHeight()));
        //mSoundButton.setScale(Const.SCALE_FACTOR,Const.SCALE_FACTOR);
        //mSoundButton.setPosition((mWidth-mSoundButton.getWidth()),(mHeight- mSoundButton.getHeight()));
        mSoundButton.setVisible(true);

        mGame.mStageGame.addActor(mBetButton);

        mGame.mStageGame.addActor(mMinusButton);
        mGame.mStageGame.addActor(mNoButton);
        mGame.mStageGame.addActor(mSoundButton);
        mGame.mStageGame.addActor(mNoSoundButton);
        mGame.mStageGame.addActor(mPlusButton);

        mGame.mStageGame.addActor(mPlayButton);
        mGame.mStageGame.addActor(mPlayButton);
        mGame.mStageGame.addActor(mNoButton);

        mCoin = new Coin(mMoneyToBet);
    }

    public void render(SpriteBatch spritebatch)
    {
        spritebatch.begin();



        mBetButton.draw(spritebatch);
        mSoundButton.draw(spritebatch);
        mMinusButton.draw(spritebatch);
        mPlusButton.draw(spritebatch);
        mPlayButton.draw(spritebatch);
        mNoButton.draw(spritebatch);

        mCoin.draw(spritebatch);
        /**
         *  return Math.round((float)(i * Gdx.graphics.getWidth()) / Const.DEFAULT_WIDTH);
         * */
        if(Const.DEBUG)
            mFont.draw(spritebatch, "Game Status is: "+Game.getInstance().getGameStatus()+ "\n screen size is\n mWidth: "+mWidth+
                            "\n mHeight: "+mHeight,
                    RearrangeUtils.getOffsetX(580F),
                    RearrangeUtils.getOffsetY(520F));

        mFont.draw(spritebatch,mPrefs.getString("personName")+" Money "+Player.getInstance().getMoney(),
                RearrangeUtils.getOffsetX(40F),
                RearrangeUtils.getOffsetY(720F));

        mFont.draw(spritebatch,"dish "+String.valueOf(mMoneyToBet),
                RearrangeUtils.getOffsetX(150F),
                RearrangeUtils.getOffsetY(500F));

        spritebatch.end();

    }

    public void updateGameTime(float currentTime) {
        mCurrentTime=currentTime;
    }

    public void updateGameFrequency(double avgFrequency) {
        mAVGFrequency=avgFrequency;
    }

    /**
     * game is open but only for view cards, hide all button
     * */
    public void openGame() {
        mBetButton.setVisible(false);
        mPlayButton.setVisible(false);
        mMinusButton.setVisible(false);
        mPlusButton.setVisible(false);
        mNoButton.setVisible(false);
    }

    /**
     * game wait for first bet, show bet button hide playbutton
     *
     * TODO implements bet button
     * */
    public void firstBet() {
        if(Player.getInstance().isCanOpen()) {
            mBetButton.setVisible(true);
            mMinusButton.setVisible(true);
            mPlusButton.setVisible(true);
            mNoButton.setVisible(true);
            mPlayButton.setVisible(false);
        }
    }
}
