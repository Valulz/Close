package com.valulz.close.model;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CloseTest {

    @Test
    public void test_fermeture() throws Exception {
        //Given
        Item a=new Item("a");Item b=new Item("b");Item c=new Item("c");Item d=new Item("d");Item e=new Item("e");

        List<SortedSet<Item>> listItems = Lists.newArrayList();
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(a, c, d)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(b, c, e)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(a, b, c, e)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(b, e)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(a, b, c, e)));
        listItems.add(new TreeSet<>(Sets.newLinkedHashSet(b, c, e)));

        List<SortedSet<Item>> candidates = Lists.newArrayList();
        candidates.add(new TreeSet<>(Sets.newLinkedHashSet(a)));
        candidates.add(new TreeSet<>(Sets.newLinkedHashSet(b)));
        candidates.add(new TreeSet<>(Sets.newLinkedHashSet(c)));
        candidates.add(new TreeSet<>(Sets.newLinkedHashSet(d)));
        candidates.add(new TreeSet<>(Sets.newLinkedHashSet(e)));

        List<Generator> expected = Lists.newArrayList();
        expected.add(new Generator(new TreeSet<>(Sets.newLinkedHashSet(a)), new TreeSet<>(Sets.newLinkedHashSet(a, c))));
        expected.add(new Generator(new TreeSet<>(Sets.newLinkedHashSet(b)), new TreeSet<>(Sets.newLinkedHashSet(b, e))));
        expected.add(new Generator(new TreeSet<>(Sets.newLinkedHashSet(c)), new TreeSet<>(Sets.newLinkedHashSet(c))));
        expected.add(new Generator(new TreeSet<>(Sets.newLinkedHashSet(d)), new TreeSet<>(Sets.newLinkedHashSet(a, c, d))));
        expected.add(new Generator(new TreeSet<>(Sets.newLinkedHashSet(e)), new TreeSet<>(Sets.newLinkedHashSet(b, e))));

        int[] exEncounter = new int[]{3, 5, 5, 1, 5};

        Comparator<Generator> comparator = (o1, o2) -> o1.getGenerators().first().compareTo(o2.getGenerators().first());

        Close close = new Close();

        //When
        List<Generator> result = close.fermeture(candidates, listItems);

        //Then
        Collections.sort(result, comparator);

        for(int i = 0; i<result.size(); i++){
            assertThat(result.get(i).getGenerators()).isEqualTo(expected.get(i).getGenerators());
            assertThat(result.get(i).getFerme()).isEqualTo(expected.get(i).getFerme());
            assertThat(result.get(i).getEncounter()).isEqualTo(exEncounter[i]);
        }
    }
}