package com.example.keith.a5_serialization;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by keith on 2/7/17.
 */

public class KP_fileIO {
    private static final String TAG = "KP_fileIO";

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
                String line;
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
