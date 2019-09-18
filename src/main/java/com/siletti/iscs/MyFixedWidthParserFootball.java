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

class MyFixedWidthParserFootball extends MyFixedWidthParser  {

    public MyFixedWidthParserFootball(File file) throws IOException {
        super(file);
    }

    @Override
    List<String[]> getTheList(File inputFile) throws IOException {

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
            throw e;
        }

    }

    @Override
    Map<String, Integer> stringIntegerMap() {

        Map<String, Integer> myMap = theList.stream()     //  myRows.stream()
                .filter(arr -> arr[0].charAt(0) != '-')
                .collect(Collectors.toMap(strings -> strings[0], strings -> Math.abs(tryParseIntLast3Chars(strings[1]) - tryParseIntLast3Chars(strings[2]))));

        return myMap;
    }


}
