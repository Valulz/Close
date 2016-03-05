package com.valulz.close.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ItemSetTest {

    @Test
    public void constructor_with_multiple_items_parameter_create_an_new_set_filled_with_those_items() throws Exception {
        //Given
        Item a=new Item("a");
        Item b=new Item("b");

        //When
        ItemSet itemSet = new ItemSet(a, b);

        //Then
        assertThat((Iterable<? extends Item>) itemSet).contains(a,b);

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
        ItemSet itemSet;
        itemSet = new ItemSet(new Item("a"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isLessThan(0);
    }

    @Test
    public void compareTo_return_a_positive_value_if_the_given_set_is_smaller_than_the_attribute_one() throws Exception {
        //Given
        ItemSet itemSet;
        itemSet = new ItemSet(new Item("a"), new Item("b"));
        ItemSet compare = new ItemSet(new Item("a"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isGreaterThan(0);
    }

    @Test
    public void compareTo_return_0_if_the_two_set_are_equal() throws Exception {
        //Given
        ItemSet itemSet = new ItemSet(new Item("a"), new Item("b"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isEqualTo(0);
        assertThat((Iterable<? extends Item>) itemSet).isEqualTo(compare);
    }

    @Test
    public void compareTo_return_a_positive_value_if_this_is_lexicographically_greater_than_the_given_one() throws Exception {
        //Given
        ItemSet itemSet;
        itemSet = new ItemSet(new Item("a"), new Item("b"), new Item("z"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"), new Item("d"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isGreaterThan(0);
    }

    @Test
    public void compareTo_return_a_negative_value_if_this_is_lexicographically_lesser_than_the_given_one() throws Exception {
        //Given
        ItemSet itemSet;
        itemSet = new ItemSet(new Item("a"), new Item("b"), new Item("d"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"), new Item("z"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isLessThan(0);
    }



}