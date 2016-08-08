package com.glm.texas.holdem.game.bean;


import com.glm.texas.holdem.game.Const;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gianluca on 07/07/16.
 */
public class Player {
    private volatile Hand mHand=null;
    private volatile String mName="";
    private volatile int mMoney= Const.INITIAL_MONEY;
    private volatile String mUnicheID=null;
    private volatile boolean isBot=true;
    private volatile boolean isWinner=false;
    private volatile boolean isPlayCurrentGame=true;


    private volatile boolean isBet=false;
    private volatile boolean isCheck=false;
    private volatile boolean isFold=false;
    private volatile boolean isLook=false;



    private volatile boolean isClientResponse=false;

    public synchronized boolean isBet() {
        return isBet;
    }

    public synchronized void setBet(boolean bet) {
        isBet = bet;
    }

    public synchronized boolean isCheck() {
        return isCheck;
    }

    public synchronized void setFold(boolean bet) {
        isFold = bet;
    }

    public synchronized boolean isFold() {
        return isFold;
    }

    public synchronized void setCheck(boolean check) {
        isCheck = check;
    }

    public synchronized boolean isLook() {
        return isLook;
    }

    public synchronized void setLook(boolean look) {
        isLook = look;
    }


    public synchronized boolean isClientResponse() {
        return isClientResponse;
    }

    public synchronized void setClientResponse(boolean clientResponse) {
        isClientResponse = clientResponse;
    }

    public synchronized boolean isPlayCurrentGame() {
        return isPlayCurrentGame;
    }

    public synchronized void setPlayCurrentGame(boolean playCurrentGame) {
        isPlayCurrentGame = playCurrentGame;
    }

    public synchronized boolean isWinner() {
        return isWinner;
    }

    public synchronized void setWinner(boolean winner) {
        isWinner = winner;
    }

    /**
     * 0=out of game
     * 1=first bet
     * 2=second bet
     * 100=wait to other
     *
     * **/
    private volatile int mStatus=0;

    public synchronized boolean isBot() {
        return isBot;
    }

    public synchronized void setBot(boolean bot) {
        isBot = bot;
    }

    public Player(){
        DateFormat formatter1 = new SimpleDateFormat("yyyymmddHHmmssSSSSSS");
        mUnicheID=formatter1.format(new Date());
        mUnicheID = String.valueOf(0 + (Math.random() * Float.parseFloat(mUnicheID)));
        mUnicheID=mUnicheID.replace(".","");
    }

    public synchronized int getStatus() {
        return mStatus;
    }

    public synchronized void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public synchronized Hand getHand() {
        return mHand;
    }

    public synchronized String getName() {
        return mName;
    }

    public synchronized void setName(String mName) {
        this.mName = mName;
    }

    public synchronized int getMoney() {
        return mMoney;
    }

    public synchronized void setMoney(int mMoney) {
        this.mMoney = mMoney;
    }

    public synchronized String getUnicheID() {
        return mUnicheID;
    }

    public synchronized void setUnicheID(String mUnicheID) {
        this.mUnicheID = mUnicheID;
    }

    public synchronized void setHand(Hand mHand) {

        this.mHand = mHand;
    }

    public synchronized void reset() {
       isBet=false;
       isCheck =false;
       isFold=false;
       isLook=false;
       isClientResponse=false;
       isWinner=false;
       isPlayCurrentGame=true;
    }
}
