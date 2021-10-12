package com.cemsafa.note_crrk_android;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryNameTV;
        public TextView notesCountTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryNameTV = itemView.findViewById(R.id.categoryNameTV);
            notesCountTV = itemView.findViewById(R.id.notesCountTV);
        }
    }
}
