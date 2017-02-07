package com.example.keith.a5_serialization;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author lynn
 *         see technique 5-3 in Android Recipes book
 *         this is private storage
 */
public class FileActivityInternal extends Activity {
    private static final String FILENAME = "datafile.txt";
    private static final String TAG = "datafile.txt";
    private static final String LINE_SEP = System.getProperty("line.separator");
    private String dir;
    private TextView etLocation;
    private TextView etFileName;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_internal);
        et = (EditText) findViewById(R.id.editText1);
        etLocation = (TextView) findViewById(R.id.textView4);
        etFileName = (TextView) findViewById(R.id.textView5);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        Boolean mybool = extras.getBoolean("Some boolean");
        char myChar = extras.getChar("Some char");
        String myString = extras.getString("Some String", "But don't know what it will be");
        long myLong = extras.getLong("Some long");

        setFileLoc();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_file, menu);
        return true;
    }

    private void setFileLoc() {
        dir = this.getFilesDir().getAbsolutePath();
        etLocation.setText(dir);
        etFileName.setText(FILENAME);
    }

    public void doSave(View v) {
        File file = new File(dir, FILENAME);
        KP_fileIO.writeStringAsFile(et.getText().toString(), file);

        et.setText("");
    }

    public void doGet(View v) {
            File file = new File(dir, FILENAME);

            if (file.exists() && file.canRead()) {
                et.setText(KP_fileIO.readFileAsString(file));
                Log.d(TAG, "File read");
            }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }
}
