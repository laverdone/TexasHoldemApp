package com.glm.texas.holdem.game.net;



import com.badlogic.gdx.Gdx;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.Game;
import com.glm.texas.holdem.game.TexasHoldemGame;
import com.glm.texas.holdem.game.net.node.CommunicationTunnel;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by gianluca on 23/12/15.
 */
public class MasterNode implements Runnable {

    /**
     * Oggetto Thread utilizzato per la gestione dei client connessi
     */
    private Thread mMyThread;
    private Game mGame;
    private TexasHoldemGame mTexasHoldemGame;

    public MasterNode(TexasHoldemGame texasHoldemGame) {
        mTexasHoldemGame=texasHoldemGame;
    }

    /**
     * Init the Master Node
     */
    public boolean init(Game game) {
        mGame = game;
        mMyThread = new Thread(this, "MasterNodeConnectionChannel");
        mMyThread.start();
        return true;
    }

    @Override
    public void run() {
            CommunicationTunnel mClusterNode = null;
                try {
                    Socket oSocket = new Socket(Const.HOST_NAME, Const.HOST_PORT);
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    String ClusterID = df.format(new Date());

                    mClusterNode = new CommunicationTunnel(ClusterID, oSocket, this);


                    if(Gdx.app!=null) Gdx.app.log("debug", " -> Connection to Server from '"
                            + mClusterNode.getHostname() + "' ["
                            + mClusterNode.getHostAddress() + ":"
                            + mClusterNode.getPort() + "]");

                } catch (Exception e) {
                    if(Gdx.app!=null) Gdx.app.log("error", " error connecting to Server");
                    mGame=null;
                }
    }

    public Game getGame(){
        return mGame;
    }
    public void setGame(Game game){
        mGame=game;
        mTexasHoldemGame.setGame(game);
    }
    public TexasHoldemGame getTexasHoldemGame(){
        return mTexasHoldemGame;
    }
}

