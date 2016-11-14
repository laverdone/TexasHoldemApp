// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.glm.texas.holdem.game.utils;

import com.badlogic.gdx.Gdx;
import com.glm.texas.holdem.game.Const;


public class RearrangeUtils {
    public static float getActorHeight(float i) {
        return Math.round((float) Gdx.graphics.getHeight() / (Const.DEFAULT_HEIGHT / i));
    }

    public static float getActorWidth(float i) {
        return Math.round((float) Gdx.graphics.getWidth() / (Const.DEFAULT_WIDTH / i));
    }

    public static float getOffsetX(float i) {
        return Math.round((i * Gdx.graphics.getWidth()) / Const.DEFAULT_WIDTH);
    }

    public static float getOffsetY(float i) {
        return Math.round((i * Gdx.graphics.getHeight()) / Const.DEFAULT_HEIGHT);
    }

    public static String getResolutionAssets() {
        if (Gdx.graphics.getHeight() > Const.DEFAULT_HEIGHT && Gdx.graphics.getWidth() > Const.DEFAULT_WIDTH)
            return "hd";
        else if (Gdx.graphics.getHeight() < Const.DEFAULT_HEIGHT && Gdx.graphics.getWidth() < Const.DEFAULT_WIDTH)
            return "md";
        else
            return "sd";
    }

    public static float getScaleFactor() {
        if (Gdx.graphics.getHeight() < Const.DEFAULT_HEIGHT)
            return 0.5f;
        else
            return (float) (Gdx.graphics.getHeight() / Const.DEFAULT_HEIGHT);
    }
}
