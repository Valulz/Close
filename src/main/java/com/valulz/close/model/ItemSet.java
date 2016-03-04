package com.valulz.close.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class ItemSet implements Comparable<ItemSet>{

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

    public boolean isEmpty(){
        return itemSet.isEmpty();
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

    //Devrais-je y avoir accès ?
    public SortedSet<Item> getItemSet() {
        return itemSet;
    }

    @Override
    public int compareTo(ItemSet o) {
        if(o == null){
            throw new IllegalArgumentException();
        }

        if(this.size() != o.size()){
            return this.size() < o.size() ? -1 : 1;
        }

        Iterator<Item> itThis = this.itemSet.iterator();
        Iterator<Item> itOther = o.itemSet.iterator();

        int compareToItem = 0;

        while(itThis.hasNext() && (compareToItem = itThis.next().compareTo(itOther.next())) == 0);

        return compareToItem;

    }
}
