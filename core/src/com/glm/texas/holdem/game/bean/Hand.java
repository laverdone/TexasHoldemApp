package com.glm.texas.holdem.game.bean;

import com.glm.texas.holdem.game.Deck;

import java.util.Arrays;
import java.util.HashMap;
import com.glm.texas.holdem.game.bean.Card;
/**
 * Created by gianluca on 01/07/16.
 */
public class Hand {
    /** Oggetto log4j utilizzato per tutte le operazioni di logging */
    public HashMap<Integer, Card> mCards = new HashMap<Integer, Card>();

    public String mName;
    private String mNaturalScore="";
    private int[] mHandCardRank = { -1, -1, -1, -1, -1};
    private int mMyScore=0;
    private Deck mDeck=null;

    public Hand(Deck d)
    {
        mDeck=d;
        for (int x=0; x<5; x++)
        {
            Card tmpCard= mDeck.drawFromDeck();
            tmpCard.setCardUnicheID(x);
            mHandCardRank[x]=tmpCard.getCardRank();
            mCards.put(tmpCard.getCardRank()+tmpCard.getCardSuit(),tmpCard);
        }

        calculateScore();

    }

    public void setName(String name){
        mName=name;
    }
    public String getName(){
        return mName;
    }


    public int myScore(){
        return mMyScore;
    }

    public Card getCard(int nOfCard){
        for (Integer key : mCards.keySet()) {
            if(nOfCard==mCards.get(key).getCardUnicheID()){
                return mCards.get(key);
            }
        }
        return null;//cards[nOfCard];
    }

    public void replaceCard(int nOfCardtoRelace, Card card){
        for (Integer key : mCards.keySet()) {
            if(nOfCardtoRelace==mCards.get(key).getCardUnicheID()){
                card.setCardUnicheID(nOfCardtoRelace);
                mCards.put(key,card);
            }
        }
    }

    public HashMap<Integer, Card> displayAll() {
        return mCards;
    }
    public String display() {
        return mNaturalScore;
    }

    public int compareTo(Hand that)
    {
        if(this.mMyScore>that.mMyScore){
            return 1;
        }else if (this.mMyScore<that.mMyScore){
            return -1;
        }else{
            return 0;
        }
        /*for (int x=0; x<6; x++)
        {
            if (this.value[x]>that.value[x])
                return 1;
            else if (this.value[x]<that.value[x])
                return -1;
        }
        return 0; //if hands are equal*/
    }


    public int getmMyScore() {
        return mMyScore;
    }

    /**
     * 2.1	Scala reale (royal flush)
     * 2.2	Scala a colore (straight flush)
     * 2.3	Poker (four of a kind)
     * 2.4	Full (Full house)
     * 2.5	Colore (flush)
     * 2.6	Scala (straight)
     * 2.7	Tris (three of a kind)
     * 2.8	Doppia coppia (two pairs)
     * 2.9	Coppia (one pair)
     * 2.10	Carta isolata o carta alta (high card, no pair)
     *
     * **/
    public void calculateScore() {

        int[] mCardRanks = new int[5];
        int mTotalScore=0;
        boolean flush=false, straight=false, max_straight=false, mix_straight=false, middle_straight=false,
                poker=true,  full=false, tris=false, two_pairs=false, one_pair=false;

        boolean mSearchForScore=true;
        int x=0;
        for (Integer key : mCards.keySet()) {
            try{
                mHandCardRank[x]=mCards.get(key).getCardRank();
            }catch (Exception w){
               //Gdx.app.log("error",w+" n of card: "+mDeck.getTotalCards());
            }

            x++;
        }


        /*for (int x=0; x<5; x++)
        {
            mHandCardRank[x]=cards[x].getCardRank();
        }*/

        //Importante per calcolo punteggi
        Arrays.sort(mHandCardRank);

        //Cerco per Scala Massima, Minima e Media
        boolean tmpStraight=false;
        for (x=0; x<4; x++) {
            if((!max_straight && !mix_straight && !middle_straight) && mHandCardRank[0]==10) {
                //Può essere scala minima o massima
                if(mHandCardRank[4]==130){
                    tmpStraight=true;
                    max_straight=true;
                } else if (mHandCardRank[4]==50){
                    tmpStraight=true;
                    mix_straight=true;
                }
                continue;
            }else{
                middle_straight=true;
            }
            if ((x + 1) <= 4){
                if(mHandCardRank[x] == (mHandCardRank[x + 1] - 10)) {
                    continue;
                }else{
                    straight = false;
                    break;
                }
            }
            if(tmpStraight){
                //4-5 di scala
                straight=true;
            }
        }

        //Cerco per Colore
        Card prevCard=null;
        for (Integer key : mCards.keySet()) {
            if(prevCard==null){
                prevCard=mCards.get(key);
            }else{
                if(prevCard.getSuit()!=mCards.get(key).getSuit()){
                    flush = false;
                    break;
                }
            }
        }
        /*for (int x=0; x<4; x++) {
            if ( cards[x].getSuit() != cards[x+1].getSuit() ) {
                flush = false;
                break;
            }
        }*/

        if(flush && straight && max_straight){
            //Scala Reale
            for (Integer key : mCards.keySet()) {
                mCards.get(key).setHoldCard(true);
            }
            mSearchForScore=false;
            mMyScore=11000;
            mNaturalScore="max straight flush " + Card.cardAsString(mHandCardRank[4]) + " high";
        }

        if(flush && straight && mix_straight){
            //Scala Reale Minima
            for (Integer key : mCards.keySet()) {
                mCards.get(key).setHoldCard(true);
            }
            mSearchForScore=false;
            mMyScore=11010;
            mNaturalScore="min straight flush " + Card.cardAsString(mHandCardRank[4]) + " high";
        }

        if(flush && straight && middle_straight){
            //Scala Reale Media
            for (Integer key : mCards.keySet()) {
                mCards.get(key).setHoldCard(true);
            }
            mSearchForScore=false;
            mMyScore=11020;
            mNaturalScore="middle straight flush " + Card.cardAsString(mHandCardRank[4]) + " high";
        }


        if(mSearchForScore && straight){
            //Scala
            for (Integer key : mCards.keySet()) {
                mCards.get(key).setHoldCard(true);
            }
            mSearchForScore=false;
            mMyScore=10000;
            mNaturalScore=Card.cardAsString(mHandCardRank[4]) + " high straight";
        }

        if(mSearchForScore && flush) {
            //Colore
            for (Integer key : mCards.keySet()) {
                mCards.get(key).setHoldCard(true);
            }
            mMyScore = 8000;
            mSearchForScore = false;
            mNaturalScore = "flush";
        }

        //Poker
        if(mSearchForScore) {
            int iMaxPairCard = 1;
            String pokerCard = "", pokerSuit = "";
            mTotalScore=0;
            for (x = 0; x < 4; x++) {
                if ((x + 1) <= 4) {
                    if (mHandCardRank[x] == mHandCardRank[x + 1]) {
                        iMaxPairCard++;

                        //pokerCard = cards[x].getmNaturalRank();
                        //pokerSuit = cards[x].getmNaturalSuit();
                        mCardRanks[mTotalScore] = mHandCardRank[x];
                    }
                }
                if (iMaxPairCard < 4) poker = false;
            }
            if(poker){
                for (Integer key : mCards.keySet()) {
                    if(mCardRanks[0]==mCards.get(key).getCardRank()){
                        mCards.get(key).setHoldCard(true);
                    }
                }
                mMyScore=9000+mCardRanks[0];
                mSearchForScore=false;
                mNaturalScore="four of a kind " + pokerCard + " "+pokerSuit;
            }
        }

        if(mSearchForScore) {
            //Full
            int iMaxPairCard = 1;
            boolean due = false;
            boolean tre = false;
            mTotalScore=0;
            for (x = 0; x < 4; x++) {
                if ((x + 1) <= 4) {
                    if (mHandCardRank[x] == mHandCardRank[x + 1]) {
                        iMaxPairCard++;
                    } else {
                        if (iMaxPairCard == 2) {
                            due = true;
                            mCardRanks[mTotalScore] = mHandCardRank[x-1];
                            mTotalScore++;
                            iMaxPairCard=1;
                        } else if (iMaxPairCard == 3) {
                            tre = true;
                            mCardRanks[mTotalScore] = mHandCardRank[x-1];

                        }
                    }
                }
                if (tre && due) full = true;
            }
            if(full){
                for (Integer key : mCards.keySet()) {
                    mCards.get(key).setHoldCard(true);
                }
                mSearchForScore=false;
                mMyScore=7000+mCardRanks[0]+mCardRanks[1];
                mNaturalScore="full house " + Card.rankAsString(mCardRanks[0]) + " over " + Card.rankAsString(mCardRanks[1]);
            }
        }

        if(mSearchForScore) {
            //Tris
            mTotalScore=0;
            int iMaxPairCard = 1;
            for (x = 0; x < 4; x++) {
                if ((x + 1) <= 4) {
                    if (mHandCardRank[x] == mHandCardRank[x + 1]) {
                        iMaxPairCard++;
                        mCardRanks[mTotalScore] = mHandCardRank[x];
                    } else {
                        if (iMaxPairCard == 3) {
                            tris = true;
                            break;
                        }
                    }
                }
            }
            if(tris){
                for (Integer key : mCards.keySet()) {
                    if(mCardRanks[0]==mCards.get(key).getCardRank()){
                        mCards.get(key).setHoldCard(true);
                    }
                }
                mSearchForScore=false;
                mMyScore=6000+mCardRanks[0];
                mNaturalScore="three of a kind " + Card.cardAsString(mCardRanks[0]) + "\'s";
            }
        }


        if(mSearchForScore) {
            //Doppia
            mTotalScore = 0;
            int iMaxPairCard = 1;
            int nPair = 0;
            for (x = 0; x < 4; x++) {
                if ((x + 1) <= 4) {
                    if (mHandCardRank[x] == mHandCardRank[x + 1]) {
                        mCardRanks[mTotalScore] = mHandCardRank[x];
                        iMaxPairCard++;
                        mTotalScore++;
                    } else {
                        if (iMaxPairCard == 2) {
                            nPair++;
                            iMaxPairCard=1;
                            continue;
                        }
                    }

                }
            }
            if (nPair == 2) two_pairs = true;
            if(two_pairs){
                for (Integer key : mCards.keySet()) {
                    if(mCardRanks[0]==mCards.get(key).getCardRank()
                            || mCardRanks[1]==mCards.get(key).getCardRank()){
                        mCards.get(key).setHoldCard(true);
                    }
                }
                mSearchForScore=false;
                mMyScore=5000+mCardRanks[0]+mCardRanks[1];
                mNaturalScore="two pair " + Card.cardAsString(mCardRanks[0]) + " and " + Card.cardAsString(mCardRanks[1]);

            }
        }

        if(mSearchForScore) {
            //Coppia
            mTotalScore = 0;
            int iMaxPairCard = 1;
            for (x = 0; x < 4; x++) {
                if ((x + 1) <= 4) {
                    if (mHandCardRank[x] == mHandCardRank[x + 1]) {
                        mCardRanks[mTotalScore] = mHandCardRank[x];

                        iMaxPairCard++;

                    } else {
                        if (iMaxPairCard == 2) {
                            one_pair = true;
                            break;
                        }
                    }
                }
            }
            if (iMaxPairCard == 2) {
                one_pair = true;
            }
            if(one_pair){
                for (Integer key : mCards.keySet()) {
                    if(mCardRanks[0]==mCards.get(key).getCardRank()){
                        mCards.get(key).setHoldCard(true);
                    }
                }
                mMyScore=4000+mCardRanks[0];
                mSearchForScore=false;
                mNaturalScore="one pair of " + Card.cardAsString(mCardRanks[0]);
            }
        }

        //Assegno lo Score da 1 a 10
        if(mSearchForScore){
            //Carta Alta

            //mSearchForScore=false;
            if(mHandCardRank[0]==10){
                for (Integer key : mCards.keySet()) {
                    if(mHandCardRank[0]==mCards.get(key).getCardRank()){
                        mCards.get(key).setHoldCard(true);
                    }
                }
                mNaturalScore="high card " + Card.cardAsString(mHandCardRank[0]);
                mMyScore=3000+mHandCardRank[0];
            }else{
                for (Integer key : mCards.keySet()) {
                    if(mHandCardRank[4]==mCards.get(key).getCardRank()){
                        mCards.get(key).setHoldCard(true);
                    }
                }
                mNaturalScore="high card " + Card.cardAsString(mHandCardRank[4]);
                mMyScore=3000+mHandCardRank[4];
            }

        }



        /*for (int x=0; x<=13; x++)
        {
            ranks[x]=0;
        }
        for (int x=0; x<=4; x++)
        {
            ranks[ cards[x].getRank() ]++;
        }



        for (int x=13; x>=1; x--)
        {

            if (ranks[x] > sameCards)
            {
                if (sameCards != 1)  //if sameCards was not the default value
                {
                    sameCards2 = sameCards;
                    smallGroupRank = x;   //changed from smallGroupRank=largeGroupRank;
                }

                sameCards = ranks[x];
                largeGroupRank = x;

            }
            else if (ranks[x] > sameCards2)
            {
                sameCards2 = ranks[x];
                smallGroupRank = x;
            }
        }


        if (ranks[1]==1) //if ace, run this before because ace is highest card
        {
            orderedRanks[index]=14;
            index++;
        }

        for (int x=13; x>=2; x--)
        {
            if (ranks[x]==1)
            {
                orderedRanks[index]=x; //if ace
                index++;
            }
        }




        for (int x=1; x<=9; x++) //can't have straight with lowest value of more than 10
        {
            if (ranks[x]==1 && ranks[x+1]==1 && ranks[x+2]==1 && ranks[x+3]==1 && ranks[x+4]==1)
            {
                straight=true;
                topStraightValue=x+4; //4 above bottom value
                break;
            }
        }

        if (ranks[10]==1 && ranks[11]==1 && ranks[12]==1 && ranks[13]==1 && ranks[1]==1) //ace high
        {
            straight=true;
            topStraightValue=14; //higher than king
        }

        for (int x=0; x<=5; x++)
        {
            value[x]=0;
        }


        //start hand evaluation
        if ( sameCards==1 ) {
            value[0]=1;
            value[1]=orderedRanks[0];
            value[2]=orderedRanks[1];
            value[3]=orderedRanks[2];
            value[4]=orderedRanks[3];
            value[5]=orderedRanks[4];
        }

        if (sameCards==2 && sameCards2==1)
        {
            value[0]=2;
            value[1]=largeGroupRank; //rank of pair
            value[2]=orderedRanks[0];
            value[3]=orderedRanks[1];
            value[4]=orderedRanks[2];
        }

        if (sameCards==2 && sameCards2==2) //two pair
        {
            value[0]=3;
            value[1]= largeGroupRank>smallGroupRank ? largeGroupRank : smallGroupRank; //rank of greater pair
            value[2]= largeGroupRank<smallGroupRank ? largeGroupRank : smallGroupRank;
            value[3]=orderedRanks[0];  //extra card
        }

        if (sameCards==3 && sameCards2!=2)
        {
            value[0]=4;
            value[1]= largeGroupRank;
            value[2]=orderedRanks[0];
            value[3]=orderedRanks[1];
        }

        if (straight && !flush)
        {
            value[0]=5;
            value[1]=topStraightValue;
        }

        if (flush && !straight)
        {
            value[0]=6;
            value[1]=orderedRanks[0]; //tie determined by ranks of cards
            value[2]=orderedRanks[1];
            value[3]=orderedRanks[2];
            value[4]=orderedRanks[3];
            value[5]=orderedRanks[4];
        }

        if (sameCards==3 && sameCards2==2)
        {
            value[0]=7;
            value[1]=largeGroupRank;
            value[2]=smallGroupRank;
        }

        if (sameCards==4)
        {
            value[0]=8;
            value[1]=largeGroupRank;
            value[2]=orderedRanks[0];
        }

        if (straight && flush)
        {
            value[0]=9;
            value[1]=topStraightValue;
        }

        switch( value[0] )
        {

            case 1:
                mNaturalScore="high card";
                break;
            case 2:
                mNaturalScore="pair of " + Card.rankAsString(value[1]) + "\'s";
                break;
            case 3:
                mNaturalScore="two pair " + Card.rankAsString(value[1]) + " " + Card.rankAsString(value[2]);
                break;
            case 4:
                mNaturalScore="three of a kind " + Card.rankAsString(value[1]) + "\'s";
                break;
            case 5:
                mNaturalScore=Card.rankAsString(value[1]) + " high straight";
                break;
            case 6:
                mNaturalScore="flush";
                break;
            case 7:
                mNaturalScore="full house " + Card.rankAsString(value[1]) + " over " + Card.rankAsString(value[2]);
                break;
            case 8:
                mNaturalScore="four of a kind " + Card.rankAsString(value[1]);
                break;
            case 9:
                mNaturalScore="straight flush " + Card.rankAsString(value[1]) + " high";
                break;
            default:
                mNaturalScore="error in Hand.display: value[0] contains invalid value";
        }*/

    }
}



