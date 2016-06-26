package com.videojavacv.jal.reconocimineto_facial;

/**
 * Created by jal on 24/06/16.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class AprenderActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "AprenderActivity ";
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    EditText nameUser;
    String imagen = "imagen";

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


    /*public void takephoto(View v){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);


        }
    }*/


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
               /* final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/PHOTOS/";
                File newdir = new File(dir);
                newdir.mkdirs();
                String file = dir + nameUser.getText() + ".jpg";
                File mediaFile = new File(file);
                try {
                    mediaFile.createNewFile();
                } catch (IOException e) {
                }
                Bitmap bitmap;
                try {
                    try (InputStream is = new URL(mediaFile.getName()).openStream()) {
                        bitmap = BitmapFactory.decodeStream(is);
                    }
                    //Bitmap bm = BitmapFactory.decodeFile(mediaFile.getName());
                    bitmap = Bitmap.createScaledBitmap(bitmap, 600, 400, true);
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, file, "");
                    imageview.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }*/
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (data != null) {
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");

                            imageview.setImageBitmap(imageBitmap);
                        } else {
                            // Get the dimensions of the View
                            int targetW = imageview.getWidth();
                            int targetH = imageview.getHeight();

                            // Get the dimensions of the bitmap
                            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                            bmOptions.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(mediaFile.getPath(), bmOptions);
                            int photoW = bmOptions.outWidth;
                            int photoH = bmOptions.outHeight;

                            // Determine how much to scale down the image
                            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

                            // Decode the image file into a Bitmap sized to fill the View
                            bmOptions.inJustDecodeBounds = false;
                            bmOptions.inSampleSize = scaleFactor;
                            bmOptions.inPurgeable = true;

                            Bitmap bitmap = BitmapFactory.decodeFile(mediaFile.getPath(), bmOptions);
                            imageview.setImageBitmap(bitmap);
                            String s = "data vacio " + mediaFile.getPath();
                            //Toast.makeText(getBaseContext(), TAG + s, Toast.LENGTH_LONG).show();
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
