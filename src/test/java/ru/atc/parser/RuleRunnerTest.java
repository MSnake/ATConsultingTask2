// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;


import ru.atc.parser.lr.LRParseException;
import ru.atc.parser.lr.LRParser;
import ru.atc.parser.notation.NotationParser;
import ru.atc.parser.notation.element.RuleSet;

public final class RuleRunnerTest {

    public RuleRunnerTest() {
        super();
    }

    @Test()
    public void runRulesFromFile() throws Exception {
        InputStream notationStream = RuleRunnerTest.class.getClassLoader().getResourceAsStream("test.notation");
        Reader notationReader = new InputStreamReader(notationStream, "UTF-8");
        NotationParser notationParser = new NotationParser();
        RuleSet rules = notationParser.parse(notationReader);
        notationStream.close();

        InputStream inputStream = RuleRunnerTest.class.getClassLoader().getResourceAsStream("input.txt");
        Reader inputReader = new InputStreamReader(inputStream);
        LRParser lrParser = new LRParser(rules);
        lrParser.parse(inputReader);
        inputReader.close();
    }

}
