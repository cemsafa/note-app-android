package com.cemsafa.note_crrk_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cemsafa.note_crrk_android.Model.NoteViewModel;

import java.io.IOException;

public class RecordPlayActivity extends AppCompatActivity {

    public static final String AUDIO_REPLY = "audio_reply";

    private Button recordBtn, stopRecordBtn, playBtn, stopPlayBtn, doneBtn;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static String mFileName = null;

    private NoteRVAdapter adapter;

    private long noteId = 0;

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

        if (getIntent().hasExtra(adapter.NOTE_AUDIO)) {
            mFileName = getIntent().getStringExtra(adapter.NOTE_AUDIO);
            setBtnEnable(true, false, true, false);
        } else if (getIntent().hasExtra(AddEditActivity.NOTE_ID)) {
            setBtnEnable(true, false, false, false);
            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            noteId = getIntent().getLongExtra(AddEditActivity.NOTE_ID, 0);
            mFileName += "/AudioRecording" + noteId + ".3gp";
        }

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnEnable(false, true, false, false);
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
            }
        });

        stopRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnEnable(true, false, true, true);
                mRecorder.stop();
                mRecorder.reset();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnEnable(true, false, false, true);
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
                setBtnEnable(true, false, true, false);
                Toast.makeText(getApplicationContext(), "Playing Stopped", Toast.LENGTH_SHORT).show();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("SENT: " + mFileName);
                Intent intent = new Intent();
                intent.putExtra(AUDIO_REPLY, mFileName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setBtnEnable(boolean record, boolean stopRecord, boolean play, boolean stopPlay) {
        recordBtn.setEnabled(record);
        stopRecordBtn.setEnabled(stopRecord);
        playBtn.setEnabled(play);
        stopPlayBtn.setEnabled(stopPlay);
    }
}