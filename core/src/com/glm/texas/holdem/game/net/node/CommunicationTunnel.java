package com.glm.texas.holdem.game.net.node;


import com.badlogic.gdx.Gdx;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.Game;


import com.glm.texas.holdem.game.bean.Message;
import com.glm.texas.holdem.game.bean.Player;
import com.glm.texas.holdem.game.net.MasterNode;
import com.glm.texas.holdem.game.net.util.Base64Coder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;


/**
 * Created by gianluca on 23/12/15.
 * bead class user by MasterNode for Cluster implementation
 */
public class CommunicationTunnel implements Runnable {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * IP Address of node
     */
    private String mNodeAdress = "localhost";
    /**
     * Master Node Port Number
     */
    private int mPortNumber = 35441;
    /**
     * Node Name
     */
    private String mServerName = "node";
    /**
     * Master Server IP Address
     */
    private String mMasterAddress = null;
    /**
     * Node Queque of job
     */
    private HashMap<Integer, String> mJobQueque = new HashMap<Integer, String>();
    /**
     * Socket to Node
     */
    private Socket mSocketClient = null;
    /**
     * Master Socket Node
     */
    private MasterNode mMasterSocket = null;

    /**
     * Oggetto utilizzato per la lettura delle informazioni provenienti dal
     * Client
     */
    private BufferedReader oBR;
    /**
     * Oggetto utilizzato per la scrittura delle informazioni verso il Clinet
     */
    private BufferedWriter oBW;
    /**
     * Oggetto Thread utilizzato per la gestione dei client connessi
     */
    private Thread mMyThread;
    private String mClusterID;
    /**
     * Identifica se il client è uno Slave Valido
     */
    private boolean isValidClientNode = false;
    private Game mCurrentRoom = null;

    private Message mMessage = new Message();
    private Player mCurrentUmanPlayer = null;
    private String mMessageSended = null;

    public CommunicationTunnel(String clusterID, Socket oSocket, MasterNode oParentServer) {
        mSocketClient = oSocket;
        mMasterSocket = oParentServer;
        mClusterID = clusterID;
        mMyThread = new Thread(this, "ClusterConnectionChannel");
        mMyThread.start();
    }

    /**
     * channel to comunicate
     */
    @Override
    public void run() {

        try {

            //TODO
            //Gdx.app.log("info","Communication Channel");

            // Creo un buffer sull'inputStream per determinare il protocollo
            int iBufferMark = 100;    //marco un buffer interno all'input stream

            BufferedInputStream oBufferedInputStream = null;
            oBufferedInputStream = new BufferedInputStream(mSocketClient.getInputStream());

            oBufferedInputStream.mark(iBufferMark);

            oBR = new BufferedReader(new InputStreamReader(oBufferedInputStream));

            if (oBW == null) {
                oBW = new BufferedWriter(new OutputStreamWriter(mSocketClient
                        .getOutputStream()));
            }

            //SEND AUTH COMMAND
            loginToServer();

            String line;

            while (true) {
                try {
                    //TODO
                    line = oBR.readLine();
                    if (Gdx.app != null)
                        Gdx.app.log("debug", "Response Message From Server:" + line);
                    if (line == null) break;


                    //Get User Message
                    //Message tmpMessage = gson.fromJson(line, Message.class);
                    mMessage = gson.fromJson(line, Message.class);
                    mCurrentUmanPlayer = mMessage.getPlayer();
                    mCurrentUmanPlayer.setClientResponse(true);

                    if (mMessage.getMessage().equals("assign_slot")) {
                        Game tmpGame = mMessage.getCurrentGame();
                        Player tmpPlayer = mMessage.getPlayer();
                        tmpPlayer.setName("Gianluca Masci");
                        tmpPlayer.setBot(false);
                        tmpPlayer.setMoney(2001);
                        mMessage.setPlayer(tmpPlayer);
                        mMessage.setMessage("user_player");
                        tmpGame.setGamePlayer(tmpPlayer);
                        mMasterSocket.setGame(tmpGame);
                    }else if (mMessage.getMessage().equals("join_room_ok")) {
                        mMessage.getCurrentGame().setGamePlayer(mMessage.getPlayer());
                        mMasterSocket.setGame(mMessage.getCurrentGame());

                    }else if (mMessage.getMessage().equals("wait_game")) {
                        mMessage.getCurrentGame().setGamePlayer(mMessage.getPlayer());
                        mMasterSocket.setGame(mMessage.getCurrentGame());
                    }else if (mMessage.getMessage().equals("open_game")) {
                        mMessage.getCurrentGame().setGamePlayer(mMessage.getPlayer());
                        mMasterSocket.setGame(mMessage.getCurrentGame());
                    }else if (mMessage.getMessage().equals("first_bet")) {
                        Game tmpGame = mMessage.getCurrentGame();
                        Player tmpPlayer = mMessage.getPlayer();
                        if (tmpGame.getDeck().isOpen()) {
                            //Game is Open
                            tmpPlayer.setMoney(tmpPlayer.getMoney() - tmpGame.getDeck().getCurrentBet());
                            mMessage.setPlayer(tmpPlayer);
                            mMessage.setMessage("response_first_bet");
                            tmpGame.getDeck().setMoney(tmpGame.getDeck().getMoney() + tmpGame.getDeck().getCurrentBet());
                        } else {
                            tmpPlayer.setMoney(tmpPlayer.getMoney() - 1);
                            mMessage.setPlayer(tmpPlayer);
                            mMessage.setMessage("response_first_bet");
                            tmpGame.getDeck().setMoney(1);
                            tmpGame.getDeck().setCurrentBet(1);
                            tmpGame.getDeck().setOpen(true);
                        }
                        /** HashMap<Integer, Card> tmpCards = tmpPlayer.getHand().displayAll();
                         for (Integer key : tmpCards.keySet()) {
                         Card tmpCard = tmpCards.get(key);
                         tmpCard.setHoldCard(false);
                         tmpPlayer.getHand().displayAll().put(key, tmpCard);
                         }*/
                        tmpPlayer.setClientResponse(true);
                        mMessage.setPlayer(tmpPlayer);
                        mMessage.setCurrentGame(tmpGame);
                        mMasterSocket.setGame(tmpGame);
                    } else if (mMessage.getMessage().equals("turn_over_players")) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Game tmpGame = mMessage.getCurrentGame();
                        Player tmpPlayer = mMessage.getPlayer();
                        if (tmpGame.getDeck().isOpen()) {
                            //Game is Open
                            tmpPlayer.setMoney(tmpPlayer.getMoney() - tmpGame.getDeck().getCurrentBet());
                            mMessage.setPlayer(tmpPlayer);
                            mMessage.setMessage("response_turn_player");
                            tmpGame.getDeck().setMoney(tmpGame.getDeck().getMoney() + tmpGame.getDeck().getCurrentBet());
                        } else {
                            tmpPlayer.setMoney(tmpPlayer.getMoney() - 1);
                            mMessage.setPlayer(tmpPlayer);
                            mMessage.setMessage("response_turn_player");
                            tmpGame.getDeck().setMoney(1);
                            tmpGame.getDeck().setCurrentBet(1);
                            tmpGame.getDeck().setOpen(true);
                        }
                        tmpPlayer.getHand().getCard(1).setHoldCard(true);
                        tmpPlayer.getHand().getCard(2).setHoldCard(true);
                        tmpPlayer.getHand().getCard(3).setHoldCard(true);
                        tmpPlayer.getHand().getCard(4).setHoldCard(true);
                        tmpPlayer.setClientResponse(true);
                        mMessage.setPlayer(tmpPlayer);
                        mMessage.setCurrentGame(tmpGame);
                        mMasterSocket.setGame(tmpGame);
                    } else {
                        if (Gdx.app != null) Gdx.app.log("debug", "New Incoming Message");
                        if (Gdx.app != null) Gdx.app.log("debug", line);
                    }
                } catch (JsonSyntaxException e) {
                    if (Gdx.app != null) Gdx.app.log("error", "Message is not a message");
                }
                sendMessageToClient(mMessage);

                /**
                 * workflow
                 * 1) Authenticate Client
                 * 2) Response with game->player
                 * 3) initial game procedure
                 *
                 * */
                   /* String testCryptedCommandFromClient=  Base64Coder.encodeString(line);
                    Gdx.app.log("debug",testCryptedCommandFromClient);
                    oBW.write("Crypted Command is: "+testCryptedCommandFromClient+"\n");
                    oBW.flush();
                    String testDecryptedCommandFromClient= Base64Coder.decodeString(testCryptedCommandFromClient);
                    //Receive command from Slave Crypted
                    Gdx.app.log("debug",testDecryptedCommandFromClient);
                    //String str = new String ();

                    oBW.write("Decrypted Command is: "+testDecryptedCommandFromClient+"\n");
                    oBW.flush();
*/
            }
            oBW.close();
            oBR.close();
        } catch (IOException e) {
            if (Gdx.app != null) Gdx.app.log("error", "Cluster Channel communication error!");
            if (Gdx.app != null) Gdx.app.log("error", e.getMessage());
        }


    }

    private void loginToServer() {
        String auth = Base64Coder.encodeString("TexasHoldemGLM");
        try {
            oBW.write(auth);
            oBW.newLine();
            oBW.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read Method
     */
    private void startThreadLine() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gdx.app.log("debug", "Start Thread line");
                while (true) {
                    //Extract this to another thread
                    //Monitor the Current Game
                    //if(mCurrentRoom!=null && mCurrentRoom.getCurrentPlayerUnicheIDTurn()!=null && mCurrentUmanPlayer!=null) {
                    while (!mCurrentRoom.getCurrentPlayerUnicheIDTurn().equals(mCurrentUmanPlayer.getUnicheID())) {
                        //Wait for my TURN
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //My Turn sent Message To Client
                    sendMessageToClient();

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Extract this to another thread
                    //}
                }
            }
        }).start();
    }


    //Send Message to Client
    private void sendMessageToClient() {
        if (mCurrentRoom == null) return;

        Message tmpMessage = new Message();
        tmpMessage.setCurrentGame(mCurrentRoom);
        tmpMessage.setPlayer(mCurrentUmanPlayer);
        if (mCurrentRoom.getGameStatus() == 2) {
            tmpMessage.setMessage("first_bet");
        } else if (mCurrentRoom.getGameStatus() == 3) {
            tmpMessage.setMessage("turn_over_players");
        } else if (mCurrentRoom.getGameStatus() == 4) {
            tmpMessage.setMessage("final_bet");
        } else if (mCurrentRoom.getGameStatus() == 5) {
            tmpMessage.setMessage("find_winner");
        }

        if (mMessageSended != null && mMessageSended == tmpMessage.getMessage()) return;
        Gdx.app.log("debug", "Send Message to Client from Thread line");

        Gdx.app.log("debug", "Send Message To Client: " + cryptCommand(tmpMessage));
        try {
            oBW.write(cryptCommand(tmpMessage));
            oBW.newLine();
            oBW.flush();
            mMessageSended = tmpMessage.getMessage();

        } catch (IOException e) {
            Gdx.app.log("error", "IOException to Client");
        }
    }

    //Send Message to Client
    private void sendMessageToClient(Message messageToSend) {
        try {
            oBW.write(cryptCommand(messageToSend));
            oBW.newLine();
            oBW.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshCurrentRoom() {

 /*       try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/


    }

    private Message findAvaiableRoom() {
        while (true) {
            Gdx.app.log("debug", "Searching Room in Wait ");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Validate and secure the communication
     */
    private boolean handshake(String line) {
        try {
            String encoded = Base64Coder.decodeString(line);
            if (line.length() > 0
                    && encoded.compareTo("TexasHoldemGLM") == 0) {
                Gdx.app.log("info", "Auth OK");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Gdx.app.log("info", "Auth KO");
            Gdx.app.log("error", e.getMessage());
            return false;
        }
    }

    public String getHostname() {
        if (mSocketClient != null) return mSocketClient.getInetAddress().getHostName();
        else return null;
    }

    public String getHostAddress() {
        if (mSocketClient != null) return mSocketClient.getInetAddress().getHostAddress();
        else return null;
    }

    public int getPort() {
        if (mSocketClient != null) return mSocketClient.getPort();
        else return -1;
    }

    public void sendBet(Player tmpPlayer) {
        tmpPlayer.setStatus(1);
        try {
            if (oBR.ready()) {
                //oBW.write(Base64Coder.encodeString(gson.toJson(tmpPlayer)));
                oBW.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Message setUmanPlayer(Message clientMessage) {
        Message tmpMessage = new Message();

        tmpMessage.setCurrentGame(mCurrentRoom);
        tmpMessage.setPlayer(mCurrentUmanPlayer);
        tmpMessage.setMessage("join_room_ok");
        return tmpMessage;

    }

    private String cryptCommand(Message message) {
        String tmpCommand = gson.toJson(message).replace("\n", "");

        if (Const.CLEAR_COMMAND) {
            //No Crypt Message
            return tmpCommand;
        } else {
            //Crypt Message
            tmpCommand = Base64Coder.encodeString(tmpCommand);
            return tmpCommand;
        }
    }
}
