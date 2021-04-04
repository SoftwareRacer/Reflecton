package reflecton.reflecton_android_app;

//
// ReflectonTests.java
// Reflecton
//
// Created by Marco Hennermann on 19.12.17
// Copyright © 2017 Marco Hennermann. All rights reserved.
//

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import reflecton.reflecton_android_app.Configuration;

public class LoginScreen extends AppCompatActivity
{

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 0;
    private static final int UI_ANIMATION_DELAY = 0;
    static String password_q = "";
    static String password;
    static String password_h = "";
    static String email;
    static View view_t;
    private final Handler mHideHandler = new Handler();
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            if (AUTO_HIDE)
            {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable()
    {
        @SuppressLint("InlinedApi")
        @Override
        public void run()
        {

            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private Typeface typeface;
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable()
    {
        @Override
        public void run()
        {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            //if (actionBar != null)
            //{
            //  actionBar.show();
            // }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            hide();
        }
    };

    private void findTextViews(ViewGroup root)
    {
        for (int i = 0; i < root.getChildCount(); i++)
        {
            View child = root.getChildAt(i);
            if (child instanceof TextView)
            {
                ((TextView) child).setTypeface(typeface);
            } else if (child instanceof ViewGroup)
            {
                findTextViews((ViewGroup) child);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_screen);
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        typeface = Typeface.createFromAsset(getAssets(), "font/Heebo.ttf");
        findTextViews((ViewGroup) mContentView);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                toggle();
            }
        });

        TextView changeToSignup = (TextView) findViewById(R.id.changeToSignup);
        changeToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(view.getContext(), SignupScreen.class);
                startActivityForResult(signup, 0);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                finish();
            }
        });
        final Button login_btn = (Button) findViewById(R.id.btn_login);

        login_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Animation bounce = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
                login_btn.startAnimation(bounce);
                LogIn(view);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(0);
    }

    private void toggle()
    {
        if (mVisible)
        {
            hide();
        } else
        {
            show();
        }
    }

    private void hide()
    {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show()
    {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis)
    {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void LogIn(View view)
    {
        view_t = view;
        EditText email_view = (EditText) findViewById(R.id.email);
        EditText pwd_view = (EditText) findViewById(R.id.password);
        email = email_view.getText().toString();
        password = pwd_view.getText().toString();
        //password_h = Encrypt.sha256(password);
        AttemptLogin attemptLogin = new AttemptLogin();
        attemptLogin.execute(email, password);
    }

    private class AttemptLogin extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... args)
        {
            String email = args[0];
            String password = args[1];
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("passwort", password));
            return JsonParser.makeHttpRequest(Configuration.getLoginScriptURL(), "GET", params);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        protected void onPostExecute(JSONObject result)
        {
            try
            {
                if(result.getString("success").equals("1"))
                {
                    Intent settings = new Intent(view_t.getContext(), Current_values.class);
                    startActivityForResult(settings, 0);
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    finish();
                } else
                {
                    Toast.makeText(getApplicationContext(), "Der Benutzername oder das Passwort stimmt nicht überein!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException js)
            {
                js.printStackTrace();

            }
        }
    }
}
