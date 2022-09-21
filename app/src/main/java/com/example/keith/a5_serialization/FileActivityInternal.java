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
        setFileLoc();
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
}
