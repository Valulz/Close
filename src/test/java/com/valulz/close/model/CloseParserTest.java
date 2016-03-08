package com.valulz.close.model;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CloseParserTest {


    @Test
    public void parseFile_fail_if_a_null_parameter_is_specified() throws Exception {
        //Given
        CloseParser closeParser = new CloseParser();

        try{
            //When
            closeParser.parseFile(null);
            fail("parseFile should have failed because of the null specified parameter");
        }catch (IllegalArgumentException ex){
            //Then
            assertThat(ex.getMessage()).isEqualTo("File cannot be null");
        }
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void parseFile_fail_if_a_file_cannot_be_opened_in_read_mode() throws Exception {
        //Given
//        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource("file/test.txt").getFile());
//        CloseParser closeParser = new CloseParser();
//
//        try {
//            //When
//            closeParser.parseFile(file);
//            fail("parseFile should have failed because the file is not readable");
//        }catch (IllegalArgumentException ex){
//            //then
//            assertThat(ex.getMessage()).isEqualTo("The file is not readable");
//        }
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void parseFile_fail_if_the_specified_file_is_not_a_txt_file() throws Exception {
        //Given
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("file/fail.lol").getFile());
        CloseParser closeParser = new CloseParser();

        try {
            //When
            closeParser.parseFile(file);
            fail("parseFile should have failed because the file is not a .txt file");
        }catch (IllegalArgumentException ex){
            //then
            assertThat(ex.getMessage()).isEqualTo("The file must be a text (.txt) file");
        }
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void parseFile_create_a_list_of_ItemSet_from_the_specified_file() throws Exception {
        //Given
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("file/test.txt").getFile());
        CloseParser closeParser = new CloseParser();

        Item a=new Item("a");Item b=new Item("b");Item c=new Item("c");Item d=new Item("d");Item e=new Item("e");
        List<ItemSet> expected = Lists.newArrayList();
        expected.add(new ItemSet(a, c, d));
        expected.add(new ItemSet(b, c, e));
        expected.add(new ItemSet(a, b, c, e));
        expected.add(new ItemSet(b, e));
        expected.add(new ItemSet(a, b, c, e));
        expected.add(new ItemSet(b, c, e));

        //When
        final List<ItemSet> itemSets = closeParser.parseFile(file);

        //Then
        Collections.sort(itemSets);
        Collections.sort(expected);

        assertThat(itemSets).isEqualTo(expected);
    }
}