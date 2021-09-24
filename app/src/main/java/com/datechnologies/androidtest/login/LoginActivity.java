package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.LoginApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
public class LoginActivity extends AppCompatActivity
{
    private EditText textEmail;
    private EditText textPassword;
    private String email, password;
    private Retrofit retrofit;
    private LoginApi loginApi;
    private final int LOGIN_CODE_SUCCESS = 200;
    private long startTime, stopTime, totalTime;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_password);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://dev.rapptrlabs.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        loginApi = retrofit.create(LoginApi.class);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onLoginClicked(View v)
    {
        email = textEmail.getText().toString();
        password = textPassword.getText().toString();

        // Make sure that fields aren't empty
        if(!email.equals("") && !password.equals(""))
        {
            Call<String> loginCall = loginApi.login(email, password);

            // Get a timestamp just before we send the request
            startTime = SystemClock.elapsedRealtime();
            loginCall.enqueue(new Callback<String>()
            {
                @Override
                public void onResponse(Call<String> call, Response<String> response)
                {
                    // Get a timestamp when we get a response
                    stopTime = SystemClock.elapsedRealtime();
                    // Total time for the request is the difference between the two timestamps
                    totalTime = stopTime - startTime;

                    if(response.code() == LOGIN_CODE_SUCCESS)
                    {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Login Successful!")
                                .setMessage("Response Code: " + response.code() +
                                            "\nResponse Message: " + response.message() +
                                            "\nAPI Call took " + totalTime + " milliseconds.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                                {
                                    // Navigate back to MainActivity the same way we navigated here
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        MainActivity.start(LoginActivity.this);
                                    }
                                })
                                .show();
                    }
                    // We got a response back, but not a successful one
                    else
                    {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Login unsuccessful!")
                                .setMessage("Response Code: " + response.code() +
                                            "\nResponse Message: " + response.message() +
                                            "\nPlease check your login credentials and try again." +
                                            "\nAPI Call took " + totalTime + " milliseconds.")
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable)
                {
                    System.out.println("Failed to get anything back");
                }
            });
        }
        else
        {
            // One of the fields was empty, warn the user with a toast
            Toast.makeText(getApplicationContext(), "Please enter a valid email and password.", Toast.LENGTH_SHORT).show();
        }
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
}
