package com.siletti.iscs;

import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class MyFixedWidthParserWeather extends MyFixedWidthParser {

    public MyFixedWidthParserWeather(File file) throws IOException {
        super(file);
    }

    @Override
    List<String[]> getTheList(File inputFile) throws IOException {

        // creates the sequence of field lengths in the file to be parsed
        FixedWidthFields lengths = new FixedWidthFields(4, 4, 6, 6, 10, 4, 7, 4, 9, 3, 5, 4, 5, 4, 4, 3, 7);
        // creates the default settings for a fixed width parser
        FixedWidthParserSettings parserSettings = new FixedWidthParserSettings(lengths);
        parserSettings.setHeaderExtractionEnabled(true);
        parserSettings.selectFields("Dy", "MxT", "MnT");
        parserSettings.getFormat().setLineSeparator("\n");
        // creates a fixed-width parser with the given settings
        FixedWidthParser parser = new FixedWidthParser(parserSettings);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            List<String[]> allRows = parser.parseAll(br);
            return allRows;
        } catch (IOException e) {
            throw e;
        }

    }

    @Override
    Map<String, Integer> stringIntegerMap() {

        Map<String, Integer> myMap = theList.stream()     //  myRows.stream()
                .filter(arr -> {
                    if (tryParseIntLast3Chars(arr[0]) > 0) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .collect(Collectors.toMap(strings -> strings[0], strings -> Math.abs(tryParseIntLast3Chars(strings[1]) - tryParseIntLast3Chars(strings[2]))));

      /*  myMap.forEach((key, value) -> {
            System.out.println("Key : " + key + " Value : " + value);
        });*/

        return myMap;
    }

}
