package com.valulz.close.model;

import java.util.Iterator;
import java.util.TreeSet;

public class ItemSet extends TreeSet<Item> implements Comparable<ItemSet>{

    public ItemSet(Item... items){
        super();

        for (Item item : items) {
            add(item);
        }
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
