package com.siletti.iscs;

import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

import java.io.*;
import java.util.List;

class MyFixedWidthParserWeather {

    List<String[]> getTheList(File inputFile) {

        // creates the sequence of field lengths in the file to be parsed
        FixedWidthFields lengths = new FixedWidthFields(4, 4, 6, 6, 10, 4, 7, 4, 9, 3, 5, 4, 5, 4, 4, 3, 7);
        // creates the default settings for a fixed width parser
        FixedWidthParserSettings parserSettings = new FixedWidthParserSettings(lengths);
        parserSettings.setHeaderExtractionEnabled(true);
//        parserSettings.selectIndexes(0, 1, 2);
        parserSettings.selectFields("Dy", "MxT", "MnT");
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

}
