package com.valulz.close.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.IntStream;

/**
 * Represents the generator.
 */
public class Generator  {

    private SortedSet<Item> generators;
    private SortedSet<Item> ferme;
    private int encounter;

    //TODO should check if generators or ferme size >0
    public Generator(SortedSet<Item> generators, SortedSet<Item> ferme) {

        if(generators == null || ferme == null){
            throw new IllegalArgumentException("Generators and ferme cannot be null");
        }

        if(generators.size()<=0 || ferme.size() <=0){
            throw new IllegalArgumentException("Both Sets must have at least one item");
        }

        this.generators = generators;
        this.ferme = ferme;
        this.encounter = 1;
    }

    public void newEncounter(SortedSet<Item> itemSet){

        if(itemSet == null){
            throw new IllegalArgumentException("The given itemSet cannot be null");
        }

        encounter++;
        ferme.retainAll(itemSet);
    }

    public SortedSet<Item> genNewItemSet(SortedSet<Item> itemSet){

        if(itemSet == null || itemSet.size() != generators.size()){
            throw new IllegalArgumentException();
        }

        //Just concatenate, nothing in common
        if(generators.size() == 1){
            SortedSet<Item> items = new TreeSet<>(generators);
            items.add(itemSet.last());

            return items;
        }

        List<Item> lGen = new ArrayList<>(generators);
        List<Item> lSet = new ArrayList<>(itemSet);
        int size = generators.size();

        boolean isSimilar = IntStream.range(0, size-1).parallel().allMatch(i -> lGen.get(i).equals(lSet.get(i)));

        if(isSimilar){
            SortedSet<Item> items = new TreeSet<>(generators);
            items.add(itemSet.last());

            return items;
        } else {
            return null;
        }
    }

    public SortedSet<Item> getGenerators() {
        return generators;
    }

    public SortedSet<Item> getFerme() {
        return ferme;
    }

    public int getEncounter() {
        return encounter;
    }
}
