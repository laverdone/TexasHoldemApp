package com.glm.texas.holdem.game.net;



import com.badlogic.gdx.Gdx;
import com.glm.texas.holdem.game.Game;
import com.glm.texas.holdem.game.bean.Config;
import com.glm.texas.holdem.game.bean.Player;
import com.glm.texas.holdem.game.net.node.CommunicationTunnel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by gianluca on 23/12/15.
 */
public class MasterNode implements Runnable{


    private ServerSocket mMasterSocket = null;
    private String mListenAddress;
    private int mListenPort;
    private boolean bDieServer=false;
    private HashMap<String, CommunicationTunnel> vSlaveNode = new HashMap<String, CommunicationTunnel>();

    /** Oggetto Thread utilizzato per la gestione dei client connessi */
    private Thread mMyThread;



    private Config mServerConfig=null;
    /**
     * Init the Master Node
     * */
    public boolean init(String listenAddress, int listenPort, Config serverConfg){
        mServerConfig   =   serverConfg;
        mListenAddress  =   listenAddress;
        mListenPort     =   listenPort;
        mMyThread = new Thread(this, "MasterNodeConnectionChannel");
        mMyThread.start();
        return true;
    }

        @Override
        public void run() {
            try {
                mMasterSocket   = new ServerSocket(mListenPort);
                bDieServer = false;
                CommunicationTunnel mClusterNode = null;
                while (!bDieServer) {
                    try {
                        Socket oSocket = mMasterSocket.accept();
                        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                        String ClusterID = df.format(new Date());
                        boolean isJoined=false;


                        mClusterNode = new CommunicationTunnel(ClusterID, oSocket, this);




                        Gdx.app.log("debug"," -> Accepted incoming connection from '"
                                + mClusterNode.getHostname() + "' ["
                                + mClusterNode.getHostAddress() + ":"
                                + mClusterNode.getPort() + "]");
                        /**
                         * Spedisce a tutti i client la notifica di un nuovo client
                         * connesso
                         */
                        // notifyNewConnectionToClients(oNewClient.getClientName(),
                        // oNewClient.getHostname(),oNewClient.getHostAddress(),String.valueOf(oNewClient.getPort()));
                        try {
                            vSlaveNode.put(ClusterID, mClusterNode);// addElement(oNewClient);
                        } catch (Exception e) {
                            if (!bDieServer) System.out.println(e);
                        }
                    } catch (Exception e) {
                        if (!bDieServer) System.out.println(e);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error init cluster socket");
                System.out.println(e);

            }
        }
    /**
     * Remove Node
     * */
    public void removeNode(String mClusterID) {
        vSlaveNode.remove(mClusterID);
    }
}

