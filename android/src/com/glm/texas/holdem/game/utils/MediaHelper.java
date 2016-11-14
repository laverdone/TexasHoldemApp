package com.glm.texas.holdem.game.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.glm.texas.holdem.game.Const;
import com.glm.texas.holdem.game.R;

/**
 * Created by gianluca on 08/08/16.
 */
public class MediaHelper extends MediaPlayer {
    private static MediaPlayer mMediaPlayer = null;


    public static void clickSound(Context context) {
        try {

            SharedPreferences sharedPref = context.getSharedPreferences(
                    Const.PREF_FILE, Context.MODE_PRIVATE);
            boolean isSoundGame = false;
            isSoundGame = sharedPref.getBoolean(context.getString(R.string.game_sound), isSoundGame);

            if (!isSoundGame) return;
            mMediaPlayer = MediaPlayer.create(context, R.raw.click);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setVolume(1, 1);
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exitSound(Context context) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    Const.PREF_FILE, Context.MODE_PRIVATE);
            boolean isSoundGame = false;
            isSoundGame = sharedPref.getBoolean(context.getString(R.string.game_sound), isSoundGame);

            if (!isSoundGame) return;
            endAll(context);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mMediaPlayer = MediaPlayer.create(context, R.raw.exit);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setVolume(1, 1);
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playBackgroundMusic(Context context, boolean stop) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                Const.PREF_FILE, Context.MODE_PRIVATE);
        boolean isSoundGame = false;
        isSoundGame = sharedPref.getBoolean(context.getString(R.string.game_sound), isSoundGame);

        if (!isSoundGame) return;
        endAll(context);
        mMediaPlayer = MediaPlayer.create(context, R.raw.backgroud_music);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setVolume(0.5f, 0.5f);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    public static void endAll(Context context) {

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


}
