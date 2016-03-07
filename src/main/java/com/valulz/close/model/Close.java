package com.valulz.close.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Close {

    public List<Generator> closure(List<ItemSet> candidates, List<ItemSet> listItems){

        if(candidates == null || listItems == null){
            throw new IllegalArgumentException();
        }

        if(candidates.size() <= 0 || listItems.size() <= 0){
            throw new IllegalArgumentException();
        }

        List<Generator> generators = new ArrayList<>();

        candidates.forEach(candidate -> {
            List<ItemSet> close = listItems.stream().filter(items -> items.containsAll(candidate)).collect(Collectors.toList());

            if (close.size() > 0) {
                Generator generator = new Generator(new ItemSet(candidate), new ItemSet(close.remove(0)));
                close.forEach(generator::newEncounter);

                generators.add(generator);
            }
        });

        return generators;
    }

    public List<ItemSet> generateCloseKPlus1(List<Generator> generatorsK, List<ItemSet> closure){

        if(generatorsK == null || closure == null){
            throw new IllegalArgumentException("Generators and Closure cannot be null");
        }

        List<ItemSet> generatorsKPlus1 = new ArrayList<>();
        Collections.sort(closure);
        Collections.sort(generatorsK, (o1, o2) -> o1.getGenerators().compareTo(o2.getGenerators()));

        for(int i = 0; i<generatorsK.size()-1; i++){
            for(int j = i+1; j<generatorsK.size(); j++){
                ItemSet generator = generatorsK.get(i)
                    .genNewItemSet(generatorsK.get(j).getGenerators());

                if(generator == null)   break;
                if(closure.contains(generator)) continue;

                generatorsKPlus1.add(generator);
            }
        }

        return generatorsKPlus1;
    }

}
