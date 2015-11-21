package com.example.suhaas.instacam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.File;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener{

    private static final int CAMERA_REQUEST = 10;
    private static final String TAG = "MainActivity";
    private File mPhoto;
    private FeedFragment mFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        MaterialTabHost tabBar = (MaterialTabHost)findViewById(R.id.tab_bar);
        tabBar.addTab(tabBar.newTab().setText("HOME").setTabListener(this));
        tabBar.addTab(tabBar.newTab().setText("PROFILE").setTabListener(this));

        mFeedFragment = (FeedFragment) getFragmentManager().findFragmentById(R.id.feed_container);
        if (mFeedFragment == null) {
            mFeedFragment = new FeedFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.feed_container, mFeedFragment)
                    .commit();
        }
    }

    @Override
    public void onTabSelected(MaterialTab tab) {

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    public void onClick(View v){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        mPhoto = new File(directory, "sample.jpeg");
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoto));
        startActivityForResult(i, CAMERA_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST){
            if (resultCode == RESULT_OK){
                Log.d(TAG, "We took a picture");

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(mPhoto), "image/jpeg");
                startActivity(i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
