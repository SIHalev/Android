package com.stoyan.camera2email;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;

public class MainActivity extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File pictureStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "App");

        uri = Uri.fromFile(new File(pictureStorageDir.getPath() + File.separator + "image" + ".jpg"));

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //tekePictureIntent.putExtra(mediaStore.Extra_output, Uei.fromFile(photoFile);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);

            //Bitmap bJPGcompress = codec(b, Bitmap.CompressFormat.JPEG, 3);

            Intent mail = new Intent(Intent.ACTION_SEND);
            mail.putExtra(Intent.EXTRA_EMAIL, new String[]{"androidtestmailsend@gmail.com"});
            //pass: piemisebira (pie mi se bira)
            mail.putExtra(Intent.EXTRA_SUBJECT, "TEST");
            mail.putExtra(Intent.EXTRA_TEXT, "GL");
            mail.putExtra(Intent.EXTRA_STREAM, uri);

            mail.setType("text/plain");
            mail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");

            startActivity(mail);
            //finish();
        }
    }
}
