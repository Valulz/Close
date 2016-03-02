package com.valulz.close.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ItemTest {

    @Test
    public void constructor_must_fill_name() throws Exception {
        //Given
        String name = "That name is the greatest of all time";

        //When
        Item item = new Item(name);

        //Then
        assertThat(item.getName()).isEqualTo(name);
    }

    @Test
    public void constructor_fail_if_name_is_null() throws Exception {
        //Given

        try{
            //When
            new Item(null);
            fail("The name cannot be null");
        }catch (IllegalArgumentException ex){
            //Then
            assertThat(ex.getMessage()).isEqualTo("The name of the item must not be null");
        }
    }

    @Test
    public void equals_return_true_if_2_items_have_same_name() throws Exception {
        //Given
        Item i1 = new Item("This is a good name");
        Item i2 = new Item("This is a good name");

        //When
        final boolean equalsTo = i1.equals(i2);

        //Then
        assertThat(equalsTo).isTrue();
    }

    @Test
    public void equals_return_false_if_2_items_have_2_different_name() throws Exception {
        //Given
        Item i1 = new Item("This is a good name");
        Item i2 = new Item("This is the MOTHER OF ALL NAME :D");

        //When
        final boolean equalsTo = i1.equals(i2);

        //Then
        assertThat(equalsTo).isFalse();
    }

    @Test
    @SuppressWarnings("all") //Compiler raise a warning for the item.equals'"Well no")
    public void equals_return_false_if_the_given_class_is_different() throws Exception {
        //Given
        Item item = new Item("A");

        //When
        final boolean equalsTo = item.equals("Well no");

        //Then
        assertThat(equalsTo).isFalse();
    }

    @Test
    @SuppressWarnings("all") //Compiler raise a warning for the item.equals(null);
    public void equals_return_false_if_the_given_parameter_is_null() throws Exception {
        //Given
        Item item = new Item("What Up");

        //When
        final boolean equalsTo = item.equals(null);

        //Then
        assertThat(equalsTo).isFalse();

    }

    @Test
    public void hashCode_return_same_number_if_2_items_have_same_name() throws Exception {
        //Given
        Item i1 = new Item("This is a good name");
        Item i2 = new Item("This is a good name");

        //When
        final boolean sameHashCode = i1.hashCode() == i2.hashCode();

        //Then
        assertThat(sameHashCode).isTrue();
    }

    @Test
    public void hashCode_return_2_different_number_if_2_items_have_different_name() throws Exception {
        //Given
        Item i1 = new Item("This is a good name");
        Item i2 = new Item("This is the MOTHER OF ALL NAME :D");

        //When
        final boolean sameHashCode = i1.hashCode() == i2.hashCode();

        //Then
        assertThat(sameHashCode).isFalse();
    }

    @Test
    public void compareTo_returns_a_negative_number_if_an_item_is_lexicographically_lesser_than_an_other() throws Exception {
        //Given
        Item i1 = new Item("ABaaf");
        Item i2 = new Item("brebrf");

        //When
        final int compareTo = i1.compareTo(i2);

        //Then
        assertThat(compareTo).isLessThan(0);
    }

    @Test
    public void compareTo_return_0_if_two_item_have_same_names() throws Exception {
        //Given
        Item i1 = new Item("ABC");
        Item i2 = new Item("ABC");

        //When
        final int compareTo = i1.compareTo(i2);

        //Then
        assertThat(compareTo).isEqualTo(0);
    }

    @Test
    public void compareTo_returns_a_number_greater_than_0_if_an_item_is_lexicographically_greater_than_an_other() throws Exception {
        //Given
        Item i1 = new Item("ZEFfece");
        Item i2 = new Item("Aef");

        //When
        final int compareTo = i1.compareTo(i2);

        //Then
        assertThat(compareTo).isGreaterThan(0);
    }


}