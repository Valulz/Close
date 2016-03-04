package com.valulz.close.model;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class ItemSet {

    private SortedSet<Item> itemSet;

    public ItemSet() {
        this.itemSet = new TreeSet<>();
    }

    public ItemSet(Item... items){
        itemSet = new TreeSet<>();
        Collections.addAll(itemSet, items);
    }

    public int size(){
        return itemSet.size();
    }




    public SortedSet<Item> getItemSet() {
        return itemSet;
    }
}
