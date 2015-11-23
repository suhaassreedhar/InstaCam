package com.example.suhaas.instacam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private UiLifecycleHelper mUiLifecycleHelper;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton loginButton = (LoginButton)findViewById(R.id.loginButton);
        loginButton.setReadPermissions("user_birthday");

        mUiLifecycleHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState sessionstate, Exception e) {
                onSessionStateChanged(session, sessionstate, e);
            }
        });

        mUiLifecycleHelper.onCreate(savedInstanceState);
    }

    private void onSessionStateChanged(final Session session, SessionState sessionState, Exception e){
        if (sessionState.isOpened()){

            List<String> permissions = Session.getActiveSession().getPermissions();

            boolean hasBirthdayPermission = false;
            for (String permission:permissions){
                if (permission.equals("user_birthday")){
                    hasBirthdayPermission = true;
                }
            }

            if (!hasBirthdayPermission){
                Session.NewPermissionsRequest permissionRequest = new Session.NewPermissionsRequest(this, "user_birthday");
                session.requestNewReadPermissions(permissionRequest);
                return;
            }

            Bundle parameters = new Bundle();
            parameters.putString("fields", "picture,first_name,last_name,birthday");
            Request request = new Request(session, "/me", parameters, HttpMethod.GET, new Request.Callback() {
                @Override
                public void onCompleted(Response response) {
                    if (session == Session.getActiveSession()){
                        if (response.getGraphObject() != null){
                            GraphObject graphObject = response.getGraphObject();
                            User.setCurrentUser(graphObject);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                    if (response.getError() != null){
                        Log.d(TAG, "Error is "+response.getError());
                    }
                }
            });

            request.executeAsync();
        }else{

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUiLifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUiLifecycleHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUiLifecycleHelper.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUiLifecycleHelper.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUiLifecycleHelper.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

}
