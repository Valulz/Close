package com.valulz.close.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Represents the generator.
 */
public class Generator  {

    private ItemSet generators;
    private ItemSet closure;
    private int encounter;

    public Generator(ItemSet generators, ItemSet closure) {

        if(generators == null || closure == null){
            throw new IllegalArgumentException("Generators and closure cannot be null");
        }

        if(generators.size()<=0 || closure.size() <=0){
            throw new IllegalArgumentException("Both Sets must have at least one item");
        }

        this.generators = generators;
        this.closure = closure;
        this.encounter = 1;
    }

    public void newEncounter(ItemSet itemSet){

        if(itemSet == null){
            throw new IllegalArgumentException("The given itemSet cannot be null");
        }

        if(itemSet.size() <= 0){
            throw new IllegalArgumentException("The given itemSet must have at least one element");
        }

        encounter++;
        closure.retainAll(itemSet);
    }

    public ItemSet genNewItemSet(ItemSet itemSet){

        if(itemSet == null || itemSet.size() != generators.size()){
            throw new IllegalArgumentException();
        }

        //Just concatenate, nothing in common
        if(generators.size() == 1){
            ItemSet items = new ItemSet(generators);
            items.add(itemSet.last());

            return items;
        }

        List<Item> lGen = new ArrayList<>(generators);
        List<Item> lSet = new ArrayList<>(itemSet);
        int size = generators.size();

        boolean isSimilar = IntStream.range(0, size-1).parallel().allMatch(i -> lGen.get(i).equals(lSet.get(i)));

        if(isSimilar){
            ItemSet items = new ItemSet(generators);
            items.add(itemSet.last());

            return items;
        } else {
            return null;
        }
    }

    public ItemSet getGenerators() {
        return generators;
    }

    public ItemSet getClosure() {
        return closure;
    }

    public int getEncounter() {
        return encounter;
    }
}
