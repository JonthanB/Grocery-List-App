package com.example.grocerylistapp.ui.ShoppingList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.grocerylistapp.R;
import com.example.grocerylistapp.ui.Database.GroceryItem;

public class ShoppingListFragment extends Fragment {

    // Class to represent grocery items in the shopping list.
    protected class ShoppingListItem {

        String itemName;
        boolean checked;
        GroceryItem gItemReference;

        public ShoppingListItem(String itemName, GroceryItem gItemReference) {
            this.itemName = itemName;
            this.gItemReference = gItemReference;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public GroceryItem getgItemReference() {
            return gItemReference;
        }
    }

    ShoppingListItem[] shoppingList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shopping, container, false);

        return root;
    }
}
