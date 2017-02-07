package com.example.keith.a5_serialization;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_file, menu);
        return true;
    }

    private void setFileLoc() {
        etLocation.setText(this.getFilesDir().getAbsolutePath());
        etFileName.setText(FILENAME);
    }

    public void doSave(View v) {
        String data = et.getText().toString();

        FileOutputStream fos = null;
        try {
            // note that there are many modes you can use
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            try {
                fos.write(data.getBytes());
            } finally {
                fos.close();
                et.setText("");
                setFileLoc();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found", e);
        } catch (IOException e) {
            Log.e(TAG, "IO problem", e);
        }
    }

    public void doGet(View v) {
        FileInputStream fis = null;
        Scanner scanner = null;
        StringBuilder sb = new StringBuilder();
        try {
            fis = openFileInput(FILENAME);
            scanner = new Scanner(fis);
            try {
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine());
                }
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        //why bother?
                    }
                }
                if (scanner != null) {
                    scanner.close();
                }
                et.setText(sb.toString());
                setFileLoc();
            }

        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found", e);
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
