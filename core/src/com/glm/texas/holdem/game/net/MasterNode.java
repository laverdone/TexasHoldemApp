package com.glm.texas.holdem.game.net;


import com.badlogic.gdx.Gdx;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.Game;
import com.glm.texas.holdem.game.TexasHoldemAdapter;
import com.glm.texas.holdem.game.net.node.CommunicationTunnel;
import com.glm.texas.holdem.game.utils.GameStatusController;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by gianluca on 23/12/15.
 */
public class MasterNode implements Runnable {
    private static volatile MasterNode mMyInstance;

    /**
     * Oggetto Thread utilizzato per la gestione dei client connessi
     */
    private Thread mMyThread;
    private TexasHoldemAdapter mTexasHoldemAdapter;
    public  CommunicationTunnel mClusterNode = null;
    public GameStatusController mGameController = null;
    private Socket mSocket;

    public static void setInstance(MasterNode instance) {
        mMyInstance = instance;
    }


    public static MasterNode getInstance(){
        if(mMyInstance==null){
            synchronized (MasterNode.class){
                if(mMyInstance==null){
                    mMyInstance=new MasterNode();
                }
            }
        }
        return mMyInstance;
    }



    public boolean isOnLineGameAvaiable() {
        return isOnLineGameAvaiable;
    }

    private boolean isOnLineGameAvaiable=false;


    public MasterNode() {

        try {
           mSocket = new Socket(Const.HOST_NAME, Const.HOST_PORT);
           isOnLineGameAvaiable=true;
        } catch (IOException e) {
            isOnLineGameAvaiable=false;
            if (Const.DEBUG && Gdx.app != null) Gdx.app.log(this.getClass().getCanonicalName(), " error connecting to Server OnLineGameAvaiable not Avaiable");
        }
    }

    /**
     * Init the Master Node
     */
    public boolean init(TexasHoldemAdapter texasHoldemAdapter) {
        mTexasHoldemAdapter = texasHoldemAdapter;

        mMyThread = new Thread(this, "MasterNodeConnectionChannel");
        mMyThread.start();
        return true;
    }

    @Override
    public void run() {


        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String ClusterID = df.format(new Date());

            mClusterNode     = new CommunicationTunnel(ClusterID, mSocket, this);
            //Wait for communication channel
            while(Game.getInstance()==null){
                Thread.sleep(2000);
            }

            mGameController  =  new GameStatusController(mTexasHoldemAdapter);



            if (Const.DEBUG && Gdx.app != null)
                Gdx.app.log("debug", " -> Connection to Server from '"
                        + mClusterNode.getHostname() + "' ["
                        + mClusterNode.getHostAddress() + ":"
                        + mClusterNode.getPort() + "]");

            mTexasHoldemAdapter.setCommunicationChannel(mClusterNode);

        } catch (Exception e) {
            if (Const.DEBUG && Gdx.app != null) Gdx.app.log("error", " error connecting to Server");
            Game.getInstance().setGameStatus(-10000);
            if(mGameController!=null) mGameController.stopController();
            e.printStackTrace();
        }
    }

    public TexasHoldemAdapter getTexasHoldemGame() {
        return mTexasHoldemAdapter;
    }
}

