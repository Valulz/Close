package com.valulz.close.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The Generator class represents a generator for the Close Algorithm.
 * A Generator is composed of :
 * <ul>
 *     <li>generator represents a set of item</li>
 *     <li>closure represents an ItemSet which appear in the same objects as the generator</li>
 *     <li>encounter represents how many times the generator has been found</li>
 * </ul>
 */
public class Generator  {

    private ItemSet generators;
    private ItemSet closure;
    private int encounter;

    /**
     * Initialize a newly created Generator with a generator and closure.
     * @param generators an ItemSet that represents the generator
     * @param closure the closure of the generator.
     * @throws IllegalArgumentException if generators or closure are either null or empty
     */
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

    /**
     * A new encounter make an intersection between the itemSet and the current closure. The number of encounter increments.
     * @param itemSet an ItemSet
     * @throws IllegalArgumentException if the ItemSet is null, or does not contain, at least, the generator.
     */
    public void newEncounter(ItemSet itemSet){

        if(itemSet == null){
            throw new IllegalArgumentException("The given itemSet cannot be null");
        }

        if(!itemSet.containsAll(generators)){
            throw new IllegalArgumentException("The given itemSet must, at least, have the generator");
        }

        encounter++;
        closure.retainAll(itemSet);
    }

    /**
     * Generate a new generator from the current one and the given itemSet.
     *
     * @param itemSet The ItemSet to concat to the generator.
     * @throws IllegalArgumentException if itemSet is null or empty.
     * @return the concatenation of the generator and the itemSet, or null if the itemSet does not contain the generator
     */
    public ItemSet createNewGenerator(ItemSet itemSet){

        if(itemSet == null || itemSet.size() != generators.size()){
            throw new IllegalArgumentException();
        }

        List<Item> listGenerator = new ArrayList<>(generators);
        List<Item> listItemSet = new ArrayList<>(itemSet);

        boolean hasSimilarBegin = IntStream
                .range(0, generators.size()-1)
                .parallel()
                .allMatch(i -> listGenerator.get(i).equals(listItemSet.get(i)));

        if(hasSimilarBegin){
            ItemSet items = new ItemSet(generators);
            items.add(itemSet.last());
            return items;
        }

        return null;
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
