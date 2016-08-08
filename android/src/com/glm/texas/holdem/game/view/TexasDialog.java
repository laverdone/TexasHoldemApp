package com.glm.texas.holdem.game.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.glm.texas.holdem.game.R;

/**
 * Created by gianluca on 05/08/16.
 */
public class TexasDialog extends Dialog {
    private Button mOk=null;
    private Button mKo=null;
    private Context mContext=null;

    public TexasDialog(final Context context) {
        super(context);
        mContext=context;
        getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimationSlide;
        getWindow().setBackgroundDrawableResource(R.drawable.alert_dialog_roundcorner);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.texas_dialog, null);
        mOk=(Button) dialogView.findViewById(R.id.ok_btn);
        mKo=(Button) dialogView.findViewById(R.id.ko_btn);
        setContentView(dialogView);
        setCancelable(true);
        mOk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        v.setBackground(context.getDrawable(R.drawable.buttonshapesel));
                    }
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        v.setBackground(context.getDrawable(R.drawable.buttonshape));
                    }
                }
                return false;
            }
        });

        mKo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        v.setBackground(context.getDrawable(R.drawable.buttonshapesel));
                    }
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        v.setBackground(context.getDrawable(R.drawable.buttonshape));
                    }
                }
                return false;
            }
        });

    }
    public Button getOk(){
        return mOk;
    }
    public Button getKo(){
        return mKo;
    }

}
