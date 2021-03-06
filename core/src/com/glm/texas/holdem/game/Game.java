package com.glm.texas.holdem.game;


import com.glm.texas.holdem.game.bean.Hand;
import com.glm.texas.holdem.game.bean.Player;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by gianluca on 06/07/16.
 */
public class Game {
    private static volatile Game mMyInstance;

    /**
     * 0=initial timeout
     * 1=start the game
     * 2=first bet
     * 3=turn over players
     * 4=final bet
     * 5=find winner
     * */
    private volatile int mGameStatus=0;
    /**
     * intermediate state of game
     * E.S.
     * status=3
     * substatus=1 redraw changed card
     * */
    private volatile int mGameSubStatus=0;
    private volatile String mGameStatusDesc="initial timeout";

    private volatile int botTimeout=0;
    private volatile int umanTimeout=0;
    private volatile int  roomTimeout=0;

    /**identify the current player (ME)*/
    private volatile Player mGamePlayer;

    private volatile Vector<Player> mPlayers = new Vector<Player>();

    private volatile Vector<Player> mPlayersReTurn = new Vector<Player>();

    private volatile Deck mDeck= new Deck();
    private volatile int mNumberOfPlayers;
    private volatile Vector<Integer> iPlayerIndex=new Vector<Integer>();
    private volatile boolean inGame=false;
    private volatile int mGameTimeout =30000;
    private volatile String mCurrentPlayerUnicheIDTurn="";
    private volatile Player mWinner = null;
    private volatile String mUnicheID="";

    public Game(){
        int numberOfPlayers = 4 + (int)(Math.random() * 2);
        DateFormat formatter1 = new SimpleDateFormat("yyyymmddHHmmssSSSSSS");
        mUnicheID=formatter1.format(new Date());
        mUnicheID = String.valueOf(0 + (Math.random() * Float.parseFloat(mUnicheID)));
        mUnicheID=mUnicheID.replace(".","");

        mNumberOfPlayers=numberOfPlayers;

    }

    public static Game getInstance(){
        if(mMyInstance==null){
            synchronized (Game.class){
                if(mMyInstance==null){
                    mMyInstance=new Game();
                }
            }
        }
        return mMyInstance;
    }

    public static void setInstance(Game instance) {
        mMyInstance = instance;
    }

    public int getNumberOfPlayers() {
        return mNumberOfPlayers;
    }

    public void removeOnePlayers() {
        mNumberOfPlayers--;
    }

    public synchronized Player getmWinner() {
        return mWinner;
    }

    public synchronized void setmWinner(Player mWinner) {
        this.mWinner = mWinner;
    }

    public synchronized Vector<Integer> getiPlayerIndex() {
        return iPlayerIndex;
    }

    public synchronized void setiPlayerIndex(Integer index) {
        iPlayerIndex.add(index);
    }

    public synchronized Integer removeiPlayerIndex(int iMe) {
        return iPlayerIndex.remove(iMe);
    }

    public synchronized Vector<Player> getmPlayersReTurn() {
        return mPlayersReTurn;
    }

    public synchronized void setmPlayersReTurn(Player playersReTurn) {
        mPlayersReTurn.add(playersReTurn);
    }

    public synchronized String getUnicheID() {
        return mUnicheID;
    }

    public synchronized String getCurrentPlayerUnicheIDTurn() {
        return mCurrentPlayerUnicheIDTurn;
    }

    public synchronized void setCurrentPlayerUnicheIDTurn(String mCurrentPlayerTurn) {
        mCurrentPlayerUnicheIDTurn = mCurrentPlayerTurn;
    }

    public synchronized int getGameStatus() {
        return mGameStatus;
    }

    public void setGameStatus(int gameStatus) {
        mGameStatus = gameStatus;
        if(mGameStatus==0){
            mGameStatusDesc = "initial timeout";
        }else if(mGameStatus==1){
            mGameStatusDesc = "start the game";
        }else if(mGameStatus==2){
            mGameStatusDesc = "first bet";
        }else if(mGameStatus==3){
            mGameStatusDesc = "turn over players";
        }else if(mGameStatus==4){
            mGameStatusDesc = "final bet";
        }else if(mGameStatus==5){
            mGameStatusDesc = "find winner";
        }
    }

    public synchronized Vector<Player> getPlayers() {
        return mPlayers;
    }

    public synchronized void setPlayers(Vector<Player> players) {
        mPlayers=players;
    }

    public synchronized Deck getDeck(){
        return mDeck;
    }

    public synchronized void setDeck(Deck deck) {
        mDeck = deck;
    }

    public synchronized void replacePlayer(Player tmpPlayer) {
        int i=0;
        while(true){
            if(mPlayers.get(i).getUnicheID().equals(tmpPlayer.getUnicheID())){
                //mLog.debug("replacePlayer object: "+tmpPlayer.getUnicheID());
                mPlayers.set(i,tmpPlayer);
                //mLog.debug(gson.toJson(tmpPlayer));
                return;
            }
            i++;
        }
    }

    /**
     * get player in game
     * */
    public Player getGamePlayer(){
        return mGamePlayer;
    }

    /**
     * set player in game
     * */
    public void setGamePlayer(Player player){
        mGamePlayer=player;
    }

    public int getBotTimeout() {
        return botTimeout;
    }

    public void setBotTimeout(int botTimeout) {
        this.botTimeout = botTimeout;
    }

    public int getUmanTimeout() {
        return umanTimeout;
    }

    public void setUmanTimeout(int umanTimeout) {
        this.umanTimeout = umanTimeout;
    }

    public int getRoomTimeout() {
        return roomTimeout;
    }

    public void setRoomTimeout(int roomTimeout) {
        this.roomTimeout = roomTimeout;
    }

    public int getGameSubStatus() {
        return mGameSubStatus;
    }

    public void setGameSubStatus(int mGameSubStatus) {
        this.mGameSubStatus = mGameSubStatus;
    }
}
