package com.valulz.close.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CloseParser {

    private final String ITEM_SEPARATOR = "\\|";

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        return "";
    }

    /**
     * Parse the specified {@code file} into a list of {@link ItemSet itemset}.
     * @param file the file to be parsed
     * @return a list of ItemSet
     * @throws IOException if an I/O error occurs opening the file
     * @throws IllegalArgumentException
     *          if the file is null, is not open in read mode, is not .txt file or does not respect teh format 'id|item|...|item'
     */
    public List<ItemSet> parseFile(File file) throws IOException {

        if(file == null){
            throw new IllegalArgumentException("File cannot be null");
        }

        if(!file.canRead()){
            throw new IllegalArgumentException("The file is not readable");
        }

        if(!getFileExtension(file).equals("txt")){
            throw new IllegalArgumentException("The file must be a text (.txt) file");
        }

        Path path = file.toPath();
        List<ItemSet> itemSets = new ArrayList<>();

        Files.lines(path).forEach(line -> {
            String[] lineSplit = line.split(ITEM_SEPARATOR);

            //The line must have, at least, an ID and an Item.
            if(lineSplit.length < 2){
                throw new IllegalArgumentException("The format of the file must be 'id|item|item|..|item'");
            }

            ItemSet itemSet = new ItemSet();
            IntStream.range(1, lineSplit.length).forEach(i -> itemSet.add(new Item(lineSplit[i])));
            itemSets.add(itemSet);
        });

        return itemSets;
    }


    public String parseRules(List<Generator> generators, int corpusSize){

        if(generators == null){
            throw new IllegalArgumentException("The generators cannot be null");
        }

        if(corpusSize < 0){
            throw new IllegalArgumentException("The corpus size cannot be lower than 0");
        }

        String rules = "Résultat de l'agorithme Close\n";
        rules += "Extraction des règles exactes : \n\n";
        Map<ItemSet, Integer> encounters = new HashMap<>();

        generators.stream().forEach(gen -> {
            if(!encounters.containsKey(gen.getGenerators())){
                encounters.put(gen.getGenerators(), gen.getEncounter());
            }

            if(!encounters.containsKey(gen.getClosure())){
                encounters.put(gen.getClosure(), gen.getEncounter());
            }
        });

        for(Generator gen : generators){
            ItemSet rhs = new ItemSet(gen.getClosure());
            rhs.removeAll(gen.getGenerators());
            if(rhs.isEmpty()) continue;

            double genSupport = support(gen.getGenerators(), encounters, corpusSize);
            double cloSupport = support(gen.getClosure(), encounters, corpusSize);
            double rhsSupport = support(rhs, encounters, corpusSize);

            rules += "\t" + formatItemSet(gen.getGenerators())
                    + " -> "+formatItemSet(rhs)
                    + " (sup : "+ String.format("%.2f", genSupport)
                    + " ; lift : "+String.format("%.2f", (cloSupport / (genSupport * rhsSupport))) +")\n";
        }

        rules += "\n\n Extraction des règles approximatives \n\n";

        for(int i = 0; i<generators.size(); i++){
            List<ItemSet> closureMade = new ArrayList<>();
            closureMade.add(generators.get(i).getClosure());
            for(int j = 0; j<generators.size(); j++){
                if (i == j) continue;
                if(closureMade.contains(generators.get(j).getClosure())) continue;

                if(generators.get(j).getClosure().containsAll(generators.get(i).getClosure())){

                    ItemSet rhs = new ItemSet(generators.get(j).getClosure());
                    rhs.removeAll(generators.get(i).getGenerators());
                    if(rhs.isEmpty()) continue;

                    double ruleSupport = support(generators.get(j).getClosure() // change to rule/close
                            , encounters, corpusSize);
                    double genSupport = support(generators.get(i).getGenerators(), encounters, corpusSize);

                    //TODO revoir LIFT
                    //TODO revoir noù variable
                    //TODO faire algo plus clair.
                    //TODO optimiser le tout


                    rules += "\t" + formatItemSet(generators.get(i).getGenerators()) // change to lhs
                            + " -> "+formatItemSet(rhs)
                            + " (sup : "+String.format("%.2f",ruleSupport)
                            + " conf : " +String.format("%.2f", (ruleSupport / genSupport))
                            + " ; lift : "+String.format("%.2f",(ruleSupport / (genSupport * ruleSupport))) +")\n";


                    closureMade.add(generators.get(j).getClosure());
                }
            }
        }

        return rules;
    }

    private String formatItemSet(ItemSet itemSet){
        String s = "";
        for(Item item : itemSet){
            s+=item.getName() + " ";
        }
        return s;
    }

    private double support(ItemSet itemSet, Map<ItemSet, Integer> encounters, int size){
        return ((double) encounters.get(itemSet) / size );
    }

}
