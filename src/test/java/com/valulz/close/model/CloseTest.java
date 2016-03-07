package com.valulz.close.model;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CloseTest {

    @Test
    public void closure_cannot_have_a_null_candidate() throws Exception {
        //Given
        ItemSet set = new ItemSet(new Item("a"));

        Close close = new Close();

        //When
        try {
            close.closure(null, Lists.newArrayList(set, null));
            fail("Closure cannot have a null candidate parameter");
        }catch (IllegalArgumentException ignore){
            //Then
        }
    }

    @Test
    public void closure_cannot_have_a_null_list_item() throws Exception {
        //Given
        ItemSet set = new ItemSet(new Item("a"));
        Close close = new Close();

        //When
        try {
            close.closure(Lists.newArrayList(set, null), null);
            fail("Closure cannot have a null list item parameter");
        }catch (IllegalArgumentException ignore){
            //Then
        }
    }

    @Test
    public void closure_cannot_have_an_empty_candidate_parameter() throws Exception {
        //Given
        ItemSet set = new ItemSet(new Item("a"));

        Close close = new Close();

        //When
        try {
            close.closure(Lists.emptyList(), Lists.newArrayList(set, null));
            fail("Closure cannot have an empty candidate parameter");
        }catch (IllegalArgumentException ignore){
            //Then
        }
    }

    @Test
    public void closure_cannot_have_an_empty_list_item_parameter() throws Exception {
        //Given
        ItemSet set = new ItemSet(new Item("a"));

        Close close = new Close();

        //When
        try {
            close.closure(Lists.newArrayList(set, null), Lists.emptyList());
            fail("Closure cannot have an empty listItem parameter");
        }catch (IllegalArgumentException ignore){
            //Then
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void close_create_generators_from_candidates_given() throws Exception {
        //Given
        Item a=new Item("a");Item b=new Item("b");Item c=new Item("c");Item d=new Item("d");Item e=new Item("e");
        final int NB_ITEM = 5;

        ItemSet[] generators = new ItemSet[NB_ITEM];
        generators[0]=new ItemSet(a);
        generators[1]=new ItemSet(b);
        generators[2]=new ItemSet(c);
        generators[3]=new ItemSet(d);
        generators[4]=new ItemSet(e);

        List<ItemSet> listItems = Lists.newArrayList();
        listItems.add(new ItemSet(a, c, d));
        listItems.add(new ItemSet(b, c, e));
        listItems.add(new ItemSet(a, b, c, e));
        listItems.add(new ItemSet(b, e));
        listItems.add(new ItemSet(a, b, c, e));
        listItems.add(new ItemSet(b, c, e));

        List<ItemSet> candidates = Lists.newArrayList();
        for(ItemSet item : generators){
            candidates.add(new ItemSet(item));
        }

        ItemSet[] closes = new ItemSet[NB_ITEM];
        closes[0]=new ItemSet(a, c);
        closes[1]=new ItemSet(b, e);
        closes[2]=new ItemSet(c);
        closes[3]=new ItemSet(a, c, d);
        closes[4]=new ItemSet(b, e);

        int[] encounters = new int[]{3, 5, 5, 1, 5};

        Comparator<Generator> comparator = (o1, o2) -> o1.getGenerators().first().compareTo(o2.getGenerators().first());

        Close close = new Close();

        //When
        List<Generator> result = close.closure(candidates, listItems);

        //Then
        Collections.sort(result, comparator);

        for(int i = 0; i<result.size(); i++){
            assertThat((Iterable<? extends Item>) result.get(i).getGenerators()).isEqualTo(generators[i]);
            assertThat((Iterable<? extends Item>) result.get(i).getClosure()).isEqualTo(closes[i]);
            assertThat(result.get(i).getEncounter()).isEqualTo(encounters[i]);
        }
    }

    @Test
    public void generateCloseKPlus1_fail_if_the_given_generator_is_null() throws Exception {
        //Given
        Close close = new Close();

        try{
            //When
            close.generateCloseKPlus1(null, Lists.newArrayList());
            fail("The method generateCloseKPlus1 should have failed because of the null generator parameter");
        } catch (IllegalArgumentException ex){
            //Then
            assertThat(ex.getMessage()).isEqualTo("Generators and Closure cannot be null");
        }
    }

    @Test
    public void generateCloseKPlus1_fail_if_the_given_closure_is_null() throws Exception {
        //Given
        Close close = new Close();

        try{
            //When
            close.generateCloseKPlus1(Lists.emptyList(), null);
            fail("The method generateCloseKPlus1 should have failed because of the null closure parameter");
        } catch (IllegalArgumentException ex){
            //Then
            assertThat(ex.getMessage()).isEqualTo("Generators and Closure cannot be null");
        }
    }

    @Test
    public void generateCloseKPlus1_shall_generate_an_empty_list_if_the_given_generator_is_empty() throws Exception {
        //Given
        Close close = new Close();
        List<ItemSet> closure = Lists.newArrayList();
        closure.add(new ItemSet(new Item("a")));

        //When
        final List<ItemSet> generators = close.generateCloseKPlus1(Lists.emptyList(), closure);

        //Then
        assertThat(generators).isEmpty();
    }

    @Test
    public void generateCloseKPlus1_shall_generate_all_ItemSet_from_the_given_generator_if_the_closure_is_empty() throws Exception {
        //Given
        Item a = new Item("a");
        Item b = new Item("b");
        Item c = new Item("c");
        Item d = new Item("d");

        Close close = new Close();

        List<Generator> generators = Lists.newArrayList();
        generators.add(new Generator(new ItemSet(c, d), new ItemSet(c, d)));
        generators.add(new Generator(new ItemSet(b, d), new ItemSet(a, b)));
        generators.add(new Generator(new ItemSet(b, c), new ItemSet(b, c)));
        generators.add(new Generator(new ItemSet(a, c), new ItemSet(a, c)));

        //When
        final List<ItemSet> itemSets = close.generateCloseKPlus1(generators, Lists.newArrayList());

        //Then
        assertThat(itemSets).containsExactly(new ItemSet(b, c, d));
    }

    @Test
    public void generateCloseKPlus1_generate_an_empty_ItemSet_from_the_given_generator_if_the_closure_already_contains_the_generated_ItemSet() throws Exception {
        //Given
        Item a = new Item("a");
        Item b = new Item("b");
        Item c = new Item("c");
        Item d = new Item("d");

        Close close = new Close();

        List<Generator> generators = Lists.newArrayList();
        generators.add(new Generator(new ItemSet(a, b), new ItemSet(a, b)));
        generators.add(new Generator(new ItemSet(b, c), new ItemSet(b, c)));
        generators.add(new Generator(new ItemSet(a, c), new ItemSet(a, c)));
        generators.add(new Generator(new ItemSet(c, d), new ItemSet(c, d)));

        List<ItemSet> closure = Lists.newArrayList();
        closure.add(new ItemSet(a, b, c));

        //When
        final List<ItemSet> itemSets = close.generateCloseKPlus1(generators, closure);

        //Then
        assertThat(itemSets).isEmpty();
    }
}