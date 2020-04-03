package com.example.grocerylistapp.ui.Database;

import android.app.Application;
import android.os.AsyncTask;

public class GroceryItemRepository {
    private GroceryItemDAO groceryItemDAO;

    public GroceryItemRepository(Application application) {
        GroceryDatabase gDB = GroceryDatabase.getInstance(application);
        groceryItemDAO = gDB.groceryItemDAO();
    }

    public void insert(GroceryItem groceryItem) {
        new InsertItemsAsync(groceryItemDAO).execute(groceryItem);
    }

    public void delete(GroceryItem groceryItem) {
        new DeleteItemAsync(groceryItemDAO).execute(groceryItem);
    }

    public void update(GroceryItem groceryItem) {
        new UpdateItemAsync(groceryItemDAO).execute(groceryItem);
    }

    private static class InsertItemsAsync extends AsyncTask<GroceryItem, Void, Void> {
        private GroceryItemDAO groceryItemDAO;

        private InsertItemsAsync(GroceryItemDAO groceryItemDAO) {
            this.groceryItemDAO = groceryItemDAO;
        }

        @Override
        protected Void doInBackground(GroceryItem... groceryItems) {
            groceryItemDAO.insertAll(groceryItems[0]);
            return null;
        }
    }

    private static class DeleteItemAsync extends AsyncTask<GroceryItem, Void, Void> {
        private GroceryItemDAO groceryItemDAO;

        private DeleteItemAsync(GroceryItemDAO groceryItemDAO) {
            this.groceryItemDAO = groceryItemDAO;
        }

        @Override
        protected Void doInBackground(GroceryItem... groceryItems) {
            groceryItemDAO.delete(groceryItems[0]);
            return null;
        }
    }

    private static class UpdateItemAsync extends AsyncTask<GroceryItem, Void, Void> {
        private GroceryItemDAO groceryItemDAO;

        private UpdateItemAsync(GroceryItemDAO groceryItemDAO) {
            this.groceryItemDAO = groceryItemDAO;
        }

        @Override
        protected Void doInBackground(GroceryItem... groceryItems) {
            groceryItemDAO.updateItem(groceryItems[0]);
            return null;
        }
    }

}
