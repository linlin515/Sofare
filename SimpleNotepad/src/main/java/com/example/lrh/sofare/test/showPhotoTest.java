package com.example.lrh.sofare.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lrh.sofare.R;

import java.io.File;
import java.io.IOException;

import static java.lang.System.currentTimeMillis;

public class showPhotoTest extends AppCompatActivity {

    private Button photoTest = null;
    private ImageView imageView = null;
    private String currentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo_test);
        init();
    }

    private void init() {
        photoTest = (Button) findViewById(R.id.btn_test);
        imageView = (ImageView) findViewById(R.id.showPhotoTest);
        photoTest.setOnClickListener(new btnClickListener());

    }

    private class btnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
           Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(i, 1);
            File f = new File(getMediaDir(), System.currentTimeMillis() + "jpg");
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d("调试", "1 ");
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            Log.d("调试", "2 ");
            currentPath = f.getAbsolutePath();
            Log.d("调试",currentPath);
            startActivityForResult(i, 1);
            Log.d("调试", "3");

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                Bitmap bm = BitmapFactory.decodeFile(currentPath);
                imageView.setImageBitmap(bm);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File getMediaDir(){
        File dir = new File(Environment.getExternalStorageDirectory()+"/photo");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
}
