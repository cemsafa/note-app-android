package com.cemsafa.note_crrk_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cemsafa.note_crrk_android.Model.Folder;
import com.cemsafa.note_crrk_android.Model.FolderWithNotes;
import com.cemsafa.note_crrk_android.Model.NoteViewModel;

import java.util.List;

public class FolderRVAdapter extends RecyclerView.Adapter<FolderRVAdapter.ViewHolder> {

    private List<FolderWithNotes> foldersWithNotes;
    private Context context;
    private OnFolderClickListener onFolderClickListener;

    public FolderRVAdapter(List<FolderWithNotes> foldersWithNotes, Context context, OnFolderClickListener onFolderClickListener) {
        this.foldersWithNotes = foldersWithNotes;
        this.context = context;
        this.onFolderClickListener = onFolderClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.folder_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FolderWithNotes folders = foldersWithNotes.get(position);
        holder.folderNameTV.setText(folders.getFolder().getName());
        holder.notesCountTV.setText(String.valueOf(folders.getNotes().size()));
    }

    @Override
    public int getItemCount() {
        return foldersWithNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView folderNameTV;
        public TextView notesCountTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            folderNameTV = itemView.findViewById(R.id.folderNameTV);
            notesCountTV = itemView.findViewById(R.id.notesCountTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onFolderClickListener.onFolderClick(getAdapterPosition());
        }
    }

    public interface OnFolderClickListener {
        void onFolderClick(int position);
    }
}
