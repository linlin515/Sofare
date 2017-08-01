package com.example.lrh.sofare;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.lrh.sofare.database.MediaDB;
import com.example.lrh.sofare.database.NoteDB;

public class MainActivity extends ListActivity implements View.OnClickListener {

    private NoteDB db;
    private MediaDB mediaDB;
    //    private SQLiteDatabase dbRead, dbWrite;
    private CursorAdapter adapter;
    private ImageButton addNote;
    private TextView showTitleTV, showContentTV;
    private Cursor cv;

    public static final int REQEST_CODE_ADD_NOTE = 1;
    public static final int REQEST_CODE_EDIT_NOTE = 1;
    public static int height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_show);
        init();
    }


    private void init() {
        addNote = (ImageButton) findViewById(R.id.btn_addNote);
        addNote.setOnClickListener(this);

        db = new NoteDB(this);
        db.openDB();
        mediaDB = new MediaDB(this);
        mediaDB.openDB();
//        cv = db.queryAllNotes();


        adapter = new SimpleCursorAdapter(this
                , R.layout.notes_list_cell
                , null
                , new String[]{NoteDB.NOTES_TITLE, NoteDB.NOTES_DATE}
                , new int[]{R.id.showNotesTitleTV, R.id.showNotesTimeTV});
//                , new String[]{mediaDB.MEDIA_PICTURE}
//                , new int[]{R.id.showNotesTitleTV});
                setListAdapter(adapter);


        refreshNotesList();

        getHeight();
        setLongClickListener();
    }

    private void setLongClickListener() {
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, final long l) {
                Log.d("调试", "onItemLongClick: " + position);
                new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                        .setMessage("确认删除这条日志")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deleteOneNote((int) l);
                                refreshNotesList();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        startActivityForResult(intent, REQEST_CODE_ADD_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQEST_CODE_ADD_NOTE:
                if (requestCode == EditNoteActivity.RESULT_OK) {
                    refreshNotesList();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshNotesList() {
        adapter.changeCursor(db.queryAllNotes());
//        adapter.changeCursor(mediaDB.queryAllMedia());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        cv = adapter.getCursor();
        cv.moveToPosition(position);
        Log.d("调试3", "onListItemClick: " + position);

        Intent i = new Intent(MainActivity.this, EditNoteActivity.class);
        i.putExtra(EditNoteActivity.EXTRA_NOTE_ID, cv.getInt(cv.getColumnIndex(NoteDB.NOTE_ID)));
        i.putExtra(EditNoteActivity.EXTRA_NOTE_TITLE, cv.getString(cv.getColumnIndex(NoteDB.NOTES_TITLE)));
        i.putExtra(EditNoteActivity.EXTRA_NOTE_CONTENT, cv.getString(cv.getColumnIndex(NoteDB.NOTES_CONTENT)));
//        i.putExtra(EditNoteActivity.EXTRA_MEDIA_PICTURE_PATH, cv.getString(cv.getColumnIndex(mediaDB.MEDIA_PICTURE)));
//        i.putExtra(EditNoteActivity.EXTRA_MEDIA_ID, cv.getInt(cv.getColumnIndex(mediaDB.MEDIA_ID)));

        startActivityForResult(i, REQEST_CODE_EDIT_NOTE);

        super.onListItemClick(l, v, position, id);
    }


    private int getHeight() {
        Display display = getWindowManager().getDefaultDisplay();
        height = display.getHeight();
        return height;
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshNotesList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.closeDB();
        mediaDB.closeDB();
    }


//    @Override
//    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Log.d("调试", "onItemLongClick: " + "有长按");
//        new AlertDialog.Builder(MainActivity.this).setTitle("提示")
//                .setMessage("确认删除这条日志")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                }).show();
//        return true;
//    }




}
