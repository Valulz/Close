package com.valulz.close.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Close {

    public List<Generator> closure(List<SortedSet<Item>> candidates, List<SortedSet<Item>> listItems){

        if(candidates == null || listItems == null){
            throw new IllegalArgumentException();
        }

        if(candidates.size() <= 0 || listItems.size() <= 0){
            throw new IllegalArgumentException();
        }

        List<Generator> generators = new ArrayList<>();

        candidates.forEach(candidate -> {
            List<SortedSet<Item>> close = listItems.stream().filter(items -> items.containsAll(candidate)).collect(Collectors.toList());

            if (close.size() > 0) {
                Generator generator = new Generator(new TreeSet<>(candidate), new TreeSet<>(close.remove(0)));
                close.forEach(generator::newEncounter);

                generators.add(generator);
            }
        });

        return generators;
    }

}
