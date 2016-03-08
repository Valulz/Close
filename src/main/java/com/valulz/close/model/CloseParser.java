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

        //TODO make a file not readable
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

}
