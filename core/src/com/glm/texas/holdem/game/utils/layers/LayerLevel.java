// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.glm.texas.holdem.game.utils.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.Game;
import com.glm.texas.holdem.game.utils.actors.ButtonUI;
import com.glm.texas.holdem.game.utils.actors.Card;
import com.glm.texas.holdem.game.utils.game.GameTexasHoldem;
import com.glm.texas.holdem.game.utils.game.GameWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


/**
 * Contains the level games with distance, difficult ad so on
 */
public class LayerLevel {

    private Vector<Card> mMyCards = new Vector<Card>();

    private float mWidth, mHeight;
    private BitmapFont mFont;

    /**
     * Delta Time
     */
    private float mDeltaTime = 0.0f;

    private Game mOnLineGame;

    private GameTexasHoldem mGame;

    /**
     *
     * */
    public LayerLevel(GameWorld gameWorld, GameTexasHoldem game, float width, float height) {
        mGame = game;
        mOnLineGame = Game.getInstance();
        mWidth = width;
        mHeight = height;

        /**load font for game progress*/
        mFont = new BitmapFont(Gdx.files.internal("data/font/comic.fnt"),
                Gdx.files.internal("data/font/comic.png"), false);
        mFont.setColor(1, 1, 1, 1);
        //mFont.setScale(0.4f);

        initMyCard();

    }


    /**
     * Get may card from Server Game and draw
     */
    public void initMyCard() {

        while (mOnLineGame == null
                || mOnLineGame.getGamePlayer() == null
                || mOnLineGame.getGamePlayer().getHand() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (mOnLineGame != null
                && mOnLineGame.getGamePlayer() != null
                && mOnLineGame.getGamePlayer().getHand() != null) {
            HashMap<Integer, com.glm.texas.holdem.game.bean.Card> mCards = mOnLineGame.getGamePlayer().getHand().displayAll();

            for (Map.Entry<Integer, com.glm.texas.holdem.game.bean.Card> entry : mCards.entrySet()) {
                Integer key = entry.getKey();
                com.glm.texas.holdem.game.bean.Card value = entry.getValue();

                mMyCards.add(new Card(value));
                //mMyCards.add(new Sprite(new Texture("Cards/"+value.getCardRank()+"_"+value.getmNaturalSuit()+".png")));

            }
        }

        if (mMyCards.size() < 5) return;

        mMyCards.get(0).setPosition(mWidth / Const.MY_CARD_OFFSET, 0);
        mMyCards.get(0).setSize(mMyCards.get(0).getWidth() / 2, mMyCards.get(0).getHeight() / 2);
        mMyCards.get(0).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Game.getInstance().getGameStatus() != 3) return;
                Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mMyCards.get(0).setHold(!mMyCards.get(0).getHold());
                if (mMyCards.get(0).getHold()) {
                    mMyCards.get(0).addAction(Actions.moveTo(mMyCards.get(0).getX(), mMyCards.get(0).getY() - (mWidth / 2), Const.DURATION));
                } else {
                    mMyCards.get(0).addAction(Actions.moveTo(mMyCards.get(0).getX(), mMyCards.get(0).getY() + (mWidth / 2), Const.DURATION));
                }

                if (Const.DEBUG)
                    Gdx.app.log(this.getClass().getCanonicalName(), "card1 set to change " + !mMyCards.get(0).getHold());


            }
        });
        //mCard1.rotate90(true);
        //mCard2 = new Sprite(new Texture("Cards/120_clubs.png"));
        mMyCards.get(1).setPosition((mWidth / Const.MY_CARD_OFFSET) + mMyCards.get(0).getWidth() / 2, 0);
        mMyCards.get(1).setSize(mMyCards.get(1).getWidth() / 2, mMyCards.get(1).getHeight() / 2);
        mMyCards.get(1).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Game.getInstance().getGameStatus() != 3) return;
                Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mMyCards.get(1).setHold(!mMyCards.get(1).getHold());
                if (mMyCards.get(1).getHold()) {
                    mMyCards.get(1).addAction(Actions.moveTo(mMyCards.get(1).getX(), mMyCards.get(1).getY() - (mWidth / 2), Const.DURATION));
                } else {
                    mMyCards.get(1).addAction(Actions.moveTo(mMyCards.get(1).getX(), mMyCards.get(1).getY() + (mWidth / 2), Const.DURATION));
                }

                if (Const.DEBUG)
                    Gdx.app.log(this.getClass().getCanonicalName(), "card2 set to change " + !mMyCards.get(1).getHold());

            }
        });

        //mCard3 = new Sprite(new Texture("Cards/50_spades.png"));
        mMyCards.get(2).setPosition((mWidth / Const.MY_CARD_OFFSET) + (mMyCards.get(1).getWidth() / 2) + (mMyCards.get(1).getWidth() / 2), 0);
        mMyCards.get(2).setSize(mMyCards.get(2).getWidth() / 2, mMyCards.get(2).getHeight() / 2);
        mMyCards.get(2).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Game.getInstance().getGameStatus() != 3) return;
                Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mMyCards.get(2).setHold(!mMyCards.get(2).getHold());
                if (mMyCards.get(2).getHold()) {
                    mMyCards.get(2).addAction(Actions.moveTo(mMyCards.get(2).getX(), mMyCards.get(2).getY() - (mWidth / 2), Const.DURATION));
                } else {
                    mMyCards.get(2).addAction(Actions.moveTo(mMyCards.get(2).getX(), mMyCards.get(2).getY() + (mWidth / 2), Const.DURATION));
                }
                if (Const.DEBUG)
                    Gdx.app.log(this.getClass().getCanonicalName(), "card3 set to change " + !mMyCards.get(2).getHold());

            }
        });

        //mCard4 = new Sprite(new Texture("Cards/10_hearts.png"));
        mMyCards.get(3).setPosition((mWidth / Const.MY_CARD_OFFSET) + (mMyCards.get(2).getWidth() / 2) + (mMyCards.get(2).getWidth() / 2) + (mMyCards.get(2).getWidth() / 2), 0);
        mMyCards.get(3).setSize(mMyCards.get(3).getWidth() / 2, mMyCards.get(3).getHeight() / 2);
        mMyCards.get(3).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Game.getInstance().getGameStatus() != 3) return;
                Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mMyCards.get(3).setHold(!mMyCards.get(3).getHold());
                if (mMyCards.get(3).getHold()) {
                    mMyCards.get(3).addAction(Actions.moveTo(mMyCards.get(3).getX(), mMyCards.get(3).getY() - (mWidth / 2), Const.DURATION));
                } else {
                    mMyCards.get(3).addAction(Actions.moveTo(mMyCards.get(3).getX(), mMyCards.get(3).getY() + (mWidth / 2), Const.DURATION));
                }
                if (Const.DEBUG)
                    Gdx.app.log(this.getClass().getCanonicalName(), "card4 set to change " + !mMyCards.get(3).getHold());

            }
        });

        //mCard5 = new Sprite(new Texture("Cards/100_diamonds.png"));
        mMyCards.get(4).setPosition((mWidth / Const.MY_CARD_OFFSET) + (mMyCards.get(3).getWidth() / 2) + (mMyCards.get(3).getWidth() / 2) + (mMyCards.get(3).getWidth() / 2) + (mMyCards.get(3).getWidth() / 2), 0);
        mMyCards.get(4).setSize(mMyCards.get(4).getWidth() / 2, mMyCards.get(4).getHeight() / 2);
        mMyCards.get(4).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Game.getInstance().getGameStatus() != 3) return;
                Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.ogg")).play();
                mMyCards.get(4).setHold(!mMyCards.get(4).getHold());
                if (mMyCards.get(4).getHold()) {
                    mMyCards.get(4).addAction(Actions.moveTo(mMyCards.get(4).getX(), mMyCards.get(4).getY() - (mWidth / 2), Const.DURATION));
                } else {
                    mMyCards.get(4).addAction(Actions.moveTo(mMyCards.get(4).getX(), mMyCards.get(4).getY() + (mWidth / 2), Const.DURATION));
                }
                if (Const.DEBUG)
                    Gdx.app.log(this.getClass().getCanonicalName(), "card5 set to change " + !mMyCards.get(4).getHold());
            }
        });

        for (int i = 0; i < 5; i++) {
            mGame.mStageGame.addActor(mMyCards.get(i));
        }

    }

    public void render(SpriteBatch spritebatch) {
        mDeltaTime += Gdx.graphics.getDeltaTime();
        spritebatch.begin();
        if (mFont != null
                && mGame != null
                && mGame.getGame() != null
                && mGame.getGame().getGamePlayer() != null
                && mGame.getGame().getGamePlayer().getName() != null)
            mFont.draw(spritebatch, mGame.getGame().getGamePlayer().getName(), 0, 0);

        mMyCards.get(0).draw(spritebatch);
        mMyCards.get(1).draw(spritebatch);
        mMyCards.get(2).draw(spritebatch);
        mMyCards.get(3).draw(spritebatch);
        mMyCards.get(4).draw(spritebatch);

        spritebatch.end();

    }

    /**
     * Call when user confirm to Change card
     */
    public void changeCard() {
        for (int i = 0; i < 5; i++) {
            Card cartToChange = mMyCards.get(i);
            if (!cartToChange.getHold()) {
                //animateOut
                mMyCards.get(i).addAction(Actions.moveTo(Const.OUT_OF_SCREEN, Const.OUT_OF_SCREEN, Const.DURATION));
                mMyCards.set(i, null);
            }
        }
    }

    /**
     * redraw the card
     */
    public void redrawCardChanged() {
        initMyCard();
    }
}
