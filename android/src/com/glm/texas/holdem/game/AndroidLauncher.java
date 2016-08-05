package com.glm.texas.holdem.game;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import com.glm.texas.holdem.game.utils.ConstValue;
import com.glm.texas.holdem.game.view.TexasDialog;

public class AndroidLauncher extends AppCompatActivity {
	private Context mContext =null;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		if (ConstValue.DEBUG) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads()
					.detectDiskWrites()
					.detectNetwork()   // or .detectAll() for all detectable problems
					.penaltyLog()
					.build());
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects() //or .detectAll() for all detectable problems
                        .penaltyLog()
                        .penaltyDeath()
                        .build());
			}
		}


		setContentView(R.layout.main);
		mContext=this;

		Button exit_btn= ((Button) findViewById(R.id.exit_btn));
		exit_btn.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						v.setBackground(getDrawable(R.drawable.buttonshapesel));
					}
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						v.setBackground(getDrawable(R.drawable.buttonshape));
					}
				}
				return false;
			}
		});
		exit_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final TexasDialog mDialog = new TexasDialog(mContext);
				mDialog.getOk().setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});

				mDialog.getKo().setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mDialog.dismiss();
					}
				});
				mDialog.show();
			}
		});

		Button new_game_btn= ((Button) findViewById(R.id.new_game_btn));
		new_game_btn.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						v.setBackground(getDrawable(R.drawable.buttonshapesel));
					}
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						v.setBackground(getDrawable(R.drawable.buttonshape));
					}
				}
				return false;
			}
		});
		new_game_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Launch the Game
				startActivity(new Intent(getApplicationContext(), GameLauncher.class));
			}
		});

		Button setting_btn= ((Button) findViewById(R.id.setting_btn));
		setting_btn.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						v.setBackground(getDrawable(R.drawable.buttonshapesel));
					}
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						v.setBackground(getDrawable(R.drawable.buttonshape));
					}
				}
				return false;
			}
		});
		setting_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), PreferencesActivity.class));
			}
		});
	}

	@Override
	public void onBackPressed() {
		final TexasDialog mDialog = new TexasDialog(mContext);
		mDialog.getOk().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mDialog.getKo().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
}
