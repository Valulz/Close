package com.valulz.close.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class ItemSet extends TreeSet<Item> implements Comparable<ItemSet>{

    public ItemSet(Item... items){
        super();

        if(items == null){
            throw new IllegalArgumentException("The given items cannot be null");
        }
        List<Item> listItem = Arrays.asList(items);

        if(listItem.stream().anyMatch(item -> item == null)){
            throw new IllegalArgumentException("An item cannot be null");
        }

        addAll(Arrays.asList(items));
    }

    public ItemSet(ItemSet itemSet) {
        super();
        if(itemSet == null){
            throw new IllegalArgumentException("ItemSet cannot be null");
        }

        this.addAll(itemSet);
    }

    @Override
    public int compareTo(ItemSet o) {
        if(o == null){
            throw new IllegalArgumentException();
        }

        if(this.size() != o.size()){
            return this.size() < o.size() ? -1 : 1;
        }

        Iterator<Item> itThis = this.iterator();
        Iterator<Item> itOther = o.iterator();

        int compareToItem = 0;

        while(itThis.hasNext()){
            if((compareToItem = itThis.next().compareTo(itOther.next())) != 0){
                break;
            }
        }

        return compareToItem;
    }
}
