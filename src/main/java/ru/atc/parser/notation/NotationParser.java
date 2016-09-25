// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import ru.atc.parser.notation.state.IState;
import ru.atc.parser.notation.state.StateFactory;
import ru.atc.parser.notation.element.RuleSet;

public final class NotationParser {

    public NotationParser() {
        super();
    }

    public RuleSet parse(final String str) throws IOException, NotationParseException {
        return parse(new StringReader(str));
    }

    public RuleSet parse(final InputStream input) throws IOException, NotationParseException {
        return parse(new InputStreamReader(input));
    }

    public RuleSet parse(final Reader reader) throws IOException, NotationParseException {
        try {
            IState state = StateFactory.getFirstState();
            StackImpl stack = new StackImpl();
            int rowPos = 0;
            int colPos;

            BufferedReader buffer = new BufferedReader(reader);

            do {
                String row = buffer.readLine();
                if (row == null) {
                    break;
                }
                rowPos++;
                for (colPos = 0; colPos < row.length(); colPos++) {
                    state = state.processSymbol(row.charAt(colPos), stack, rowPos, colPos + 1);
                }
                state = state.processSymbol('\n', stack, rowPos, colPos + 1);
            } while (true);
            state.processSymbol('\n', stack, rowPos + 1, 1);

            RuleSet rules = stack.getRuleSet();
            NotationValidator.validate(rules);
            return rules;
        } finally {
            reader.close();
        }
    }

}
