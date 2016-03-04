package com.valulz.close.model;

import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
    public void constructor_with_ItemSet_parameter_make_a_copy_of_the_given_parameter() throws Exception {
        //Given
        Item a = new Item("a"); Item b = new Item("b");
        ItemSet expected = new ItemSet(a, b);

        //When
        ItemSet itemSet = new ItemSet(expected);

        //Then
        assertThat(itemSet).isEqualTo(expected);
    }

    @Test
    public void size_return_the_number_of_item_inside_the_set() throws Exception {
        //Given
        Item a = new Item("a"); Item b = new Item("b");

        //When
        ItemSet itemSet = new ItemSet(a, b);

        //Then
        assertThat(itemSet.size()).isEqualTo(2);
    }

    @Test
    public void isEmpty_return_true_if_the_set_contains_no_item() throws Exception {
        //Given
        ItemSet itemSet = new ItemSet();

        //When
        final boolean isEmpty = itemSet.isEmpty();

        //Then
        assertThat(isEmpty).isTrue();
    }

    @Test
    public void isEmpty_return_false_if_the_set_contains_at_least_one_item() throws Exception {
        //Given
        ItemSet itemSet = new ItemSet(new Item("a"));

        //When
        final boolean isEmpty = itemSet.isEmpty();

        //Then
        assertThat(isEmpty).isFalse();
    }

    @Test
    public void compareTo_fail_if_the_given_parameter_is_null() throws Exception {
        //Given

        try{
            //When
            new ItemSet().compareTo(null);
            fail("compareTo cannot have a null parameter");
        }catch (IllegalArgumentException ignore){
            //Then
        }
    }

    @Test
    public void compareTo_return_minus_1_if_the_given_set_is_larger_than_the_attribute_one() throws Exception {
        //Given
        ItemSet itemSet = new ItemSet(new Item("a"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isEqualTo(-1);
    }
}