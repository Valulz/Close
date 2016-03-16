package com.valulz.close.control;

import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.text.ParseException;

public class SupportStringConverter extends StringConverter<Double> {
    private final DecimalFormat df = new DecimalFormat("#.######");

    public SupportStringConverter() {
    }

    @Override public String toString(Double value) {
        // If the specified value is null, return a zero-length String
        if (value == null) {
            return "";
        }

        return df.format(value);
    }

    @Override public Double fromString(String value) {
        try {
            // If the specified value is null or zero-length, return null
            if (value == null) {
                return null;
            }

            value = value.trim();

            if (value.length() < 1) {
                return null;
            }

            // Perform the requested parsing
            return df.parse(value).doubleValue();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
