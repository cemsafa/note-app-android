package com.cemsafa.note_crrk_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class NewNoteActivity extends AppCompatActivity {

    Button select;
    private static final int CAMERA_REQUEST = 200;
    private static final int GALLERY_REQUEST = 201;
    Bitmap bitmap;
    AppCompatImageView pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        select=(Button) findViewById(R.id.select);
        pic=(AppCompatImageView) findViewById(R.id.pic);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleImageChooser();
            }
        });
    }

    public void handleImageChooser() {
        final CharSequence[] items = { "Camera", "Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("With Camera")) {
                CaptureImage();
            } else if (items[item].equals("From Gallery")) {
                OpenGallery();
            }
            else if(items[item].equals("Cancel")){
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void CaptureImage() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void OpenGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            bitmap = (Bitmap) data.getExtras().get("data");
            pic.setImageBitmap(bitmap);
        }
        else if(reqCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                pic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}