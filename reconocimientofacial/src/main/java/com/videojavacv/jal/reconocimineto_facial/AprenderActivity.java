package com.videojavacv.jal.reconocimineto_facial;

/**
 * Created by jal on 24/06/16.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AprenderActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "AprenderActivity ";
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    EditText nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aprenderlayout);
        nameUser = (EditText) findViewById(R.id.nameUser);
        nameUser.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                nameUser.setText("");
            }
        });

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

    @Override
    public void onClick(View v) {
        runOnUiThread(new Runnable() {
            public void run() {
                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/PHOTOS/";
                File newdir = new File(dir);
                newdir.mkdirs();
                String file = dir + nameUser.getText() + ".jpg";
                File mediaFile = new File(file);
                try {
                    mediaFile.createNewFile();
                } catch (IOException e) {
                }
                try {
                    Uri outputFileUri = Uri.fromFile(mediaFile);
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
                } catch (Exception e) {
                    final String s = e.toString();
                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(getBaseContext(), "onClick " + s, Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.e("onClick ", s);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TAG = "onActivityResult";
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/PHOTOS/";
            final File newdir = new File(dir);
            newdir.mkdirs();
            String file = dir + nameUser.getText() + ".jpg";
            final File mediaFile = new File(file);
            try {
                mediaFile.createNewFile();
            } catch (IOException e) {
            }
            try {
                final ImageView imageview = (ImageView) findViewById(R.id.imageViewPhoto);
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (data != null) {
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            imageview.setImageBitmap(imageBitmap);
                        } else {
                            int targetW = imageview.getWidth();
                            int targetH = imageview.getHeight();
                            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                            bmOptions.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(mediaFile.getPath(), bmOptions);
                            int photoW = bmOptions.outWidth;
                            int photoH = bmOptions.outHeight;
                            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
                            bmOptions.inJustDecodeBounds = false;
                            bmOptions.inSampleSize = scaleFactor;
                            bmOptions.inPurgeable = true;
                            Bitmap bitmap = BitmapFactory.decodeFile(mediaFile.getPath(), bmOptions);
                            imageview.setImageBitmap(bitmap);
                            String s = "data vacio " + mediaFile.getPath();
                            Log.e(TAG, s);
                        }
                    }
                });
            } catch (Exception ex) {
                final String s = ex.toString();
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getBaseContext(), TAG + s, Toast.LENGTH_LONG).show();
                    }
                });
                Log.e(TAG, s);
            }
        } else {
            final String s = "No se ha hecho la imagen";
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), TAG + s, Toast.LENGTH_LONG).show();
                }
            });
            Log.e(TAG, s);
        }
    }

    public void volver(View v) {
        Intent myIntent = new Intent(AprenderActivity.this, RECONOCIMIENTOFACIALActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        AprenderActivity.this.startActivity(myIntent);

    }
}
