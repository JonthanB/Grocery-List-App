package com.example.grocerylistapp.ui.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroceryItemDAO {

    @Query("SELECT * FROM groceryitem")
    List<GroceryItem> getAllItems();

    @Query("SELECT * FROM groceryitem WHERE id = :qID LIMIT 1")
    GroceryItem getItemByID(int qID);

    @Insert
    void insertAll(GroceryItem ... groceryItems);

    @Delete
    void delete(GroceryItem groceryItem);

    @Query("DELETE FROM groceryitem WHERE id = :qID")
    void deleteByID(int qID);

    @Update
    void updateItem(GroceryItem groceryItem);

}
