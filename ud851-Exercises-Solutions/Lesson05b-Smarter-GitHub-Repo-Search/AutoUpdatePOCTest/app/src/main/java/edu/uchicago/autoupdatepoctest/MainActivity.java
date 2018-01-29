package edu.uchicago.autoupdatepoctest;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "PERMISSION_GRANTED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AutoUpdateTask().execute();

    }

    public class AutoUpdateTask extends AsyncTask<String , Void, String> {

        @Override
        protected String doInBackground(String... urlString) {

            //get destination to update file and set Uri
            //TODO: First I wanted to store my update .apk file on internal storage for my app but apparently android does not allow you to open and install
            //aplication with existing package from there. So for me, alternative solution is Download directory in external storage. If there is better
            //solution, please inform us in comment
            String destination = Environment.getDataDirectory() + "/incident-reporter";
            //String fileName = "ir-release.apk";
            //destination += fileName;
            Context context = MainActivity.this;
           // final Uri uri = Uri.parse("file://" + destination);
            File destinationDirectory =  new File(destination);
            if( !destinationDirectory.exists() ){
                destinationDirectory.mkdir();
            }
            final Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", destinationDirectory);

            //Delete update file if exists
            File file = new File(destination);
            if (file.exists())
                //file.delete() - test this, I think sometimes it doesnt work
                file.delete();

            //get url of app on server
            String url = "https://app-builds.uchicago.edu/android/incident-reporter/ir-release.apk";

            //set downloadmanager
            final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("IR Update");
            request.setTitle("New Version is available.");

            //set destination
            request.setDestinationUri(uri);

            // get download service and enqueue file
            final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final long downloadId = manager.enqueue(request);

            //set BroadcastReceiver to install app when .apk is downloaded
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    install.setDataAndType(uri,
                            manager.getMimeTypeForDownloadedFile(downloadId));
                    startActivity(install);

                    unregisterReceiver(this);
                    finish();
                }

                private void finish() {
                    return;
                }
            };
            //register receiver for when .apk download is compete
            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            return null;
        }

        @Override
        public void onPreExecute(){
            if (!(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                Log.v(TAG,"Permission is granted");
                //File write logic here
                int REQUEST_CODE =1;
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

            }
        }
    }


    public class GenericFileProvider extends FileProvider {

    }

}
