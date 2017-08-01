package com.example.lrh.sofare;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lrh.sofare.database.MediaDB;
import com.example.lrh.sofare.database.NoteDB;
import com.example.lrh.sofare.layoutAnimation.LayoutAnimationUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAddNote, btnCancel, btnAddPhoto, btnAddVedio;
    private EditText etTitle, etContent;
    private ImageView showSmallPictureIV;
    private NoteDB db;
    private MediaDB mediaDB;
    private String picturePath;
    private Bitmap smallPicture;

    public static final String EXTRA_NOTE_ID = "noteID";
    public static final String EXTRA_NOTE_TITLE = "noteTitle";
    public static final String EXTRA_NOTE_CONTENT = "noteContent";
    public static final String EXTRA_MEDIA_PICTURE_PATH = "mediaPicturePath";
    public static final String EXTRA_MEDIA_ID = "mediaID";

    private static final int TAKE_PICTURE_REQUEST_CODE = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 3;
    private int noteID = -1;
    private int mediaID = -1;

    private LinearLayout ly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        init();
        setLayoutAnimation();


        mediaID = getIntent().getIntExtra(EXTRA_MEDIA_ID, -1);
        noteID = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);
        String title = getIntent().getStringExtra(EXTRA_NOTE_TITLE);
        String content = getIntent().getStringExtra(EXTRA_NOTE_CONTENT);
        picturePath = getIntent().getStringExtra(EXTRA_MEDIA_PICTURE_PATH);
        if (noteID > -1) {
            etTitle.setText(title);
            etContent.setText(content);
            if (picturePath != null) {
                smallPicture = BitmapFactory.decodeFile(picturePath);
                showSmallPictureIV.setImageBitmap(smallPicture);
            }
        }
    }

    private void init() {
        btnAddNote = (Button) findViewById(R.id.btn_savenote);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnAddPhoto = (Button) findViewById(R.id.btn_addPhoto);
        btnAddVedio = (Button) findViewById(R.id.btn_addVedio);
        etTitle = (EditText) findViewById(R.id.edit_title);
        etContent = (EditText) findViewById(R.id.edit_content);
        showSmallPictureIV = (ImageView) findViewById(R.id.showSmallPictureIV);
        ly = (LinearLayout) findViewById(R.id.btnLinearLayout);

        btnAddNote.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAddPhoto.setOnClickListener(this);
        btnAddVedio.setOnClickListener(this);

        db = new NoteDB(this);
        db.openDB();
        mediaDB = new MediaDB(this);
        mediaDB.openDB();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_savenote:
                //参数说明：需要添加动画的View,起始位置中心点x值，y值 ，动画开始半径，动画结束半径
//                Animator myAnimator = ViewAnimationUtils.createCircularReveal(view, 0, 0, 0, view.getWidth());
                //设置动画时间
//                myAnimator.setDuration(500);
                //打开动画
//                myAnimator.start();
                if (noteID > -1) {
                    db.upateOneNote(noteID, etTitle.getText().toString()
                            , etContent.getText().toString()
                            , new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss").format(new Date()));
//                    mediaDB.updateOneMedia(mediaID, noteID, picturePath);

                } else {
                    long saveData = db.insertOneNote(etTitle.getText().toString()
                            , etContent.getText().toString()
                            , new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss").format(new Date()));
//                    Cursor c = db.queryAllNotes();
//                    c.moveToLast();
//                    noteID = c.getInt(c.getColumnIndex(NoteDB.NOTE_ID));
//                    mediaDB.insertOneMedia(noteID, picturePath);


                }

                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btn_cancel:
                setResult(RESULT_OK);
                db.deleteOneNote(noteID);
                finish();
                break;
            case R.id.btn_addPhoto:
                takePhoto();
                break;

            default:
                break;
        }

    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void takeAndSavePicture() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "/photo");
        if (!f.exists()) {
            f.mkdir();
        }
        File dir = new File(f, System.currentTimeMillis() + "jpg");
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(dir));
        picturePath = f.getAbsolutePath();
        startActivityForResult(i, TAKE_PICTURE_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case RESULT_OK:
//                smallPicture = BitmapFactory.decodeFile(picturePath);
//                showSmallPictureIV.setImageBitmap(smallPicture);
//        }
//
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            showSmallPictureIV.setImageBitmap(imageBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        db.closeDB();
        mediaDB.closeDB();
        super.onDestroy();
    }

    private void setLayoutAnimation() {
        ScaleAnimation s = new ScaleAnimation(0, 1, 0, 1);
        s.setDuration(500);
        LayoutAnimationUtil.setLayoutAnimation(s, 0.5f, ly);
    }


}
