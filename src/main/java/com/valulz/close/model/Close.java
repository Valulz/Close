package com.valulz.close.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Close {

    public List<Generator> fermeture(List<SortedSet<Item>> candidates, List<SortedSet<Item>> listItems){

        List<Generator> generators = new ArrayList<>();

        candidates.forEach(candidate -> {
            List<SortedSet<Item>> fermes = listItems.stream().filter(i -> i.containsAll(candidate)).collect(Collectors.toList());

            if (fermes.size() > 0) {
                Generator generator = new Generator(new TreeSet<>(candidate), new TreeSet<>(fermes.remove(0)));
                fermes.forEach(generator::newEncounter);
                generators.add(generator);
            }
        });

        return generators;
    }

}
