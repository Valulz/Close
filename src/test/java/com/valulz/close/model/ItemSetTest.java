package com.valulz.close.model;

import org.junit.Test;

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
}