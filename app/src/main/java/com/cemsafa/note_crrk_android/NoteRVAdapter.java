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

import com.cemsafa.note_crrk_android.Model.Note;
import com.cemsafa.note_crrk_android.Model.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.ViewHolder> implements Filterable {

    private List<Note> noteList;
    private List<Note> noteListFull;
    private Context context;
    private OnNoteClickListener onNoteClickListener;

    public NoteRVAdapter(List<Note> noteList, Context context, OnNoteClickListener onNoteClickListener) {
        this.noteList = noteList;
        this.context = context;
        this.onNoteClickListener = onNoteClickListener;
        noteListFull = new ArrayList<>(noteList);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitleTV = itemView.findViewById(R.id.noteTitleTV);

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
