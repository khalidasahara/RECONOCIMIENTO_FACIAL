package com.videojavacv.jal.reconocimineto_facial;


import android.media.MediaPlayer;
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
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
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



        /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("/mnt/sdcard/video.mp4"));
        intent.setDataAndType(Uri.parse("/mnt/sdcard/video.mp4"), "video/mp4");
        startActivity(intent);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reconocimientofacial, menu);
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

    protected void onActivityResult(int requestCode,final  int resultCode,final Intent data) {
        try {
            if (requestCode == VIDEO_CAPTURE) {
                if (resultCode == RESULT_OK) {
                    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/CAPTURAS";
                    File newdir = new File(dir);
                    newdir.mkdirs();
                    String file = dir + "/video.mp4";
                    File mediaFile = new File(file);
                    try {
                        mediaFile.createNewFile();
                    } catch (IOException e) {
                    }
                   /*final Uri videoUri =  data.getData();videoview.setVideoURI(Uri.parse(videoUri.toString()));
                    videoview.setMediaController(new MediaController(this));
                    videoview.requestFocus();
                    videoview.start();*/

                    final VideoView videoview = (VideoView)findViewById(R.id.videoview);
                    try {
                        // Start the MediaController
                        MediaController mediacontroller = new MediaController(
                                this);
                        mediacontroller.setAnchorView(videoview);
                        // Get the URL from String VideoURL
                        Uri video = Uri.parse(file);
                        videoview.setMediaController(mediacontroller);
                        videoview.setVideoURI(video);

                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }

                    videoview.requestFocus();
                    videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        // Close the progress bar and play the video
                        public void onPrepared(MediaPlayer mp) {
                            videoview.start();
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
        }catch(final Exception e){
            runOnUiThread(new Runnable() {
                public void run() {
                    //Toast.makeText(getBaseContext(), "Exception "+e.toString(), Toast.LENGTH_LONG).show();
                }
            });
            Log.e("Exception ",e.toString());
        }
    }

    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        } else {
            return false;
        }
    }

    public void startRecording(View view)
    {
        //Movies/CAPTURAS/video.mp4
        //Guardar en tarjeta
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/CAPTURAS";
        //Guardar en movil
        File newdir = new File(dir);
        newdir.mkdirs();
        String file = dir + "/video.mp4";
        //String file = "/mnt/sdcard/video.mp4";
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

    public void takePhoto(View v){
         Intent myIntent = new Intent(RECONOCIMIENTOFACIALActivity.this, AprenderActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        RECONOCIMIENTOFACIALActivity.this.startActivity(myIntent);
    }
}


