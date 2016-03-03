package com.valulz.close.model;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CloseTest {

    @Test
    public void closure_cannot_have_a_null_candidate() throws Exception {
        //Given
        SortedSet<Item> set = new TreeSet<>();
        set.add(new Item("a"));

        Close close = new Close();

        //When
        try {
            close.closure(null, Lists.newArrayList(set, null));
            fail("Closure cannot have a null generator parameter");
        }catch (IllegalArgumentException ignore){
            //Then
        }
    }

    @Test
    public void closure_cannot_have_a_null_list_item() throws Exception {
        //Given
        SortedSet<Item> set = new TreeSet<>();
        set.add(new Item("a"));

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
    @SuppressWarnings("unchecked")
    public void close_create_generators_from_candidates_given() throws Exception {
        //Given
        Item a=new Item("a");Item b=new Item("b");Item c=new Item("c");Item d=new Item("d");Item e=new Item("e");
        final int NB_ITEM = 5;

        SortedSet<Item>[] generators = new SortedSet[NB_ITEM];
        generators[0]=new TreeSet<>(); generators[0].add(a);
        generators[1]=new TreeSet<>(); generators[1].add(b);
        generators[2]=new TreeSet<>(); generators[2].add(c);
        generators[3]=new TreeSet<>(); generators[3].add(d);
        generators[4]=new TreeSet<>(); generators[4].add(e);

        List<SortedSet<Item>> listItems = Lists.newArrayList();
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(a, c, d)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(b, c, e)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(a, b, c, e)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(b, e)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(a, b, c, e)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(b, c, e)));

        List<SortedSet<Item>> candidates = Lists.newArrayList();
        for(SortedSet<Item> item : generators){
            candidates.add(new TreeSet<>(item));
        }

        SortedSet<Item>[] closes = new SortedSet[NB_ITEM];
        closes[0]=new TreeSet<>(); closes[0].add(a); closes[0].add(c);
        closes[1]=new TreeSet<>(); closes[1].add(b); closes[1].add(e);
        closes[2]=new TreeSet<>(); closes[2].add(c);
        closes[3]=new TreeSet<>(); closes[3].add(a); closes[3].add(c); closes[3].add(d);
        closes[4]=new TreeSet<>(); closes[4].add(b); closes[4].add(e);

        int[] encounters = new int[]{3, 5, 5, 1, 5};

        Comparator<Generator> comparator = (o1, o2) -> o1.getGenerators().first().compareTo(o2.getGenerators().first());

        Close close = new Close();

        //When
        List<Generator> result = close.closure(candidates, listItems);

        //Then
        Collections.sort(result, comparator);

        for(int i = 0; i<result.size(); i++){
            assertThat(result.get(i).getGenerators()).isEqualTo(generators[i]);
            assertThat(result.get(i).getFerme()).isEqualTo(closes[i]);
            assertThat(result.get(i).getEncounter()).isEqualTo(encounters[i]);
        }
    }
}