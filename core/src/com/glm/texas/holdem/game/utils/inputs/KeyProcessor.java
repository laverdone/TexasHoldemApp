package com.glm.texas.holdem.game.utils.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.utils.game.GameWorld;

import java.util.Calendar;

/**
 * Created by gianluca on 08/07/14.
 */
public class KeyProcessor implements InputProcessor {
    private String message;
    private GameWorld mWorld;

    /**time when user click down*/
    private double startTouchTime=0d;
    /**difference from startTouchTime and second Touch MIN==MORE FREQUENCY*/
    private double diffTouchTime=0d;
    /**Arraylist to store the last touch time frequency to calculate the avg */
    private double[] mFrequencies = new double[Const.TOUCH_TO_STORE];
    /**number of touch diff. to Store*/
    private int mCurrentOfTouch=0;
    private double mAvgTouchFrequency=0;
    @Override
    public boolean keyDown(int keycode) {
        message = "keyDown "+keycode;
        Gdx.app.log("INFO", message);
        return keycode != Input.Keys.BACK;

    }

    @Override
    public boolean keyUp(int keycode) {
        message = "keyUP "+keycode;
        Gdx.app.log("INFO", message);

        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        message = "keyTyped "+c;
        Gdx.app.log("INFO", message);
        return false;
    }
    /**get the Avg frequency*/
    public double getAvgFrequency() {

        return mAvgTouchFrequency;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(Const.DEBUG)
            Gdx.app.log(this.getClass().getCanonicalName(), "X:" + screenX + " Y:" + screenY + " POINTER:" + pointer + " BUTTON:" + button);
        /**check the area touch ed the frequency*/
        if(startTouchTime==0) {
            //First TouchDownTime
            startTouchTime = Calendar.getInstance().getTimeInMillis();
        }else{
            //Second TouchDown calculate the difference
            diffTouchTime = Calendar.getInstance().getTimeInMillis()-startTouchTime;
            startTouchTime= Calendar.getInstance().getTimeInMillis();

            if(mCurrentOfTouch<Const.TOUCH_TO_STORE) {
                mFrequencies[mCurrentOfTouch]=diffTouchTime;
                mCurrentOfTouch++;
            }
            else {
                double sum=0;
                for(int i=0;i<Const.TOUCH_TO_STORE;i++){
                    sum+=mFrequencies[i];
                }
                mAvgTouchFrequency=sum/30;
                mCurrentOfTouch=0;
                if(Const.DEBUG) Gdx.app.log(this.getClass().getCanonicalName(),"AVG Frequency is: "+mAvgTouchFrequency);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
