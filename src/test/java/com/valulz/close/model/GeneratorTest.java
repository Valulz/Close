package com.valulz.close.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class GeneratorTest {

    @Test
    public void constructor_must_fail_if_the_generator_parameter_is_null() throws Exception {
        //Given
        ItemSet items = new ItemSet(new Item("a"));

        try{
            //When
            new Generator(null, items);
            fail("Generators cant have a null parameter and should have thrown an IllegalArgumentException");
        }catch (IllegalArgumentException ex){
            //Then
            assertThat(ex.getMessage()).isEqualTo("Generators and closure cannot be null");
        }
    }

    @Test
    public void constructor_must_fail_if_the_closure_parameter_is_null() throws Exception {
        //Given
        ItemSet items = new ItemSet(new Item("a"));

        try{
            //When
            new Generator(items, null);
            fail("Generators cant have a null parameter and should have thrown an IllegalArgumentException");
        }catch (IllegalArgumentException ex){

            //Then
            assertThat(ex.getMessage()).isEqualTo("Generators and closure cannot be null");
        }
    }

    @Test
    public void constructor_fail_if_generators_contains_no_item() throws Exception {
        //Given
        ItemSet closure = new ItemSet(new Item("a"));

        try{
            new Generator(new ItemSet(), closure);
            fail("generators must have at least one item");
        }catch (IllegalArgumentException ex){
            assertThat(ex.getMessage()).isEqualTo("Both Sets must have at least one item");
        }
    }

    @Test
    public void constructor_fail_if_closure_contains_no_item() throws Exception {
        //Given
        ItemSet generators = new ItemSet(new Item("a"));

        try{
            new Generator(generators, new ItemSet());
            fail("closure must have at least one item");
        }catch (IllegalArgumentException ex){
            assertThat(ex.getMessage()).isEqualTo("Both Sets must have at least one item");
        }
    }

    @Test
    public void constructor_must_fill_generators_closure_and_encounter_attribute() throws Exception {
        //Given
        ItemSet gen = new ItemSet(new Item("Ceci"), new Item("Est"));
        ItemSet closure = new ItemSet(new Item("Un"), new Item("Peu"), new Item("Long"));

        //When
        Generator generator = new Generator(new ItemSet(gen), new ItemSet(closure));

        //Then
        assertThat((Iterable<? extends Item>) generator.getGenerators()).isEqualTo(gen);
        assertThat((Iterable<? extends Item>) generator.getClosure()).isEqualTo(closure);
        assertThat(generator.getEncounter()).isEqualTo(1);
    }

    @Test
    public void newEncounter_fail_if_the_parameter_is_null() throws Exception {
        //Given
        ItemSet items = new ItemSet(new Item("a"));
        Generator gen = new Generator(items, items);

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
    public void newEncounter_fail_if_the_parameter_is_empty() throws Exception {
        //Given
        ItemSet items = new ItemSet(new Item("a"));
        Generator gen = new Generator(items, items);

        try{
            //When
            gen.newEncounter(new ItemSet());
            fail("The newEncounter method should have fail because of the empty parameter");
        } catch(IllegalArgumentException ex){

            //Then
            assertThat(ex.getMessage()).isEqualTo("The given itemSet must have at least one element");
        }
    }

    @Test
    public void newEncounter_increments_encounter_and_change_closure() throws Exception {
        //Given
        Item i1 = new Item("Ceci");
        Item i2 = new Item("Est");
        Item i3 = new Item("Un");
        Item i4 = new Item("Test");

        ItemSet given = new ItemSet(i1, i2, i4);
        ItemSet parameter = new ItemSet(i1,i3,i4);
        ItemSet expected = new ItemSet(i1, i4);

        Generator gen = new Generator(new ItemSet(new Item("a")), given);

        //When
        gen.newEncounter(parameter);

        //Then
        assertThat((Iterable<? extends Item>) gen.getClosure()).isEqualTo(expected);
        assertThat(gen.getEncounter()).isEqualTo(2);
    }

    @Test
    public void genNewItemSet_fail_if_the_item_set_is_null() throws Exception {
        //Given
        ItemSet items = new ItemSet(new Item("a"));
        Generator generator = new Generator(items, items);

        try{
            //When
            generator.genNewItemSet(null);
            fail("getNewItemSet should have failed because of the null parameter");
        } catch (IllegalArgumentException ignored){
            //Then
        }
    }

    @Test
    public void getNewItemSet_fail_if_the_size_of_the_item_set_is_different_from_the_generator_size() throws Exception {
        //Given
        ItemSet gen = new ItemSet(new Item("LOL"));
        Generator generator = new Generator(gen, gen);

        try {
            //When
            generator.genNewItemSet(new ItemSet());
            fail("getNewItemSet should have failed because the generator and the itemSet have two different size");
        }catch (IllegalArgumentException ignored){
            //Then
        }
    }

    @Test
    public void getNewItemSet_return_intersection_of_two_item_set_of_size_1() throws Exception {
        //Given
        Item a = new Item("a"); Item b = new Item("b");

        ItemSet gene = new ItemSet(a);
        ItemSet para = new ItemSet(b);
        ItemSet expected = new ItemSet(a, b);
        ItemSet closure = new ItemSet(a);

        Generator generator = new Generator(gene, closure);

        //When
        final ItemSet items = generator.genNewItemSet(para);

        //Then
        assertThat((Iterable<? extends Item>) items).isEqualTo(expected);
    }

    @Test
    public void genNewItemSet_returns_null_if_generators_and_given_item_set_different() throws Exception {
        //Given
        Item a = new Item("a"); Item b = new Item("b"); Item c = new Item("c");

        ItemSet gene = new ItemSet(a, b);
        ItemSet para = new ItemSet(b, c);

        Generator generator = new Generator(gene, new ItemSet(a));

        //When
        final ItemSet items = generator.genNewItemSet(para);

        //Then
        assertThat((Iterable<? extends Item>) items).isEqualTo(null);
    }

    @Test
    public void genNewItemSet_return_the_concatenation_of_generators_and_the_given_item_set() throws Exception {
        //Given
        Item a = new Item("a"); Item b = new Item("b"); Item c = new Item("c");Item d = new Item("d");

        ItemSet gene = new ItemSet(a, b, c);
        ItemSet para = new ItemSet(a, b, d);
        ItemSet expected = new ItemSet(a, b, c, d);

        Generator generator = new Generator(gene, new ItemSet(a));

        //When
        final ItemSet items = generator.genNewItemSet(para);

        //Then
        assertThat((Iterable<? extends Item>) items).isEqualTo(expected);

    }
}
