package com.premsinghdaksha.downloadmanager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.premsinghdaksha.download_manager.DefaultRetryPolicy;
import com.premsinghdaksha.download_manager.DownloadManager;
import com.premsinghdaksha.download_manager.DownloadRequest;
import com.premsinghdaksha.download_manager.DownloadStatusListenerV1;
import com.premsinghdaksha.download_manager.PSDDownloadManager;
import com.premsinghdaksha.download_manager.RetryPolicy;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private PSDDownloadManager downloadManager;
    private static final int DOWNLOAD_THREAD_POOL_SIZE = 4;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 121;
    private int permissionClick = 0;

    Button mDownload1;
    Button mDownload2;
    Button mDownload3;
    Button mDownload4;
    Button mDownload5;

    Button mStartAll;
    Button mCancelAll;
    Button mListFiles;

    ProgressBar mProgress1;
    ProgressBar mProgress2;
    ProgressBar mProgress3;
    ProgressBar mProgress4;
    ProgressBar mProgress5;

    TextView mProgress1Txt;
    TextView mProgress2Txt;
    TextView mProgress3Txt;
    TextView mProgress4Txt;
    TextView mProgress5Txt;

    private static final String FILE1 = "https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823_960_720.jpg";
    private static final String FILE2 = "https://media.istockphoto.com/photos/winding-road-picture-id1205443403";
    private static final String FILE3 = "https://cdn.pixabay.com/photo/2014/02/27/16/10/tree-276014_960_720.jpg";
    private static final String FILE4 = "https://cdn.pixabay.com/photo/2014/02/27/16/10/tree-276014_960_720.jpg";
    private static final String FILE5 = "https://cdn.pixabay.com/photo/2014/02/27/16/10/tree-276014_960_720.jpg";
    private static final String FILE6 = "https://cdn.pixabay.com/photo/2014/02/27/16/10/tree-276014_960_720.jpg";

    MyDownloadDownloadStatusListenerV1
            myDownloadStatusListener = new MyDownloadDownloadStatusListenerV1();

    int downloadId1;
    int downloadId2;
    int downloadId3;
    int downloadId4;
    int downloadId5;
    int downloadId6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        mDownload1 = (Button) findViewById(R.id.button1);
        mDownload2 = (Button) findViewById(R.id.button2);
        mDownload3 = (Button) findViewById(R.id.button3);
        mDownload4 = (Button) findViewById(R.id.button4);
        mDownload5 = (Button) findViewById(R.id.button_download_headers);

        mStartAll = (Button) findViewById(R.id.button5);
        mCancelAll = (Button) findViewById(R.id.button6);
        mListFiles = (Button) findViewById(R.id.button7);

        mProgress1Txt = (TextView) findViewById(R.id.progressTxt1);
        mProgress2Txt = (TextView) findViewById(R.id.progressTxt2);
        mProgress3Txt = (TextView) findViewById(R.id.progressTxt3);
        mProgress4Txt = (TextView) findViewById(R.id.progressTxt4);
        mProgress5Txt = (TextView) findViewById(R.id.progressTxt5);

        mProgress1 = (ProgressBar) findViewById(R.id.progress1);
        mProgress2 = (ProgressBar) findViewById(R.id.progress2);
        mProgress3 = (ProgressBar) findViewById(R.id.progress3);
        mProgress4 = (ProgressBar) findViewById(R.id.progress4);
        mProgress5 = (ProgressBar) findViewById(R.id.progress5);

        mProgress1.setMax(100);
        mProgress1.setProgress(0);

        mProgress2.setMax(100);
        mProgress2.setProgress(0);

        mProgress3.setMax(100);
        mProgress3.setProgress(0);

        mProgress4.setMax(100);
        mProgress4.setProgress(0);

        mProgress5.setMax(100);
        mProgress5.setProgress(0);

        downloadManager = new PSDDownloadManager(DOWNLOAD_THREAD_POOL_SIZE);
        RetryPolicy retryPolicy = new DefaultRetryPolicy();

        File filesDir = getExternalFilesDir("");

        Uri downloadUri = Uri.parse(FILE1);
        Uri destinationUri = Uri.parse(filesDir + "/test_photo1.JPG");
        final DownloadRequest downloadRequest1 = new DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW)
                .setRetryPolicy(retryPolicy)
                .setDownloadContext("Download1")
                .setStatusListener(myDownloadStatusListener);

        downloadUri = Uri.parse(FILE2);
        destinationUri = Uri.parse(filesDir + "/test_photo2.jpg");
        final DownloadRequest downloadRequest2 = new DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW)
                .setDownloadContext("Download2")
                .setStatusListener(myDownloadStatusListener);

        downloadUri = Uri.parse(FILE3);
        destinationUri = Uri.parse(filesDir + "/test_song.mp3");
        final DownloadRequest downloadRequest3 = new DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadContext("Download3")
                .setStatusListener(myDownloadStatusListener);

        downloadUri = Uri.parse(FILE4);
        destinationUri = Uri.parse(filesDir + "/mani/test/aaa/test_video.mp4");
        // Define a custom retry policy
        retryPolicy = new DefaultRetryPolicy(5000, 3, 2f);
        final DownloadRequest downloadRequest4 = new DownloadRequest(downloadUri)
                .setRetryPolicy(retryPolicy)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadContext("Download4")
                .setStatusListener(myDownloadStatusListener);

        downloadUri = Uri.parse(FILE5);
        destinationUri = Uri.parse(filesDir + "/headers.json");
        final DownloadRequest downloadRequest5 = new DownloadRequest(downloadUri)
                .addCustomHeader("Auth-Token", "myTokenKey")
                .addCustomHeader("User-Agent", "Thin/Android")
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadContext("Download5")
                .setStatusListener(myDownloadStatusListener);

        downloadUri = Uri.parse(FILE6);
        destinationUri = Uri.parse(filesDir + "/wtfappengine.zip");
        final DownloadRequest downloadRequest6 = new DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadContext("Download6")
                .setStatusListener(myDownloadStatusListener);

        mDownload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionClick == 1) {
                    if (downloadManager.query(downloadId1) == DownloadManager.STATUS_NOT_FOUND) {
                        downloadId1 = downloadManager.add(downloadRequest1);
                    }
                }
            }
        });

        mDownload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionClick == 1) {
                    if (downloadManager.query(downloadId2) == DownloadManager.STATUS_NOT_FOUND) {
                        downloadId2 = downloadManager.add(downloadRequest2);
                    }
                }
            }
        });

        mDownload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionClick == 1) {
                    if (downloadManager.query(downloadId3) == DownloadManager.STATUS_NOT_FOUND) {
                        downloadId3 = downloadManager.add(downloadRequest3);
                    }
                }
            }
        });

        mDownload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionClick == 1) {
                    if (downloadManager.query(downloadId4) == DownloadManager.STATUS_NOT_FOUND) {
                        downloadId4 = downloadManager.add(downloadRequest4);
                    }
                }
            }
        });

        mDownload5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionClick == 1) {

                    //if (downloadManager.query(downloadId5) == DownloadManager.STATUS_NOT_FOUND) {
                    //    downloadId5 = downloadManager.add(downloadRequest5);
                    //}

                    if (downloadManager.query(downloadId6) == DownloadManager.STATUS_NOT_FOUND) {
                        downloadId6 = downloadManager.add(downloadRequest6);
                    }
                }

            }
        });

        mStartAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager.cancelAll();
                downloadId1 = downloadManager.add(downloadRequest1);
                downloadId2 = downloadManager.add(downloadRequest2);
                downloadId3 = downloadManager.add(downloadRequest3);
                downloadId4 = downloadManager.add(downloadRequest4);
                downloadId5 = downloadManager.add(downloadRequest5);
            }
        });

        mCancelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager.cancelAll();
            }
        });

        mListFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInternalFilesDir();
            }
        });

        mProgress1Txt.setText("Download1");
        mProgress2Txt.setText("Download2");
        mProgress3Txt.setText("Download3");
        mProgress4Txt.setText("Download4");
        mProgress5Txt.setText("Download5");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("######## onDestroy ######## ");
        downloadManager.release();
    }

    private void showInternalFilesDir() {
        File internalFile = new File(getExternalFilesDir("").getPath());
        File files[] = internalFile.listFiles();
        StringBuilder contentText = new StringBuilder();
        if (files.length == 0) {
            contentText = new StringBuilder("No Files Found");
        }

        for (File file : files) {
            contentText.append(file.getName()).append(" ").append(file.length()).append(" \n\n ");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog internalCacheDialog = builder.create();
        LayoutInflater inflater = internalCacheDialog.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.layout_files, null);
        TextView content = (TextView) dialogLayout.findViewById(R.id.filesList);
        content.setText(contentText.toString());

        builder.setView(dialogLayout);
        builder.show();

    }

    class MyDownloadDownloadStatusListenerV1 implements DownloadStatusListenerV1 {

        @Override
        public void onDownloadComplete(DownloadRequest request) {
            final int id = request.getDownloadId();
            if (id == downloadId1) {
                mProgress1Txt.setText(request.getDownloadContext() + " id: " + id + " Completed");

            } else if (id == downloadId2) {
                mProgress2Txt.setText(request.getDownloadContext() + " id: " + id + " Completed");

            } else if (id == downloadId3) {
                mProgress3Txt.setText(request.getDownloadContext() + " id: " + id + " Completed");

            } else if (id == downloadId4) {
                mProgress4Txt.setText(request.getDownloadContext() + " id: " + id + " Completed");
            } else if (id == downloadId5) {
                mProgress5Txt.setText(request.getDownloadContext() + " id: " + id + " Completed");
            }
        }

        @Override
        public void onDownloadFailed(DownloadRequest request, int errorCode, String errorMessage) {
            final int id = request.getDownloadId();
            if (id == downloadId1) {
                mProgress1Txt.setText("Download1 id: " + id + " Failed: ErrorCode " + errorCode + ", " + errorMessage);
                mProgress1.setProgress(0);
            } else if (id == downloadId2) {
                mProgress2Txt.setText("Download2 id: " + id + " Failed: ErrorCode " + errorCode + ", " + errorMessage);
                mProgress2.setProgress(0);

            } else if (id == downloadId3) {
                mProgress3Txt.setText("Download3 id: " + id + " Failed: ErrorCode " + errorCode + ", " + errorMessage);
                mProgress3.setProgress(0);

            } else if (id == downloadId4) {
                mProgress4Txt.setText("Download4 id: " + id + " Failed: ErrorCode " + errorCode + ", " + errorMessage);
                mProgress4.setProgress(0);
            } else if (id == downloadId5) {
                mProgress5Txt.setText("Download5 id: " + id + " Failed: ErrorCode " + errorCode + ", " + errorMessage);
                mProgress5.setProgress(0);
            }
        }

        @Override
        public void onProgress(DownloadRequest request, long totalBytes, long downloadedBytes, int progress) {
            int id = request.getDownloadId();

            System.out.println("######## onProgress ###### " + id + " : " + totalBytes + " : " + downloadedBytes + " : " + progress);
            if (id == downloadId1) {
                mProgress1Txt.setText("Download1 id: " + id + ", " + progress + "%" + "  " + getBytesDownloaded(progress, totalBytes));
                mProgress1.setProgress(progress);

            } else if (id == downloadId2) {
                mProgress2Txt.setText("Download2 id: " + id + ", " + progress + "%" + "  " + getBytesDownloaded(progress, totalBytes));
                mProgress2.setProgress(progress);

            } else if (id == downloadId3) {
                mProgress3Txt.setText("Download3 id: " + id + ", " + progress + "%" + "  " + getBytesDownloaded(progress, totalBytes));
                mProgress3.setProgress(progress);

            } else if (id == downloadId4) {
                mProgress4Txt.setText("Download4 id: " + id + ", " + progress + "%" + "  " + getBytesDownloaded(progress, totalBytes));
                mProgress4.setProgress(progress);
            } else if (id == downloadId5) {
                mProgress5Txt.setText("Download5 id: " + id + ", " + progress + "%" + "  " + getBytesDownloaded(progress, totalBytes));
                mProgress5.setProgress(progress);
            } else if (id == downloadId6) {
                mProgress5Txt.setText("Download6 id: " + id + ", " + progress + "%" + "  " + getBytesDownloaded(progress, totalBytes));
                mProgress5.setProgress(progress);
            }
        }
    }

    private String getBytesDownloaded(int progress, long totalBytes) {
        //Greater than 1 MB
        long bytesCompleted = (progress * totalBytes) / 100;
        if (totalBytes >= 1000000) {
            return ("" + (String.format("%.1f", (float) bytesCompleted / 1000000)) + "/" + (String.format("%.1f", (float) totalBytes / 1000000)) + "MB");
        }
        if (totalBytes >= 1000) {
            return ("" + (String.format("%.1f", (float) bytesCompleted / 1000)) + "/" + (String.format("%.1f", (float) totalBytes / 1000)) + "Kb");

        } else {
            return ("" + bytesCompleted + "/" + totalBytes);
        }
    }


    //end code
    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)

                != PackageManager.PERMISSION_GRANTED
        ) {

// Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this, Manifest.permission.CAMERA)
// || ActivityCompat.shouldShowRequestPermissionRationale(
// ActivateProductUploadDocuments.this, Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
/*|| ActivityCompat.shouldShowRequestPermissionRationale(
ActivateProductUploadDocuments.this, Manifest.permission.READ_PHONE_STATE*/

            ) {
// If we should give explanation of requested permissions

// Show an alert dialog here with request explanation


                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{
                                Manifest.permission.CAMERA,
//Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
//Manifest.permission.READ_PHONE_STATE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Camera, Read and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{
                                        Manifest.permission.CAMERA,
// Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
//Manifest.permission.READ_PHONE_STATE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
// builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
//dialog.show();
            } else {
// Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{
                                Manifest.permission.CAMERA,
// Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
// Manifest.permission.READ_PHONE_STATE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        } else {
            permissionClick = 1;
        Toast.makeText(MainActivity.this, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                if ((grantResults[0] == 0 && grantResults[1] == 0
                        && grantResults[2] == 0)) {
                    permissionClick = 1;

                } else {
                }
                return;
            }
        }
    }

}