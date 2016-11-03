package com.glm.texas.holdem.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;


import com.glm.texas.holdem.game.utils.ConstValue;
import com.glm.texas.holdem.game.utils.MediaHelper;
import com.glm.texas.holdem.game.view.TexasDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class AndroidLauncher extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
	private Context mContext =null;
	// Client used to interact with Google APIs.
	private GoogleApiClient mGoogleApiClient;
	private SharedPreferences sharedPref = null;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);

		mContext=this;




		MobileAds.initialize(getApplicationContext(), getString(R.string.home_banner_id));

		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("7437F890CD5ABB529C0526B2159E36DC")
				.build();
//        mAdView.setAdUnitId(getString(R.string.home_banner_id));
//        mAdView.setAdSize(AdSize.SMART_BANNER);
		mAdView.loadAd(adRequest);

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
					MediaHelper.clickSound(mContext);
				}

				return false;
			}
		});
		exit_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
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
					MediaHelper.clickSound(mContext);
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
					MediaHelper.clickSound(mContext);
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


		if(sharedPref.getBoolean("playgames",false)){
			autoLogInPlayGames();
		}

		enableStrictMode();
		MediaHelper.playBackgroundMusic(mContext,false);
	}

	private void autoLogInPlayGames() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(Games.API).addScope(Games.SCOPE_GAMES)
				.setViewForPopups(findViewById(android.R.id.content))
				.addApi(AppIndex.API).build();
		mGoogleApiClient.connect();

	}

	private void enableStrictMode() {
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
	}

	@Override
	public void onBackPressed() {

		final TexasDialog mDialog = new TexasDialog(mContext);
		mDialog.getOk().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MediaHelper.exitSound(mContext);
				finish();
			}
		});

		mDialog.getKo().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
		mDialog.setText("Are you sure exit game?");
		mDialog.show();
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		if (mGoogleApiClient.isConnected()) {
			if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			String personId =  Games.Players.getCurrentPlayerId(mGoogleApiClient);
			String personName = Games.getCurrentAccountName(mGoogleApiClient);
			//SharedPreferences sharedPref = context.getSharedPreferences(
			//        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("personName",personName);
			editor.putString("personId",personId);
			editor.commit();
		}
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

	}
}
