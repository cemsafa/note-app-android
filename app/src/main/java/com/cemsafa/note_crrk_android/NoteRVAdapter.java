package com.cemsafa.note_crrk_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cemsafa.note_crrk_android.activity.NotesActivity;
import com.cemsafa.note_crrk_android.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.ViewHolder>  {

    private List<Note> noteList;
    private List<Note> filteredNoteList = new ArrayList<>();
    private Context context;
    private onNoteClickListener onNoteClickListener;

    public NoteRVAdapter(List<Note> noteList, Context context, onNoteClickListener onNoteClickListener) {
        this.noteList = noteList;
        this.context = context;
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRVAdapter.ViewHolder holder, int position) {

        Note note = noteList.get(position);
        holder.tv_noteList.setText(note.getCategory_name());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }


    public interface OnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_noteList;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_noteList = itemView.findViewById(R.id.tv_note_list);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteClickListener.onNoteClick(getAdapterPosition());

        }
    }

    private class onNoteClickListener {
        void onNoteClick(int position) {

        }
    }

    //Filter Class
    public  void FilterNoteList (String charText) {
        String filter = charText.toLowerCase().trim();
     filteredNoteList.get(noteList.size()).getTitle().toLowerCase().trim();
     filteredNoteList.clear();
     if (filteredNoteList.size() == 0) {
         filteredNoteList.addAll(noteList);
     }else {
         for (Note note : filteredNoteList) {
             if (note.getTitle().toLowerCase().contains(filter) || note.getCategory_name().toLowerCase().contains(filter));
             filteredNoteList.add(note);
         }
     }
     notifyDataSetChanged();


    }

}
