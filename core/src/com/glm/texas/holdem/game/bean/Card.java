package com.glm.texas.holdem.game.bean;

/**
 * Created by gianluca on 01/07/16.
 */
public class Card
{
    private volatile short rank, suit;
    private volatile String mNaturalRank, mNaturalSuit;
    private volatile int mCardRank=0;
    private volatile int mCardSuit=0;
    private volatile int mCardUnicheID=0;
    private volatile boolean mHoldCard=false;

    public boolean isHoldCard() {
        return mHoldCard;
    }

    public void setHoldCard(boolean mHoldCard) {
        this.mHoldCard = mHoldCard;
    }

    public int getCardUnicheID() {
        return mCardUnicheID;
    }

    public void setCardUnicheID(int mCardUnicheID) {
        this.mCardUnicheID = mCardUnicheID;
    }

    private static String[] suits = { "hearts", "spades", "diamonds", "clubs" };
    private static String[] ranks  = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };

    /**
     * 4 Cuori - hearts
     * 3 Quadri - diamonds
     * 2 Fiori - club
     * 1 Picche - spades
     */
    private  static int[] iSuit = { 1, 2, 3, 4 };
    private  static int[] iRanks = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130 };

    public static String rankAsString( int __rank ) {
        return ranks[(__rank/10)];
    }

    public static String cardAsString( int __rank ) {
        return ranks[(__rank/10)-1];
    }

    public int getCardSuit() {
        return mCardSuit;
    }

    public Card(short suit, short rank)
    {
        this.rank=rank;
        this.suit=suit;
        mCardRank=iRanks[rank];
        mNaturalRank=ranks[rank];
        mNaturalSuit=suits[suit];
        if(suit==0){
            mCardSuit=4;
        }else if(suit==1){
            mCardSuit=1;
        }else if(suit==2){
            mCardSuit=3;
        }else if(suit==3){
            mCardSuit=2;
        }
    }

    public int getCardRank() {
        return mCardRank;
    }

    public @Override String toString()
    {
        return ranks[rank] + " of " + suits[suit];
    }

    public short getRank() {
        return rank;
    }

    public short getSuit() {
        return suit;
    }

    public String getmNaturalRank() {
        return mNaturalRank;
    }

    public String getmNaturalSuit() {
        return mNaturalSuit;
    }
}