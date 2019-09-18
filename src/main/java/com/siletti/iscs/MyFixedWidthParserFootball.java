package com.siletti.iscs;

import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class MyFixedWidthParserFootball extends MyFixedWidthParser  {
/*
    private static int tryParseIntLast3Chars(String value) {

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
    }*/

    @Override
    List<String[]> getTheList(File inputFile) {

        // creates the sequence of field lengths in the file to be parsed
        FixedWidthFields lengths = new FixedWidthFields(7, 15, 3, 6, 4, 4, 6, 7, 7);
        // creates the default settings for a fixed width parser
        FixedWidthParserSettings parserSettings = new FixedWidthParserSettings(lengths);
        parserSettings.setHeaderExtractionEnabled(true);
        parserSettings.selectFields("Team", "F", "A");
        parserSettings.getFormat().setLineSeparator("\n");

        // creates a fixed-width parser with the given settings
        FixedWidthParser parser = new FixedWidthParser(parserSettings);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            List<String[]> allRows = parser.parseAll(br);
            return allRows;
        } catch (IOException e) {
            System.out.printf("\n  Cannot read file: %s ! (Enter to exit)\n", inputFile.getName());
            System.exit(0);
        }

        return null;
    }

    @Override
    Map<String, Integer> stringIntegerMap (List<String[]> myRows){

        Map<String, Integer> myMap = myRows.stream()
                .filter(arr -> arr[0].charAt(0) != '-')
                .collect(Collectors.toMap(strings -> strings[0], strings -> Math.abs(tryParseIntLast3Chars(strings[1]) - tryParseIntLast3Chars(strings[2]))));

        myMap.forEach((key, value) -> {
            System.out.println("Key : " + key + " Value : " + value);
        });

        return myMap;
    }

/*
    String getKeyWithMinIntVal(Map<String, Integer> myMap) {

        Optional<Map.Entry<String, Integer>> myMinEntry = myMap.entrySet()
                .stream()
                .min(Comparator.comparingInt(Map.Entry::getValue));

        return myMinEntry.get().getKey();
    }*/
}
