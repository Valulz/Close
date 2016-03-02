package com.valulz.close.model;

import org.junit.Test;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class GeneratorTest {

    @Test
    public void constructor_must_fail_if_the_generator_parameter_is_null() throws Exception {
        //Given

        try{
            //When
            new Generator(null, new TreeSet<>());
            fail("Generators cant have a null parameter and should have thrown an IllegalArgumentException");
        }catch (IllegalArgumentException ex){

            //Then
            assertThat(ex.getMessage()).isEqualTo("Generators and ferme cannot be null");
        }
    }

    @Test
    public void constructor_must_fail_if_the_ferme_parameter_is_null() throws Exception {
        //Given

        try{
            //When
            new Generator(new TreeSet<>(), null);
            fail("Generators cant have a null parameter and should have thrown an IllegalArgumentException");
        }catch (IllegalArgumentException ex){

            //Then
            assertThat(ex.getMessage()).isEqualTo("Generators and ferme cannot be null");
        }
    }

    @Test
    public void constructor_must_fill_generators_ferme_and_encounter_attribute() throws Exception {
        //Given
        TreeSet<Item> gen = new TreeSet<>();
        TreeSet<Item> ferme = new TreeSet<>();

        gen.add(new Item("Ceci"));
        gen.add(new Item("Est"));
        ferme.add(new Item("Un"));
        ferme.add(new Item("Peu"));
        ferme.add(new Item("Long"));

        //When
        Generator generator = new Generator(new TreeSet<>(gen), new TreeSet<>(ferme));

        //Then
        assertThat(generator.getGenerators()).isEqualTo(gen);
        assertThat(generator.getFerme()).isEqualTo(ferme);
        assertThat(generator.getEncounter()).isEqualTo(1);
    }

    @Test
    public void newEncounter_fail_if_the_parameter_is_null() throws Exception {
        //Given
        Generator gen = new Generator(new TreeSet<>(), new TreeSet<>());

        try{
            //When
            gen.newEncounter(null);
            fail("The newEncounter method should have fail because of the null parameter");
        } catch(IllegalArgumentException ex){

            //Then
            assertThat(ex.getMessage()).isEqualTo("The given itemSet cannot be null");
        }
    }

    @Test
    public void newEncounter_increments_encounter_and_change_ferme() throws Exception {
        //Given
        SortedSet<Item> given = new TreeSet<>();
        SortedSet<Item> parameter = new TreeSet<>();
        SortedSet<Item> expected = new TreeSet<>();

        Item i1 = new Item("Ceci");
        Item i2 = new Item("Est");
        Item i3 = new Item("Un");
        Item i4 = new Item("Test");

        given.add(i1); given.add(i2); given.add(i4);
        parameter.add(i1); parameter.add(i3); parameter.add(i4);
        expected.add(i1); expected.add(i4);

        Generator gen = new Generator(new TreeSet<>(), given);

        //When
        gen.newEncounter(parameter);

        //Then
        assertThat(gen.getFerme()).isEqualTo(expected);
        assertThat(gen.getEncounter()).isEqualTo(2);
    }

    @Test
    public void genNewItemSet_fail_if_the_itemSet_is_null() throws Exception {
        //Given
        Generator generator = new Generator(new TreeSet<>(), new TreeSet<>());

        try{
            //When
            generator.genNewItemSet(null);
            fail("getNewItemSet should have failed because of the null parameter");
        } catch (IllegalArgumentException ignored){
            //Then
        }
    }

    @Test
    public void getNewItemSet_fail_if_the_size_of_the_itemSet_is_different_from_the_generator_size() throws Exception {
        //Given
        SortedSet<Item> gen = new TreeSet<>();
        gen.add(new Item("LOL"));
        Random random = new Random();

        Generator generator = new Generator(gen, new TreeSet<>());

        try {
            //When
            generator.genNewItemSet(new TreeSet<>());
            fail("getNewItemSet should have failed because the generator and the itemSet have two different size");
        }catch (IllegalArgumentException ignored){
            //Then
        }
    }

    @Test
    public void getNewItemSet_return_intersection_of_two_itemSet_of_size_1() throws Exception {
        //Given
        Item a = new Item("a"); Item b = new Item("b");

        SortedSet<Item> gene = new TreeSet<>();
        gene.add(a);

        SortedSet<Item> para = new TreeSet<>();
        para.add(b);

        SortedSet<Item> expected = new TreeSet<>();
        expected.add(a); expected.add(b);

        Generator generator = new Generator(gene, new TreeSet<>());

        //When
        final SortedSet<Item> items = generator.genNewItemSet(para);

        //Then
        assertThat(items).isEqualTo(expected);

    }

}