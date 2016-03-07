package com.valulz.close.model;

import java.util.*;
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

                boolean isContained = false;
                for(ItemSet close : closure){
                    if(close.containsAll(generator)){
                        isContained = true;
                        break;
                    }
                }

                if(isContained) continue;

                generatorsKPlus1.add(generator);
            }
        }

        return generatorsKPlus1;
    }


    public List<Generator> executeAlgorithm(List<ItemSet> corpus, double minSupport){

        if(corpus == null){
            throw new IllegalArgumentException("Corpus cannot be null");
        }

        if(minSupport<0 || minSupport >1){
            throw new IllegalArgumentException("The minimum support has to be between 0 and 1");
        }

        List<ItemSet> currentGenerators = searchItemInCorpus(corpus);
        List<Generator> generators = new ArrayList<>();
        List<ItemSet> closure = new ArrayList<>();
        int corpusSize = corpus.size();


        while(!currentGenerators.isEmpty()){
            List<Generator> candidates = closure(currentGenerators, corpus);
            Iterator<Generator> candidatesIterator = candidates.iterator();

            while(candidatesIterator.hasNext()){
                Generator candidate = candidatesIterator.next();
                if( ((double) candidate.getEncounter()/corpusSize) >= minSupport){
                    generators.add(candidate);
                    closure.add(candidate.getClosure());
                }else{
                    candidatesIterator.remove();
                }
            }

            currentGenerators = generateCloseKPlus1(candidates, closure);
        }

        return generators;
    }


    private List<ItemSet> searchItemInCorpus(List<ItemSet> corpus){
        List<ItemSet> itemSets = new ArrayList<>();
        SortedSet<Item> items = new TreeSet<>();

        corpus.stream()
                .filter(itemSet -> !items.containsAll(itemSet))
                .forEach(itemSet -> items.addAll(itemSet.stream()
                        .collect(Collectors.toList())));

        itemSets.addAll(items.stream().map(ItemSet::new).collect(Collectors.toList()));

        return itemSets;
    }


}
