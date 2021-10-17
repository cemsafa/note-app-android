package com.cemsafa.note_crrk_android;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class RecordPlayActivity extends AppCompatActivity {

    public static final String AUDIO_REPLY = "audio_reply";
    public static final int REQUEST_CODE = 1;

    private Button recordBtn, stopRecordBtn, playBtn, stopPlayBtn, doneBtn;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static String mFileName = null;

    private NoteRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_play);

        adapter = new NoteRVAdapter();

        recordBtn = findViewById(R.id.recordBtn);
        stopRecordBtn = findViewById(R.id.stopRecordBtn);
        playBtn = findViewById(R.id.playBtn);
        stopPlayBtn = findViewById(R.id.stopPlayBtn);
        doneBtn = findViewById(R.id.doneBtn);
        stopRecordBtn.setEnabled(false);
        playBtn.setEnabled(false);
        stopRecordBtn.setEnabled(false);

        if (getIntent().hasExtra(adapter.NOTE_AUDIO)) {
            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileName += "/" + getIntent().getStringExtra(adapter.NOTE_AUDIO) + ".3gp";
        } else {
            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileName += "/AudioRecording.3gp";
        }

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissions()) {
                    stopRecordBtn.setEnabled(true);
                    recordBtn.setEnabled(false);
                    playBtn.setEnabled(false);
                    stopPlayBtn.setEnabled(false);
                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile(mFileName);
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mRecorder.start();
                    Toast.makeText(getApplicationContext(), "Started Recording", Toast.LENGTH_LONG).show();
                } else {
                    eequestPermissions();
                }
            }
        });

        stopRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecordBtn.setEnabled(false);
                recordBtn.setEnabled(true);
                playBtn.setEnabled(true);
                stopPlayBtn.setEnabled(true);
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecordBtn.setEnabled(false);
                recordBtn.setEnabled(true);
                playBtn.setEnabled(false);
                stopPlayBtn.setEnabled(true);
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(mFileName);
                    mPlayer.prepare();
                    mPlayer.start();
                    Toast.makeText(getApplicationContext(), "Started Playing", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        stopPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.release();
                mPlayer = null;
                stopRecordBtn.setEnabled(false);
                recordBtn.setEnabled(true);
                playBtn.setEnabled(true);
                stopPlayBtn.setEnabled(false);
                Toast.makeText(getApplicationContext(),"Playing Stopped", Toast.LENGTH_SHORT).show();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( );
                intent.putExtra(AUDIO_REPLY, mFileName);
                setResult(RESULT_OK, intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermissions() {
        int result = ActivityCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ActivityCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void eequestPermissions() {
        ActivityCompat.requestPermissions(RecordPlayActivity.this, new String[] { RECORD_AUDIO, WRITE_EXTERNAL_STORAGE }, REQUEST_CODE);
    }
}