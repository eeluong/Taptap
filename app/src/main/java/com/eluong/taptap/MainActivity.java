package com.eluong.taptap;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private Button ourButton;
    private TextView ourMessage;
    private int numTimesClicked = 0;
    private String result = "Failed to run enable Taptap feature !!!";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // link the button to tesxt view
        ourButton = (Button) findViewById(R.id.button);
        ourMessage = (TextView) findViewById(R.id.textView);

        // Check to see if the tap tap feature is enable ?
        if ( isTaptapEnable() == true) {
            result = "Taptap feature is enabled !!!";
        } else
            result = "Taptap feature is not enable !!!";

        ourMessage.setText(result);

        // call back function for click botton
        View.OnClickListener tapOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activate the code
                // echo 1 > /sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture #
                // cat /sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture #
                if ( runSUCommand("echo 1 > /sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture # \n") == true) {
                    numTimesClicked++;
                    result = "Successfully enable Taptap feature.";
                    //if (numTimesClicked != 1) {
                    //    result += "s";
                    //}
                }
                ourMessage.setText(result);
            }
        };

        ourButton.setOnClickListener(tapOnClickListener);

        // the follwoing code is to setup the setting menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private boolean runSUCommand(String command) {
        try {
            String line;
            Process process = Runtime.getRuntime().exec("su");
            OutputStream stdin = process.getOutputStream();
            InputStream stderr = process.getErrorStream();
            InputStream stdout = process.getInputStream();

            stdin.write((command).getBytes());
            stdin.write("exit\n".getBytes());
            stdin.flush();

            stdin.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            while ((line = br.readLine()) != null) {
                Log.d("[Taptap Output]", line);
            }
            br.close();
            br = new BufferedReader(new InputStreamReader(stderr));
            while ((line = br.readLine()) != null) {
                Log.e("[Taptap Error]", line);
            }
            br.close();

            process.waitFor();
            process.destroy();
            return true;

        }
        catch (Exception ex) {
            return false;
        }

    }

    private boolean isTaptapEnable() {
        try {
            String line;
            boolean result=false;
            Process process = Runtime.getRuntime().exec("su");
            OutputStream stdin = process.getOutputStream();
            InputStream stderr = process.getErrorStream();
            InputStream stdout = process.getInputStream();

            stdin.write(("cat / sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture # \n" ).getBytes());
            stdin.write("exit\n".getBytes());
            stdin.flush();

            stdin.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            // check to out string for 1 or 0
            line = br.readLine();
            if (line.equals("1")) {
                result = true;
            } else
                result = false;
            while ((line = br.readLine()) != null) {
                Log.d("[isTaptap Output]", line);
            }
            br.close();
            br = new BufferedReader(new InputStreamReader(stderr));
            while ((line = br.readLine()) != null) {
                Log.e("[isTaptap Error]", line);
            }
            br.close();

            process.waitFor();
            process.destroy();
            return result;

        }
        catch (Exception ex) {
            return false;
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
            // pop up toast message the settings got tapped
            Toast toastMessage = Toast.makeText(this, "Tap Tap Tap Tap ...", Toast.LENGTH_LONG);
            toastMessage.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.eluong.taptap/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.eluong.taptap/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
