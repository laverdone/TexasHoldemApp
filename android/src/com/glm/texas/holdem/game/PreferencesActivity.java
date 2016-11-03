package com.glm.texas.holdem.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.glm.texas.holdem.game.utils.BillingHelper;
import com.glm.texas.holdem.game.utils.MediaHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;


/**
 * Activity for preferences
 *
 * */
public class PreferencesActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private BillingHelper mServiceConn = new BillingHelper();

    private Switch mSoundGame = null;
    private Button mGooglePlaySignOut = null;

    private final int RC_SIGN_IN = 9001;

    // Client used to interact with Google APIs.
    private GoogleApiClient mGoogleApiClient;

    // Are we currently resolving a connection failure?
    private boolean mResolvingConnectionFailure = false;

    // Has the user clicked the sign-in button?
    private boolean mSignInClicked = false;

    // Set to true to automatically start the sign in flow when the Activity starts.
    // Set to false to require the user to click the button in order to sign in.
    private boolean mAutoStartSignInFlow = true;

    private SharedPreferences sharedPref = null;
    private boolean isSoundGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        isSoundGame = sharedPref.getBoolean(getString(R.string.game_sound), isSoundGame);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .setViewForPopups(findViewById(android.R.id.content))
                .addApi(AppIndex.API).build();

        //mGooglePlaySignIn = (SignInButton) findViewById(R.id.sign_in_button);
        mSoundGame = (Switch) findViewById(R.id.enable_sound_btn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mSoundGame.setChecked(isSoundGame);
        }
        mSoundGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.game_sound), mSoundGame.isChecked());
                editor.commit();
                if (!mSoundGame.isChecked()) {
                    MediaHelper.endAll(getApplicationContext());
                } else {
                    MediaHelper.playBackgroundMusic(getApplicationContext(), false);
                }
            }
        });
        mGooglePlaySignOut = (Button) findViewById(R.id.sign_in_button);

       /* mGooglePlaySignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignInClicked = true;
                try {
                    if(mGoogleApiClient.isConnected()){
                        Games.signOut(mGoogleApiClient);
                        mGoogleApiClient.disconnect();
                    }else{
                        mGoogleApiClient.connect();
                        Log.d(this.getClass().getCanonicalName(),"Try to Connect");
                    }
                } catch (Exception e) {
                    Log.e(this.getClass().getCanonicalName(), "Error Connect to Google service");
                    e.printStackTrace();
                }

                *//**
         *  mSignInClicked = false;
         Games.signOut(mGoogleApiClient);
         mGoogleApiClient.disconnect();
         *//*
            }
        });*/


        mGooglePlaySignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (mGoogleApiClient.isConnected()) {
                        mSignInClicked = false;
                        Games.signOut(mGoogleApiClient);
                        mGoogleApiClient.disconnect();
                        mGooglePlaySignOut.setText("Sign In");
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("playgames",false);
                        editor.commit();
                    } else {
                        mSignInClicked = true;
                        mGoogleApiClient.connect();
                        Log.d(this.getClass().getCanonicalName(), "Try to Connect");
                        Vibrator vVib = (Vibrator) PreferencesActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        vVib.vibrate(500);
                    }
                } catch (Exception e) {
                    Log.e(this.getClass().getCanonicalName(), "Error Connect to Google service");
                    e.printStackTrace();
                }
            }
        });

        mGooglePlaySignOut.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        v.setBackground(getDrawable(R.drawable.buttonshapesel));
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        v.setBackground(getDrawable(R.drawable.buttonshape));
                    }
                }
                MediaHelper.clickSound(PreferencesActivity.this);
                return false;
            }
        });
        if(sharedPref.getBoolean("playgames",false)) {
            mGoogleApiClient.connect();
        }
//        Games.setViewForPopups(mGoogleApiClient, getWindow().getDecorView().findViewById(android.R.id.content));

        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceConn.mService != null) {
            unbindService(mServiceConn);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mGooglePlaySignOut.setText("Sign Out");
        Log.d(this.getClass().getCanonicalName(), "On Connected");
        if (mGoogleApiClient.isConnected()) {
            mGooglePlaySignOut.setText("Sign Out");
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
        if (mResolvingConnectionFailure) {
            // Already resolving
            return;
        }

        // If the sign in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;
        }

        // Put code here to display the sign-in button
        Log.d(this.getClass().getCanonicalName(), "connection Error Code: " + connectionResult.getErrorCode());
        try {
            connectionResult.startResolutionForResult(this, RC_SIGN_IN);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
        if (mGoogleApiClient.isConnected()) {
            Games.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode,
                                 Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

        switch (requestCode) {

            case RC_SIGN_IN:
                Log.d(this.getClass().getCanonicalName(), "onActivityResult with requestCode == RC_SIGN_IN, responseCode="
                        + responseCode + ", intent=" + intent);
                mSignInClicked = false;
                mResolvingConnectionFailure = false;
                if (responseCode == RESULT_OK) {
                    mGoogleApiClient.connect();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("playgames",true);
                    editor.commit();
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
                    if(mGoogleApiClient.isConnected()){
                        String personId =  Games.Players.getCurrentPlayerId(mGoogleApiClient);
                        String personName = Games.getCurrentAccountName(mGoogleApiClient);
                        //SharedPreferences sharedPref = context.getSharedPreferences(
                        //        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        editor.putString("personName",personName);
                        editor.putString("personId",personId);
                        editor.commit();
                    }
                } else {

                }
                break;
        }
        super.onActivityResult(requestCode, responseCode, intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Preferences Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.glm.texas.holdem.game/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            //switchToScreen(R.id.screen_sign_in);
        } else {
            //switchToScreen(R.id.screen_wait);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            //Games.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Log.d(this.getClass().getCanonicalName(),"Connected to Play");
            mGooglePlaySignOut.setText(R.string.sign_out);
        } else {
            Log.d(this.getClass().getCanonicalName(),"NOT Connected to Play");
            mGooglePlaySignOut.setText(R.string.sign_in);
        }
    }
}
