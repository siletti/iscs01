package com.siletti.iscs;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class MyFixedWidthParser {

     int tryParseIntLast3Chars(String value) {

        String last3Digits;     //substring that will contain last 3 characters

        if (value.length() > 3) {
            last3Digits = value.substring(value.length() - 3);
        } else {
            last3Digits = value;
        }

        try {
            return Integer.parseInt(last3Digits.trim());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }


    String getKeyWithMinIntVal(Map<String, Integer> myMap) {

        Optional<Map.Entry<String, Integer>> myMinEntry = myMap.entrySet()
                .stream()
                .min(Comparator.comparingInt(Map.Entry::getValue));

        return myMinEntry.get().getKey();
    }

    abstract List<String[]> getTheList(File inputFile);

    abstract Map<String, Integer> stringIntegerMap (List<String[]> myRows);

}
