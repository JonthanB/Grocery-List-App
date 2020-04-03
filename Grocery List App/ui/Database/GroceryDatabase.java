package com.example.grocerylistapp.ui.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GroceryItem.class}, version = 3)
public abstract class GroceryDatabase extends RoomDatabase {

    private static GroceryDatabase instance;

    public abstract GroceryItemDAO groceryItemDAO();

    public static synchronized GroceryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GroceryDatabase.class,
                    "GroceryItemDB_Test1")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()   // While not recommended because it can freeze the UI depending on how much data you have to handle, we are working with a very small amount of data.
                    .build();                   // TODO: Eventually restructure the database to work with LiveData and ViewModels in order to run operations on a background thread.
        }
        return instance;
    }

}
