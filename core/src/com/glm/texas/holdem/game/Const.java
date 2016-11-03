package com.glm.texas.holdem.game;

/**
 * Created by gianluca on 11/07/16.
 */
public class Const {
    //PRODUCTION public final static String HOST_NAME="texasholdem.ns0.it";
    public final static String HOST_NAME="192.168.1.76";
    public final static int HOST_PORT=32100;

    public final static int MIN_OPEN_GAME=4100;

    /**
     * poker point
     * */
    public final static int HIGH_CARD=3000;
    public final static int ONE_PAIR=4000;
    public final static int TWO_PAIR=5000;
    public final static int TRIS=6000;
    public final static int FULL_HOUSE=7000;
    public final static int FLUSH=8000;
    public final static int POKER=9000;
    public final static int STRAIGHT=10000;
    public final static int STRAIGHT_MIDDLE=11020;
    public final static int STRAIGHT_MIN=11010;
    public final static int STRAIGHT_MAX=11000;



    public static final int STEP_TIMEOUT = 5000;

    public static final int INITIAL_MONEY = 10;

    public static final boolean DEBUG=false;

    public static final boolean CLEAR_COMMAND =true;
    public static final float MY_CARD_OFFSET = 4f;
}
