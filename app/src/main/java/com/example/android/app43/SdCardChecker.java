package com.example.android.app43;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

/**
 * Created by Tsultrim on 3/9/18.
 */

public class SdCardChecker {

    public static void checkWeatherExternalStorageAvailableOrNot(Context context) {

        boolean isExternalStorageAvailable = false;
        boolean isExternalStorageWriteable = false;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            // We can read and write to and from the media

            isExternalStorageAvailable = isExternalStorageAvailable = true;

            Toast.makeText(context, "read and write", Toast.LENGTH_LONG).show();
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            // We can only read the media
            isExternalStorageAvailable = true;
            isExternalStorageWriteable = false;
            Toast.makeText(context, "read only", Toast.LENGTH_SHORT).show();

        } else {

            // We can neither read nor write
            isExternalStorageAvailable = isExternalStorageWriteable = false;
            Toast.makeText(context, "neither read nor write", Toast.LENGTH_SHORT).show();
        }
    }
}
