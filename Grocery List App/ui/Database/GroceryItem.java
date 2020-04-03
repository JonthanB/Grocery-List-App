package com.example.grocerylistapp.ui.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GroceryItem {

    public GroceryItem(String itemName, String itemCategory, boolean atHome) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.atHome = atHome;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "at_home")
    private boolean atHome;

    @ColumnInfo(name = "item_category")
    private String itemCategory;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isAtHome() {
        return atHome;
    }

    public void setAtHome(boolean atHome) {
        this.atHome = atHome;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }
}
