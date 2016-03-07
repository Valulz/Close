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
}