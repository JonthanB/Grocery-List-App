package com.example.grocerylistapp.ui.Database;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerylistapp.R;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter extends RecyclerView.Adapter<DBAdapter.DBViewHolder> {

    Context context;
    GroceryItem gItemArray[];
    ItemClickListener itemClickListener;
    public List<Integer> selectedPositions;

    public DBAdapter (Context ct, GroceryItem itemName[], ItemClickListener itemClickListener, List<Integer> selectedPositions) {
        context = ct;
        gItemArray = itemName;
        this.itemClickListener = itemClickListener;
        this.selectedPositions = selectedPositions;
    }

    @NonNull
    @Override
    public DBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.db_row, parent, false);
        return new DBViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DBViewHolder holder, int position) {

        holder.itemNameView.setText(gItemArray[position].getItemName());
        holder.itemCategoryView.setText(gItemArray[position].getItemCategory());

        String outputText;
        int outputColour;
        boolean atHome = gItemArray[position].isAtHome();

        if (atHome) {
            outputText = context.getString(R.string.atHome_true);
            outputColour = ContextCompat.getColor(context, R.color.color_athome_true);
        } else {
            outputText = context.getString(R.string.atHome_false);
            outputColour = ContextCompat.getColor(context, R.color.color_athome_false);
        }

        holder.itemAtHomeView.setText(outputText);
        holder.itemAtHomeView.setTextColor(outputColour);

        if (selectedPositions.contains(position)) {
            holder.itemRowView.setBackgroundColor(context.getResources().getColor(R.color.color_selected_item));
        } else {
            holder.itemRowView.setBackgroundColor(context.getResources().getColor(R.color.color_unselected_item));
        }

    }

    @Override
    public int getItemCount() {
        return gItemArray.length;
    }

    public class DBViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView itemNameView;
        TextView itemCategoryView;
        TextView itemAtHomeView;
        View itemRowView;

        ItemClickListener itemClickListener;

        public DBViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            itemNameView = itemView.findViewById(R.id.textView_itemName);
            itemCategoryView = itemView.findViewById(R.id.textView_itemCategory);
            itemAtHomeView = itemView.findViewById(R.id.textView_atHome);
            itemRowView = itemView.findViewById(R.id.view_Row);
            this.itemClickListener = itemClickListener;

            itemRowView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public void updateData(GroceryItem newItemArray[]) {
        gItemArray = newItemArray.clone();
        notifyDataSetChanged();
    }

    public int getDBItemID(int position) {
        return gItemArray[position].getId();
    }

    public void clearSelections() {
        selectedPositions.clear();
        notifyDataSetChanged();
    }

}
