package com.valulz.close.model;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
    public void parseFile_fail_if_a_file_cannot_be_opened_in_read_mode() throws Exception {
        //Given
        File file = mock(File.class);
        when(file.canRead()).thenReturn(false);
        CloseParser closeParser = new CloseParser();

        try {
            //When
            closeParser.parseFile(file);
            fail("parseFile should have failed because the file is not readable");
        }catch (IllegalArgumentException ex){
            //then
            assertThat(ex.getMessage()).isEqualTo("The file is not readable");
        }
    }

    @Test
    public void parseFile_fail_if_the_specified_file_is_not_a_txt_file() throws Exception {
        //Given
        File file = mock(File.class);
        when(file.canRead()).thenReturn(true);
        when(file.getName()).thenReturn("file.fake");
        CloseParser closeParser = new CloseParser();

        try {
            //When
            closeParser.parseFile(file);
            fail("parseFile should have failed because the file is not a .txt file");
        }catch (IllegalArgumentException ex){
            //Then
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

    @Test
    public void parseClose_should_fail_if_the_specified_generators_is_null() throws Exception {
        //Given
        CloseParser parser = new CloseParser();

        try {
            //When
            parser.parseRules(Lists.newArrayList(), null);
            fail("The parseRule should have failed because of the null specified generators");
        } catch (IllegalArgumentException ignored){
            //Then
        }
    }

    @Test
    public void parseClose_should_fail_if_the_specified_corpus_is_null() throws Exception {
        //Given
        CloseParser parser = new CloseParser();

        try {
            //When
            parser.parseRules(null, Lists.newArrayList());
            fail("The parseRule should have failed because of the null specified corpus");
        } catch (IllegalArgumentException ignored){
            //Then
        }
    }



    @Test
    public void parseClose_return_a_String_that_represents_the_rules_extracted_from_the_generators() throws Exception {
        //Given
        Item a=new Item("a");Item b=new Item("b");Item c=new Item("c");Item d=new Item("d");Item e=new Item("e");
        List<Generator> generators = Lists.newArrayList();

        ItemSet[] gens = new ItemSet[]{new ItemSet(a), new ItemSet(b), new ItemSet(c), new ItemSet(e),
                new ItemSet(a, b), new ItemSet(a, e), new ItemSet(b, c), new ItemSet(c, e)};

        ItemSet[] clos = new ItemSet[]{new ItemSet(a, c), new ItemSet(b, e), new ItemSet(c), new ItemSet(b, e),
                new ItemSet(a, b, c, e), new ItemSet(a, b, c, e), new ItemSet(b, c, e), new ItemSet(b, c, e)};

        int[] encs = new int[]{3, 5, 5, 5, 2, 2, 4, 4};

        for(int i = 0; i<gens.length; i++){
            Generator gen = mock(Generator.class);
            when(gen.getGenerators()).thenReturn(gens[i]);
            when(gen.getClosure()).thenReturn(clos[i]);
            when(gen.getEncounter()).thenReturn(encs[i]);
            generators.add(gen);
        }

        List<ItemSet> corpus = Lists.newArrayList(new ItemSet(a, c, d), new ItemSet(b, c, e), new ItemSet(a, b, c, e), new ItemSet(b, e),
                new ItemSet(a, b, c, e), new ItemSet(b, c, e));

        CloseParser parser = new CloseParser();

        //When
        final String s = parser.parseRules(corpus, generators);

        //Then
        System.out.println(s);



    }
}