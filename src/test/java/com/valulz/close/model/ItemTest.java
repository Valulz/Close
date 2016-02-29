package com.valulz.close.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void constructorMustFillTheName() throws Exception {
        String name = "That name is the greatest of all time";

        Item item = new Item(name);

        assertEquals(item.getName(), name);
    }

    @Test
    public void constructorThrowAnIllegalArgumentExceptionIfTheNameIsNull() throws Exception {
        try{
            Item item = new Item(null);
            fail("The name cannot be null");
        }catch (IllegalArgumentException ex){
            assertEquals(ex.getMessage(), "The name of the item must not be null");
        }

    }

    @Test
    public void equalsReturnTrueIfTwoItemsHaveTheSameName() throws Exception {
        Item i1 = new Item("This is a good name");
        Item i2 = new Item("This is a good name");

        assertEquals(i1, i2);
    }

    @Test
    public void equalsReturnFalseIfTwoItemsHaveTwoDifferentName() throws Exception {
        Item i1 = new Item("This is a good name");
        Item i2 = new Item("This is the MOTHER OF ALL NAME :D");

        assertNotEquals(i1, i2);
    }

    @Test
    public void hashCodeReturnTheSameNumberIfTwoItemsHaveTheSameName() throws Exception {
        Item i1 = new Item("This is a good name");
        Item i2 = new Item("This is a good name");

        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    public void hashCodeReturnTwoDifferentNumberIfTwoItemsHaveDifferentName() throws Exception {
        Item i1 = new Item("This is a good name");
        Item i2 = new Item("This is the MOTHER OF ALL NAME :D");

        assertNotEquals(i1.hashCode(), i2.hashCode());
    }
}