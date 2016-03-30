package com.valulz.close.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Close class represents the execution of the close algorithm.
 * @author Valentin Desportes
 */
public class Close {

    /**
     * This method associate, foreach candidates, its closure, determine from the corpus.
     * @param candidates the list use to create the generators
     * @param corpus the list of all the objects
     * @throws IllegalArgumentException if candidates or listItem are null or empty
     * @return a list of generators created by the association of the candidates and its closure.
     */
    List<Generator> closure(List<ItemSet> candidates, List<ItemSet> corpus){

        if(candidates == null || corpus == null){
            throw new IllegalArgumentException();
        }

        if(candidates.isEmpty() || corpus.isEmpty()){
            throw new IllegalArgumentException();
        }

        List<Generator> generators = new ArrayList<>();

        candidates.forEach(candidate -> {
            List<ItemSet> close = corpus.stream().filter(items -> items.containsAll(candidate)).collect(Collectors.toList());

            if (!close.isEmpty()) {
                Generator generator = new Generator(new ItemSet(candidate), new ItemSet(close.remove(0)));
                close.forEach(generator::newEncounter);

                generators.add(generator);
            }
        });

        return generators;
    }

    /**
     * The method generate from the k generators, the k+1 generators, if the generated one is not already in the closure list.
     * @param generatorsK the generator of size k
     * @param closure the list of all the closure found
     * @return the list of the generator k+1
     */
    List<ItemSet> generateClose(List<Generator> generatorsK, List<ItemSet> closure){

        if(generatorsK == null || closure == null){
            throw new IllegalArgumentException("Generators and Closure cannot be null");
        }

        List<ItemSet> generatorsKPlus1 = new ArrayList<>();
        Collections.sort(closure);
        Collections.sort(generatorsK, (o1, o2) -> o1.getGenerators().compareTo(o2.getGenerators()));

        for(int i = 0; i<generatorsK.size()-1; i++){
            for(int j = i+1; j<generatorsK.size(); j++){
                ItemSet generator = generatorsK.get(i)
                    .createNewGenerator(generatorsK.get(j).getGenerators());

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

    /**
     * Execute the Close Algorithm.
     * @param corpus the objects which in which we will look up.
     * @param minSupport the support minimal.
     * @return a list of generator
     */
    List<Generator> executeAlgorithm(List<ItemSet> corpus, double minSupport){

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

            currentGenerators = generateClose(candidates, closure);
        }

        return generators;
    }

    /**
     * Search for the generator of size 1, in the corpus
     * @param corpus the list of objects
     * @return the generators of size 1.
     */
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
