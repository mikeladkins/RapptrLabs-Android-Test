package com.datechnologies.androidtest.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 * */

public class AnimationActivity extends AppCompatActivity implements View.OnTouchListener
{
    private Button fadeButton;
    private ImageView logoImage;
    // Values to represent the difference between the position of the view
    // And the coordinates that were touched
    private float diffX, diffY;

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        fadeButton = findViewById(R.id.button_fade);
        logoImage = findViewById(R.id.image_logo);
        logoImage.setOnTouchListener(this);


        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Add functionality to the back button on the status bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                //MainActivity.start(this);
                finish();
                return true;
            default:
                return false;
        }
    }

    public void onFadeClicked(View v)
    {
        fadeLogo();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                // Set the diff variables as the difference between the position of the view
                // And where we've touched it
                diffX = view.getX() - motionEvent.getRawX();
                diffY = view.getY() - motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                // Add the difference
                // This helps smooth out the motion
                view.setX(diffX + motionEvent.getRawX());
                view.setY(diffY + motionEvent.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                // Clean up the trails here
                break;
            default:
                return false;
        }
        return true;
    }

    private void fadeLogo()
    {
        // Create an object animator, have it change the 'alpha' property of the image view from 1 to 0
        // Then repeat the process in reverse, from 0 to 1
        // Animation happens over 2 seconds each way
        ObjectAnimator fader = ObjectAnimator.ofFloat(logoImage, "alpha", 1.0f, 0);
        fader.setDuration(2000);
        fader.setRepeatCount(1);
        fader.setRepeatMode(ValueAnimator.REVERSE);
        fader.start();
    }
}
