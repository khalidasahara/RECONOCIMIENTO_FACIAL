package com.videojavacv.jal.reconocimineto_facial;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

/**
 * Created by jal on 26/06/16.
 */
public class ReconocerActivity extends AppCompatActivity {
    private static final int VIDEO_CAPTURE = 101;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reconocerlayout);
        final String dir;
        File newdir;
        final VideoView videoview;
        String file = null;
        try {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/CAPTURAS";
            newdir = new File(dir);
            newdir.mkdirs();
            file = dir + "/video.mp4";
            File mediaFile = new File(file);
            mediaFile.createNewFile();
        } catch (IOException e) {
        }
        try {
            videoview = (VideoView) findViewById(R.id.videoview);
            videoview.setVideoPath(file);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoview);
            mediaController.setMediaPlayer(videoview);
            videoview.requestFocus();
            videoview.setLayerType(View.LAYER_TYPE_NONE, null);
            videoview.setZOrderOnTop(true);
            videoview.start();
            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    videoview.start();
                }
            });
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
