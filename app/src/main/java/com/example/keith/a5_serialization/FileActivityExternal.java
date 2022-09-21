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
    public  File getExternalFilesDirAllApiLevels(final String packageName) {
        return getExternalDirAllApiLevels(packageName, EXT_STORAGE_FILES_PATH_SUFFIX);
    }

    private File getExternalDirAllApiLevels(final String packageName, final String suffixType) {
        File dir = new File(this.getExternalFilesDir(null).getAbsolutePath()+ EXT_STORAGE_PATH_PREFIX + packageName + suffixType);
        dir.mkdirs();
        return dir;
    }

    public void doSave(View v) {
        if (isExternalStorageWritable()) {
            File dir = getExternalFilesDirAllApiLevels(this.getPackageName());
            File file = new File(dir, FILENAME);
            KP_fileIO.writeStringAsFile(et.getText().toString(), file);
            et.setText("");
            etLocation.setText(getExternalFilesDirAllApiLevels(this.getPackageName()).toString());
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
                et.setText(KP_fileIO.readFileAsString(file));
                etLocation.setText(getExternalFilesDirAllApiLevels(this.getPackageName()).toString());
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
}
