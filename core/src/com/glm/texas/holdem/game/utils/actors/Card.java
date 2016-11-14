package com.glm.texas.holdem.game.utils.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by gianluca on 04/11/16.
 */

public class Card extends Actor {
    private Sprite mSprite;

    private com.glm.texas.holdem.game.bean.Card mCard;
    public Card(com.glm.texas.holdem.game.bean.Card card){
        mCard   =   card;
        mSprite = new Sprite(new Texture("data/cards/"+mCard.getCardRank()+"_"+mCard.getmNaturalSuit()+".png"));

        mCard.setHoldCard(true);
        setWidth(mSprite.getWidth());
        setHeight(mSprite.getHeight());
        setBounds(0,0,getWidth(),getHeight());
    }

    public void draw(Batch batch) {
        mSprite.setPosition(getX(),getY());
        setPosition(getX(),getY());
        mSprite.draw(batch);
    }

    public boolean getHold() {
        return mCard.isHoldCard();
    }

    public void setHold(boolean hold){
        mCard.setHoldCard(hold);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        mSprite.setPosition(x,y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        mSprite.setSize(width,height);
    }

    @Override
    protected void positionChanged() {
        mSprite.setPosition(getX(),getY());
        super.positionChanged();

    }
}
