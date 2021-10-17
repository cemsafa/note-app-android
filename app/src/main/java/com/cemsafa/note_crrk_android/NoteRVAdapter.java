package com.cemsafa.note_crrk_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.cemsafa.note_crrk_android.Model.Note;
import com.cemsafa.note_crrk_android.Model.NoteDao;
import com.cemsafa.note_crrk_android.Model.NoteViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.ViewHolder> implements Filterable {

    public static final String NOTE_PHOTO = "note_photo";
    public static final String NOTE_AUDIO = "note_audio";
    public static final String NOTE_LATITUDE = "note_latitude";
    public static final String NOTE_LONGITUDE = "note_longitude";

    private List<Note> noteList;
    private List<Note> noteListFull;
    private Context context;
    private OnNoteClickListener onNoteClickListener;
    private NoteViewModel noteViewModel;

    public NoteRVAdapter() {}

    public NoteRVAdapter(List<Note> noteList, Context context, OnNoteClickListener onNoteClickListener, NoteViewModel noteViewModel) {
        this.noteList = noteList;
        this.context = context;
        this.onNoteClickListener = onNoteClickListener;
        noteListFull = new ArrayList<>(noteList);
        this.noteViewModel = noteViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.note_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.noteTitleTV.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView noteTitleTV;
        public ImageButton moveBtn, mapBtn, deleteBtn, photoBtn, audioBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitleTV = itemView.findViewById(R.id.noteTitleTV);
            moveBtn = itemView.findViewById(R.id.moveToFolderBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            mapBtn = itemView.findViewById(R.id.mapBtn);
            photoBtn = itemView.findViewById(R.id.photoBtn);
            audioBtn = itemView.findViewById(R.id.audioBtn);

            moveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("MoveBtn pressed " + noteList.get(getAdapterPosition()).getTitle());
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("You are about to delete this note. Are you sure?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        noteViewModel.delete(noteList.get(getAdapterPosition()));
                        notifyDataSetChanged();
                    });
                    builder.setNegativeButton("No", (dialog, which) -> {
                        notifyDataSetChanged();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            mapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Double latitude = noteList.get(getAdapterPosition()).getLatitude();
                    Double longitude = noteList.get(getAdapterPosition()).getLongitude();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra(NOTE_LATITUDE, latitude);
                    intent.putExtra(NOTE_LONGITUDE, longitude);
                    context.startActivity(intent);
                }
            });

            photoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (noteList.get(getAdapterPosition()).getPhoto() == null || noteList.get(getAdapterPosition()).getPhoto().equals("")) {
                        Toast.makeText(context, "First add a photo", Toast.LENGTH_SHORT).show();
                    } else {
                        String receivedPhotoString = noteList.get(getAdapterPosition()).getPhoto();
                        Intent intent = new Intent(context, ShowImageActivity.class);
                        intent.putExtra(NOTE_PHOTO, receivedPhotoString);
                        context.startActivity(intent);
                    }
                }
            });

            audioBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (noteList.get(getAdapterPosition()).getAudio() == null || noteList.get(getAdapterPosition()).getAudio().equals("")) {
                        Toast.makeText(context, "First add an audio", Toast.LENGTH_SHORT).show();
                    } else {
                        String receivedAudioString = noteList.get(getAdapterPosition()).getAudio();
                        Intent intent = new Intent(context, RecordPlayActivity.class);
                        intent.putExtra(NOTE_AUDIO, receivedAudioString);
                        context.startActivity(intent);
                    }
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteClickListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteClickListener {
        void onNoteClick(int position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Note> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(noteListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Note note : noteListFull) {
                    if (note.getTitle().toLowerCase().contains(filterPattern) || note.getContent().toLowerCase().contains(filterPattern)) {
                        filteredList.add(note);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            noteList.clear();
            noteList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
