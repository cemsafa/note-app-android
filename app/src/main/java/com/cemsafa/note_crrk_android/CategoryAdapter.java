package com.cemsafa.note_crrk_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cemsafa.note_crrk_android.model.Folder;
import com.cemsafa.note_crrk_android.model.FolderWithNotes;
import com.cemsafa.note_crrk_android.model.Note;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<FolderWithNotes> categoryList;
    private Context context;
    private onCategoryClickListener onCategoryClickListener;

    public CategoryAdapter(List<FolderWithNotes> categoryList, Context context, CategoryAdapter.onCategoryClickListener onCategoryClickListener) {
        this.categoryList = categoryList;
        this.context = context;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FolderWithNotes category = categoryList.get(position);
        holder.categoryName.setText(category.getFolder().getFolderName());
        holder.notesCount.setText(category.getFolder().getFolderName());

    }

    @Override
    public int getItemCount() {

        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView categoryName;
        public TextView notesCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.tv_category_name);
            notesCount = itemView.findViewById(R.id.tv_notes_count);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCategoryClickListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface onCategoryClickListener {
        void onCategoryClick(int position);


    }


}
