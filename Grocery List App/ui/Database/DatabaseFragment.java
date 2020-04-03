package com.example.grocerylistapp.ui.Database;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.grocerylistapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFragment extends Fragment implements ItemClickListener {

    private RecyclerView recyclerView;
    private DBAdapter dbAdapter;

    public List<Integer> selectedPositions;

    private GroceryDatabase gDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_database, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.db_recyclerView);

        // Create or Get Database instance (depending on if its been created yet)
        gDB = GroceryDatabase.getInstance(getContext());
        List<GroceryItem> gItemsList = gDB.groceryItemDAO().getAllItems();
        GroceryItem items[] = gItemsList.toArray(new GroceryItem[gItemsList.size()]);

        // For selecting and unselecting elements. Kept here and given to the adapter because the adapter runs its onCreateView functions when its clicked the first time, thus resetting and list and undo-ing the first selection.
        selectedPositions = new ArrayList<Integer>();

        dbAdapter = new DBAdapter(getActivity(), items, this, selectedPositions); // The class implements ItemClickListener so 'this' keyword passes the interface.

        recyclerView.setAdapter(dbAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Inflate menu
        inflater.inflate(R.menu.database_options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle menu item clicks
        int id = item.getItemId();

        switch (id) {
            case R.id.clear_selected:
                clearSelections();
                    break;
            case R.id.mark_athome:
                markAtHome(true);
                    break;
            case R.id.unmark_athome:
                markAtHome(false);
                    break;
            case R.id.delete_selected:
                deleteSelected();
                    break;
            case R.id.addto_shoppinglist:
                // Add to Shopping List
                    break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteSelected() {
        List<Integer> selPosCopy = new ArrayList<Integer>(dbAdapter.selectedPositions);
        int itemIDs[] = new int[dbAdapter.selectedPositions.size()];

        for(int i = 0; i < itemIDs.length; i++) {
            int dbID = dbAdapter.getDBItemID(selPosCopy.get(i));
            gDB.groceryItemDAO().deleteByID(dbID);
        }

        List<GroceryItem> gItemsList = gDB.groceryItemDAO().getAllItems();
        dbAdapter.updateData(gItemsList.toArray(new GroceryItem[gItemsList.size()]));
        clearSelections();
    }

    void clearSelections() {
        dbAdapter.clearSelections();
    }

    void markAtHome(boolean atHome) {
        List<Integer> selPosCopy = new ArrayList<Integer>(dbAdapter.selectedPositions);
        int itemIDs[] = new int[dbAdapter.selectedPositions.size()];

        for(int i = 0; i < itemIDs.length; i++) {
            int dbID = dbAdapter.getDBItemID(selPosCopy.get(i));
            GroceryItem updatedItem = gDB.groceryItemDAO().getItemByID(dbID);
            updatedItem.setAtHome(atHome);
            gDB.groceryItemDAO().updateItem(updatedItem);
        }

        List<GroceryItem> gItemsList = gDB.groceryItemDAO().getAllItems();
        dbAdapter.updateData(gItemsList.toArray(new GroceryItem[gItemsList.size()]));
        clearSelections();
    }

    @Override
    public void onItemClick(int position) {
        selectPosition(position);
        dbAdapter.notifyItemChanged(position);
    }

    public void selectPosition(int position) {
        if (!selectedPositions.contains(position)) {
            selectedPositions.add(position);
        } else {
            selectedPositions.remove(selectedPositions.indexOf(position));
        }
    }
}
