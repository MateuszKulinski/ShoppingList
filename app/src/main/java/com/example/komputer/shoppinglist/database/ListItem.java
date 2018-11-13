package com.example.komputer.shoppinglist.database;

public class ListItem {
    private int listID;
    private String listName;
    private float listPrice;

    public ListItem(int listID, String listName, float listPrice) {
        this.listID = listID;
        this.listName = listName;
        this.listPrice = listPrice;
    }

    public ListItem() {

    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public float getListPrice() {
        return listPrice;
    }

    public void setListPrice(float listPrice) {
        this.listPrice = listPrice;
    }
}
