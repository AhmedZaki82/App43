package com.example.android.app43;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDownloadDirectory;
    Button btnMusicDirectory;
    Button btnDocumentsFolder;
    Button btnRingtonesFolder;
    Button btnPodcastsFolder;
    Button btnMoviesFolder;
    Button btnAlarmsFolder;
    Button btnPicturesFolder;

    Button btnSaveFile;
    EditText edtValue;

    Button btnRetrieveTheInfo;
    TextView txtValue;

    ImageView imgAnimal;

    Button btnAllowAccessPictures;

    LinearLayout linearLayoutHorizontal;

    ImageSwitcher imageSwitcher;


    public static final int REQUEST_CODE = 1234;

    public boolean isStoragePermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("LOG", "Permission is granted");
                return true;
            } else {

                Log.v("LOG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;

            }
        } else { // permission is automatically granted on sdk<23 upon installation

            Log.v("LOG", "Permission is granted");
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions
            , int[] grantResults) {super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("LOG","Permission: "+permissions[0]+"was"+grantResults[0]);
            // resume tasks needing this permission
        }
    }

    @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            SdCardChecker.checkWeatherExternalStorageAvailableOrNot(this);

            isStoragePermissionGranted();

            btnDownloadDirectory = (Button) findViewById(R.id.btnDownloadDirectory);
            btnMusicDirectory = (Button) findViewById(R.id.btnMusicDirectory);
            btnDocumentsFolder = (Button) findViewById(R.id.btnDocumentsFolder);
            btnRingtonesFolder = (Button) findViewById(R.id.btnRingtonesFolder);
            btnPodcastsFolder = (Button) findViewById(R.id.btnPodcastsFolder);
            btnMoviesFolder = (Button) findViewById(R.id.btnMoviesFolder);
            btnAlarmsFolder = (Button) findViewById(R.id.btnAlarmsFolder);
            btnPicturesFolder = (Button) findViewById(R.id.btnPicturesFolder);
            btnSaveFile = (Button) findViewById(R.id.btnSaveFile);
            edtValue = (EditText) findViewById(R.id.edtVlue);
            btnRetrieveTheInfo = (Button) findViewById(R.id.btnRetrieveTheInfo);
            imgAnimal = (ImageView) findViewById(R.id.imgAnimal);
            btnAllowAccessPictures = (Button) findViewById(R.id.btnAllowAccessPictures);
            linearLayoutHorizontal = (LinearLayout) findViewById(R.id.linearLayoutHorizontal);
            imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
            txtValue = (TextView) findViewById(R.id.txtValue);


            btnDownloadDirectory.setOnClickListener(this);
            btnMusicDirectory.setOnClickListener(this);
            btnDocumentsFolder.setOnClickListener(this);
            btnRingtonesFolder.setOnClickListener(this);
            btnPodcastsFolder.setOnClickListener(this);
            btnMoviesFolder.setOnClickListener(this);
            btnAlarmsFolder.setOnClickListener(this);
            btnPicturesFolder.setOnClickListener(this);
            btnSaveFile.setOnClickListener(this);
            btnRetrieveTheInfo.setOnClickListener(this);

            imgAnimal.setOnClickListener(this);


        }

        public File returnStorageDirectoryForFolder(String directoryName, String nameOfFolder) {

            File filePath = new File(Environment.getExternalStoragePublicDirectory(directoryName),
                    nameOfFolder);

            if (!filePath.mkdirs()) {

                letsMakeAToast("There can not be such directory in SDCard");
            } else {

                letsMakeAToast("Your folder is created and its name is: "+nameOfFolder);
            }

            return filePath;
        }

        public void letsMakeAToast(String message) {

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        public void letsSaveFileToDocumentsFolder() {

            File filePath = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS),"NiceFile.txt");

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.append(edtValue.getText().toString());
                outputStreamWriter.close();
                fileOutputStream.close();
                letsMakeAToast("Saved");

            } catch (Exception e) {
                Log.i("LOG", e.toString());
                letsMakeAToast("Exception Occurred Check the Log for more info");
            }


        }

        public void letsRetriveFileDataFromDocumentsFolder() {

            File filePath = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS),"NiceFile.txt");

            try {

                FileInputStream fileInputStream = new FileInputStream(filePath);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String fileData = "";
                String bufferData = "";
                while ((fileData = bufferedReader.readLine()) != null) {
                    bufferData = bufferData + fileData + "\n";
                }

                txtValue.setText(bufferData);
                bufferedReader.close();
                fileInputStream.close();



            }catch (Exception e) {

                Log.i("LOG", e.toString());
                letsMakeAToast("Exception occured check Log for more info");
            }
        }

        public void letsSaveTheImagesToPicturesFolder() {

            try {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bird);
                File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "Tiger123.png");

                OutputStream outputStream = new FileOutputStream(filePath);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,outputStream);
                outputStream.flush();
                outputStream.close();
                letsMakeAToast("Your image has been successfully saved.");

            } catch (Exception e) {

                Log.i("LOG", e.getMessage());
                letsMakeAToast("Exception occured check log for more info");
            }
        }

        @Override
        public void onClick (View view){

            switch (view.getId()) {

                case R.id.btnDownloadDirectory:
                    returnStorageDirectoryForFolder(Environment.DIRECTORY_DOWNLOADS,
                            "Nice Downloads!!!");
                    break;

                case R.id.btnMusicDirectory:
                    returnStorageDirectoryForFolder(Environment.DIRECTORY_MUSIC,
                            "Nice Musics!!!");
                    break;

                case R.id.btnDocumentsFolder:
                    returnStorageDirectoryForFolder(Environment.DIRECTORY_DOCUMENTS,
                            "Nice Documents!!!");
                    break;

                case R.id.btnRingtonesFolder:
                    returnStorageDirectoryForFolder(Environment.DIRECTORY_RINGTONES,
                            "Nice Ring Tones!!!");
                    break;

                case R.id.btnPodcastsFolder:
                    returnStorageDirectoryForFolder(Environment.DIRECTORY_PODCASTS,
                            "Nice Podcasts!!!");
                    break;

                case R.id.btnMoviesFolder:
                    returnStorageDirectoryForFolder(Environment.DIRECTORY_MOVIES,
                            "Nice Movies!!!");
                    break;

                case R.id.btnAlarmsFolder:
                    returnStorageDirectoryForFolder(Environment.DIRECTORY_ALARMS,
                            "Nice Alarms!!!");
                    break;

                case R.id.btnPicturesFolder:
                    returnStorageDirectoryForFolder(Environment.DIRECTORY_PICTURES,
                            "Nice Pictures!!!");
                    break;

                case R.id.btnSaveFile:
                    letsSaveFileToDocumentsFolder();

                    break;

                case R.id.btnRetrieveTheInfo:
                    letsRetriveFileDataFromDocumentsFolder();


                    break;

                case R.id.btnAllowAccessPictures:

                    break;

                case R.id.imgAnimal:
                    letsSaveTheImagesToPicturesFolder();

                    break;
            }

        }
    }

