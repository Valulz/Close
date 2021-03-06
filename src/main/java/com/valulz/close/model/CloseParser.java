package com.valulz.close.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CloseParser {

    private final String ITEM_SEPARATOR = "\\|";
    private static final String RULE_SEPARATOR = ";";


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
    List<ItemSet> parseFile(File file) throws IOException {

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
            IntStream.range(1, lineSplit.length).forEach(i -> itemSet.add(new Item(lineSplit[i].trim())));
            itemSets.add(itemSet);
        });

        return itemSets;
    }

    String parseRules(List<ItemSet> corpus, List<Generator> generators){

        if(generators == null || corpus == null){
            throw new IllegalArgumentException("The generators and corpus cannot be null");
        }

        int corpusSize = corpus.size();

        String rules = "Résultat de l'agorithme Close\n";
        rules += "Extraction des règles exactes : \n\n";

        //Extract the exact rules
        for(Generator generator : generators){

            //Extraction of the Right-Hand Side (close - lhs)
            ItemSet rhs = new ItemSet(generator.getClosure());
            rhs.removeAll(generator.getGenerators());

            //If the RHS is empty, it's because the rhs.equals(generator), so no rules to extract
            if(rhs.isEmpty()) continue;

            double genSupport = (double) generator.getEncounter() / corpusSize;
            double rhsSupport = (double) corpus.parallelStream().filter(itemSet -> itemSet.containsAll(rhs)).count() / corpusSize;

            rules += "\t" + formatItemSet(generator.getGenerators())
                    + "-> "+formatItemSet(rhs)
                    + "(sup : "+ String.format("%.2f", genSupport)
                    + " ; lift : "+String.format("%.2f", (genSupport / (genSupport * rhsSupport))) +")\n";
        }

        rules += "\nExtraction des règles approximatives\n\n";

        //Extract the approximate rules
        for(int i = 0; i<generators.size(); i++){

            Generator lhs = generators.get(i);  //The Left-Hand Side

            //To prevent a rule duplication
            List<ItemSet> closureMade = new ArrayList<>();
            closureMade.add(lhs.getClosure());

            for(int j = 0; j<generators.size(); j++){

                if (i == j) continue;
                Generator close = generators.get(j); // Close represents the full ItemSet (lhs + rhs)

                //The Closure has already been examined, and extracted
                if(closureMade.contains(close.getClosure())) continue;

                if(close.getClosure().containsAll(lhs.getClosure())){

                    //Extraction of the Right-Hand Side (close - lhs)
                    ItemSet rhs = new ItemSet(close.getClosure());
                    rhs.removeAll(lhs.getGenerators());

                    //If the RHS is empty, it's because the rhs.equals(lhs), so no rules to extract
                    if(rhs.isEmpty()) continue;

                    //Extract the support of each side of the rule and of the rule
                    double ruleSupport = (double) close.getEncounter() / corpusSize;
                    double lhsSupport = (double) lhs.getEncounter() / corpusSize;
                    double rhsSupport = (double) corpus.parallelStream().filter(itemSet -> itemSet.containsAll(rhs)).count() / corpusSize;

                    rules += "\t" + formatItemSet(lhs.getGenerators())
                            + "-> "+formatItemSet(rhs)
                            + "(sup : "+String.format("%.2f",ruleSupport)
                            + " ; conf : " +String.format("%.2f", (ruleSupport / lhsSupport))
                            + " ; lift : "+String.format("%.2f",(ruleSupport / (lhsSupport * rhsSupport))) +")\n";
                }

                closureMade.add(close.getClosure());
            }
        }

        return rules;
    }

    public String executeClose(File file, double minSupport){

        List<ItemSet> corpus;
        Close close;

        try {
            corpus = parseFile(file);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        close = new Close();

        List<Generator> generators = close.executeAlgorithm(corpus, minSupport);

        return parseRules(corpus, generators);
    }

    private String formatItemSet(ItemSet itemSet){
        String s = "";
        ItemSet copy = new ItemSet(itemSet);

        while(copy.size() > 1){
            s += copy.pollFirst().getName() + RULE_SEPARATOR+" ";
        }
        s += copy.pollFirst().getName() + " ";

        return s;
    }

}
