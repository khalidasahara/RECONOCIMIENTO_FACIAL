package com.videojavacv.jal.reconocimineto_facial;

/**
 * Created by jal on 24/06/16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class RECONOCIMIENTOFACIALActivity extends AppCompatActivity {

    private static final int VIDEO_CAPTURE = 101;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconocimientofacial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button reconocerButton = (Button) findViewById(R.id.reconocer);
        if (!hasCamera())
            reconocerButton.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reconocimientofacial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        try {
            if (requestCode == VIDEO_CAPTURE) {
                if (resultCode == RESULT_OK) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getBaseContext(), "Video guardado en " + data.getDataString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else if (resultCode == RESULT_CANCELED) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getBaseContext(), "Grabación de video ha sido cancelada.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getBaseContext(), "Fallo al guardar el video.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "Exception " + e.toString(), Toast.LENGTH_LONG).show();
                }
            });
            Log.e("Exception ", e.toString());
        }
    }

    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY)) {
            return true;
        } else {
            return false;
        }
    }

    public void takePhoto(View v) {
        Intent myIntent = new Intent(RECONOCIMIENTOFACIALActivity.this, AprenderActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        RECONOCIMIENTOFACIALActivity.this.startActivity(myIntent);
    }

    public void grabar(View v) {
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/CAPTURAS";
        File newdir = new File(dir);
        newdir.mkdirs();
        String file = dir + "/video.mp4";
        File mediaFile = new File(file);
        try {
            mediaFile.createNewFile();
        } catch (IOException e) {
        }
        fileUri = Uri.fromFile(mediaFile);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    public void reconocer(View v) {
        runOnUiThread(new Runnable() {
            public void run() {
                Intent myIntent = new Intent(RECONOCIMIENTOFACIALActivity.this, ReconocerActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                RECONOCIMIENTOFACIALActivity.this.startActivity(myIntent);
            }
        });
    }

}