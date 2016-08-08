package com.glm.texas.holdem.game;

/**
 * Created by gianluca on 01/07/16.
 */
import com.glm.texas.holdem.game.bean.Card;

import java.util.Random;
import java.util.ArrayList;

public class Deck {
    private volatile ArrayList<Card> cards=null;
    private volatile ArrayList<Card> cardsChanged=null;
    private volatile int mNumberOfCard=0;
    private volatile int mMoney=0;
    private volatile boolean isOpen=false;



    private volatile int mCurrentBet=0;

    public Deck()
    {
        cards=null;
        cardsChanged=null;
        cards = new ArrayList<Card>();
        cardsChanged = new ArrayList<Card>();
        int index_1, index_2;
        Random generator = new Random();
        Card temp;

        for (short a=0; a<=3; a++)
        {
            for (short b=0; b<=12; b++)
            {
                cards.add( new Card(a,b) );
            }
        }

        int size = cards.size() -1;

        for (short i=0; i<100; i++)
        {
            index_1 = generator.nextInt( size );
            index_2 = generator.nextInt( size );

            temp = (Card) cards.get( index_2 );
            cards.set( index_2 , cards.get( index_1 ) );
            cards.set( index_1, temp );
        }
        //System.out.println("Card Size is: "+cards.size());
        mNumberOfCard=cards.size();
    }

    public synchronized boolean isOpen() {
        return isOpen;
    }

    public synchronized void setOpen(boolean open) {
        isOpen = open;
    }

    public synchronized int getMoney() {
        return mMoney;
    }

    public synchronized void setMoney(int mMoney) {
        this.mMoney = mMoney;
    }

    public synchronized int getCurrentBet() {
        return mCurrentBet;
    }

    public synchronized void setCurrentBet(int mCurrentBet) {
        this.mCurrentBet = mCurrentBet;
    }

    public synchronized ArrayList<Card> getRemainingCards(){
        return cards;
    }
    public synchronized Card drawFromDeck()
    {
        return cards.remove( cards.size()-1 );
    }

    public synchronized Card changeCard(){
        if(cards.size()!=0){
            //Add Card to Removed Card
            cardsChanged.add(cards.get(cards.size()-1));
            return cards.remove( cards.size()-1 );
        }else if(cardsChanged.size()!=0) {
            //reuse card removed
            return cardsChanged.remove(cardsChanged.size() - 1);
        }
        //System.out.println("N. of card: "+cards.size());
        //System.out.println("N. of card: "+cardsChanged.size());
        return null;
    }

    public synchronized int getTotalCards()
    {
        return cards.size();  //we could use this method when making a complete poker game to see if we needed a new deck
    }
}