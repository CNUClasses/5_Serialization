/**
 *
 */
package com.example.keith.a5_serialization;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author lynn
 *         see technique 5-3 in Android Recipes book
 *         this is private storage
 */
public class FileActivityExternal extends Activity {
    // from the Android docs, these are the recommended paths
    private static final String EXT_STORAGE_PATH_PREFIX = "/Android/data/";
    private static final String EXT_STORAGE_FILES_PATH_SUFFIX = "/files/";
    private static final String EXT_STORAGE_CACHE_PATH_SUFFIX = "/cache/";


    private static final String FILENAME = "datafile.txt";
    private static final String TAG = "FileActivityExternal";
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


    /**
     * Use Environment to check if external storage is writable.
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Use environment to check if external storage is readable.
     *
     * @return
     */
    public static boolean isExternalStorageReadable() {
        if (isExternalStorageWritable()) {
            return true;
        }
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    /**
     * Return the recommended external files directory, whether using API level
     * 8 or lower. (Uses getExternalStorageDirectory and then appends the
     * recommended path.)
     *
     * @param packageName
     * @return
     */
    public static File getExternalFilesDirAllApiLevels(final String packageName) {
        return getExternalDirAllApiLevels(packageName, EXT_STORAGE_FILES_PATH_SUFFIX);
    }

    private static File getExternalDirAllApiLevels(final String packageName, final String suffixType) {
        File dir = new File(Environment.getExternalStorageDirectory() + EXT_STORAGE_PATH_PREFIX + packageName + suffixType);
        dir.mkdirs();
        return dir;
    }

    public void doSave(View v) {
        if (isExternalStorageWritable()) {
            File dir = getExternalFilesDirAllApiLevels(this.getPackageName());
            File file = new File(dir, FILENAME);
            writeStringAsFile(et.getText().toString(), file);
            et.setText("");
            etLocation.setText(FileActivityExternal.getExternalFilesDirAllApiLevels(this.getPackageName()).toString());
            etFileName.setText(FILENAME);
        } else {
            etLocation.setText("External storage not writable");
            Log.d(TAG, "External storage not writable");
        }
    }


    public void doGet(View v) {
        if (isExternalStorageReadable()) {
            File dir = getExternalFilesDirAllApiLevels(this.getPackageName());
            File file = new File(dir, FILENAME);
            if (file.exists() && file.canRead()) {
                et.setText(readFileAsString(file));
                etLocation.setText(FileActivityExternal.getExternalFilesDirAllApiLevels(this.getPackageName()).toString());
                etFileName.setText(FILENAME);
                Log.d(TAG, "File read");
            } else {
                Log.d(TAG, "File UNread");
            }
        } else {
            etLocation.setText("External storage not readable");
            Log.d(TAG, "External storage not readable");
        }
    }

    /**
     * Read file as String, return null if file is not present or not readable.
     *
     * @param file
     * @return
     */
    public static String readFileAsString(final File file) {
        StringBuilder sb = null;
        try {
            if ((file != null) && file.canRead()) {
                sb = new StringBuilder();
                String line = null;
                BufferedReader in = new BufferedReader(new FileReader(file), 1024);
                try {
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                } finally {
                    in.close();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading file " + e.getMessage(), e);
        }
        if (sb != null) {
            return sb.toString();
        }
        return null;
    }

    /**
     * Replace entire File with contents of String, return true on success,
     * false on failure.
     *
     * @param fileContents
     * @param file
     * @return
     */
    public static boolean writeStringAsFile(final String fileContents, final File file) {
        boolean result = false;
        try {
            if (file != null) {
                file.createNewFile(); // ok if returns false, overwrite
                Writer out = new BufferedWriter(new FileWriter(file), 1024);
                try {
                    out.write(fileContents);
                } finally {
                    out.close();
                }
                result = true;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error writing string data to file " + e.getMessage(), e);
        }
        return result;
    }
}