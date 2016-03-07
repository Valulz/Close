package com.valulz.close.model;

import org.junit.Test;

import java.util.SortedSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ItemSetTest {

    @Test
    public void constructor_with_multiple_items_parameter_create_an_new_set_filled_with_those_items() throws Exception {
        //Given
        Item a=new Item("a");
        Item b=new Item("b");

        //When
        SortedSet<Item> itemSet = new ItemSet(a, b);

        //Then
        assertThat(itemSet).contains(a,b);
    }

    @Test
    public void constructor_with_an_ItemSet_parameter_make_a_copy_of_the_given_parameter() throws Exception {
        //Given
        Item a = new Item("a");
        ItemSet given = new ItemSet(a);

        //When
        SortedSet<Item> itemSet = new ItemSet(given);

        //Then
        assertThat(itemSet).isEqualTo(given);
        assertThat(itemSet).contains(a);
    }

    @Test
    public void constructor_with_multiple_items_parameter_shall_fail_if_one_of_the_item_is_null() throws Exception {
        //Given
        Item a = new Item("a");
        Item b = new Item("b");

        try {
            new ItemSet(a, null, b);
            fail("Constructor should have fail with the given null item");
        }catch (IllegalArgumentException ex){
            assertThat(ex.getMessage()).isEqualTo("An item cannot be null");
        }
    }

    @Test
    public void constructor_with_items_parameter_shall_fail_if_a_null_parameter_is_given() throws Exception {
        //Given

        try {
            //When
            new ItemSet((Item[]) null);
            fail("Constructor cannot create a new ItemSet with a null parameter given");
        }catch (IllegalArgumentException ex){
            //Then
            assertThat(ex.getMessage()).isEqualTo("The given items cannot be null");
        }
    }

    @Test
    public void constructor_with_a_null_ItemSet_parameter_fail() throws Exception {
        //Given

        try {
            //When
            new ItemSet((ItemSet) null);
            fail("Constructor cannot create a new ItemSet with a null parameter given");
        }catch (IllegalArgumentException ex){
            //Then
            assertThat(ex.getMessage()).isEqualTo("ItemSet cannot be null");
        }
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
    public void compareTo_return_a_negative_value_if_the_given_set_is_larger_than_the_attribute_one() throws Exception {
        //Given
        Comparable<ItemSet> itemSet = new ItemSet(new Item("a"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isLessThan(0);
    }

    @Test
    public void compareTo_return_a_positive_value_if_the_given_set_is_smaller_than_the_attribute_one() throws Exception {
        //Given
        Comparable<ItemSet> itemSet = new ItemSet(new Item("a"), new Item("b"));
        ItemSet compare = new ItemSet(new Item("a"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isGreaterThan(0);
    }

    @Test
    public void compareTo_return_0_if_the_two_set_are_equal() throws Exception {
        //Given
        Comparable<ItemSet> itemSet = new ItemSet(new Item("a"), new Item("b"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isEqualTo(0);
        assertThat(itemSet).isEqualTo(compare);
    }

    @Test
    public void compareTo_return_a_positive_value_if_this_is_lexicographically_greater_than_the_given_one() throws Exception {
        //Given
        Comparable<ItemSet> itemSet = new ItemSet(new Item("a"), new Item("b"), new Item("z"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"), new Item("d"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isGreaterThan(0);
    }

    @Test
    public void compareTo_return_a_negative_value_if_this_is_lexicographically_lesser_than_the_given_one() throws Exception {
        //Given
        Comparable<ItemSet> itemSet = new ItemSet(new Item("a"), new Item("b"), new Item("d"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"), new Item("z"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isLessThan(0);
    }



}