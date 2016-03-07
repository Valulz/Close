package com.valulz.close.model;

/**
 * The Item class represents a named item.
 *
 * @author Valentin Desportes
 */
public class Item implements Comparable<Item> {

    private String name;

    /**
     * Initialize a newly created {@code Item} object with the {@code name} parameter.
     * @throws IllegalArgumentException if the {@code name} is null.
     * @param name the name of the Item.
     */
    public Item(String name) {
        setName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return name.equals(item.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    /**
     * Replace the Item's name with the one given.
     * @throws IllegalArgumentException if the {@code name} is null.
     * @param name the new name of the Item.
     */
    public void setName(String name) {
        if(name == null){
            throw new IllegalArgumentException("The name of the item must not be null");
        }
        this.name = name;
    }

    @Override
    public int compareTo(Item o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                '}';
    }
}
