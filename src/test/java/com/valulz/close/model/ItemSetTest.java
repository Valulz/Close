package com.valulz.close.model;

import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemSetTest {

    @Test
    public void empty_constructor_initialize_an_empty_set_of_item() throws Exception {
        //Given

        //When
        ItemSet itemSet = new ItemSet();

        //Then
        assertThat(itemSet.getItemSet().isEmpty()).isTrue();
    }

    @Test
    public void constructor_with_parameter_initialize_a_set_of_item_fill_with_given_items() throws Exception {
        //Given
        Item a = new Item("a"); Item b = new Item("b");

        SortedSet<Item> expected = new TreeSet<>();
        expected.add(a);
        expected.add(b);

        //When
        ItemSet itemSet = new ItemSet(a, b);

        //Then
        assertThat(itemSet.getItemSet()).isEqualTo(expected);
    }

    @Test
    public void size_return_the_number_of_item_inside_the_set() throws Exception {
        //Given
        Item a = new Item("a"); Item b = new Item("b");

        //When
        ItemSet itemSet = new ItemSet(a, b);

        //Then
        assertThat(itemSet.getItemSet().size()).isEqualTo(2);

    }
}