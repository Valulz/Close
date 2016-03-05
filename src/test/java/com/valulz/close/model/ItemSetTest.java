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
    public void hashCode_return_same_number_if_two_ItemSet_are_equal() throws Exception {
        //Given
        ItemSet i1 = new ItemSet(new Item("a"));
        ItemSet i2 = new ItemSet(new Item("a"));

        //When
        final boolean isSameHashCode = i1.hashCode() == i2.hashCode();

        //Then
        assertThat(isSameHashCode).isTrue();
        assertThat(i1).isEqualTo(i2);
    }

    @Test
    public void hasCode_two_empty_ItemSet_return_the_same_hashCode() throws Exception {
        //Given
        ItemSet i1 = new ItemSet();
        ItemSet i2 = new ItemSet();

        //When
        final boolean isSameHashCode = i1.hashCode() == i2.hashCode();

        //Then
        assertThat(isSameHashCode).isTrue();
        assertThat(i1.isEmpty()).isTrue();
        assertThat(i2.isEmpty()).isTrue();
    }

    @Test
    public void hasCode_return_2_different_number_if_two_ItemSet_are_different() throws Exception {
        //Given
        ItemSet i1 = new ItemSet(new Item("a"));
        ItemSet i2 = new ItemSet();

        //When
        final boolean isSameHashCode = i1.hashCode() == i2.hashCode();

        //Then
        assertThat(isSameHashCode).isFalse();
        assertThat(i1).isNotEqualTo(i2);
    }

    @Test
    public void add_fail_if_the_given_item_is_null() throws Exception {
        //Given
        ItemSet set = new ItemSet();

        try{
            //When
            set.add(null);
            fail("The ItemSet.add method should have failed because the given Item was null");
        } catch (IllegalArgumentException ex){
            assertThat(ex.getMessage()).isEqualTo("Given item cannot be null");
        }
    }

    @Test
    public void add_return_true_if_the_given_item_is_added_to_the_set() throws Exception {
        //Given
        Item a = new Item("a");
        ItemSet itemSet = new ItemSet();

        //When
        final boolean isAdded = itemSet.add(a);

        //Then
        assertThat(isAdded).isTrue();
        assertThat(itemSet.getItemSet().contains(a)).isTrue();
    }

    @Test
    public void add_return_false_if_the_given_item_was_already_present_in_the_ItemSet() throws Exception {
        //Given
        Item a = new Item("a");
        ItemSet itemSet = new ItemSet(a);

        //When
        final boolean isPresent = itemSet.getItemSet().contains(a);
        final boolean isAdded = itemSet.add(a);

        //Then
        assertThat(isPresent).isTrue();
        assertThat(isAdded).isFalse();
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
        ItemSet itemSet = new ItemSet(new Item("a"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isLessThan(0);
    }

    @Test
    public void compareTo_return_a_positive_value_if_the_given_set_is_smaller_than_the_attribute_one() throws Exception {
        //Given
        ItemSet itemSet = new ItemSet(new Item("a"), new Item("b"));
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
        assertThat(itemSet).isEqualTo(compare);
    }

    @Test
    public void compareTo_return_a_positive_value_if_this_is_lexicographically_greater_than_the_given_one() throws Exception {
        //Given
        ItemSet itemSet = new ItemSet(new Item("a"), new Item("b"), new Item("z"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"), new Item("d"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isGreaterThan(0);
    }

    @Test
    public void compareTo_return_a_negative_value_if_this_is_lexicographically_lesser_than_the_given_one() throws Exception {
        //Given
        ItemSet itemSet = new ItemSet(new Item("a"), new Item("b"), new Item("d"));
        ItemSet compare = new ItemSet(new Item("a"), new Item("b"), new Item("z"));

        //When
        final int compareTo = itemSet.compareTo(compare);

        //Then
        assertThat(compareTo).isLessThan(0);
    }



}