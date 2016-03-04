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

    public ItemSet(ItemSet itemSet){
        this.itemSet = new TreeSet<>(itemSet.getItemSet());
    }

    public int size(){
        return itemSet.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemSet itemSet1 = (ItemSet) o;

        return itemSet != null ? itemSet.equals(itemSet1.itemSet) : itemSet1.itemSet == null;

    }

    @Override
    public int hashCode() {
        return itemSet != null ? itemSet.hashCode() : 0;
    }

    public SortedSet<Item> getItemSet() {
        return itemSet;
    }
}
