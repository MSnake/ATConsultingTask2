// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import ru.atc.parser.lr.model.LRRuleItem;
import ru.atc.parser.lr.table.LRTableBuilder;
import ru.atc.parser.lr.table.LRTableRow;
import ru.atc.parser.lr.table.LRInputSymbol;
import ru.atc.parser.lr.table.LRTable;
import ru.atc.parser.lr.table.LRTableItem;
import ru.atc.parser.notation.element.RuleSet;

public final class LRParser {

    private final LRTable _table;

    public LRParser(final RuleSet rules) {
        LRTableBuilder builder = new LRTableBuilder(rules);
        _table = builder.getItemSetTable();
    }

    public void parse(final String str) throws IOException, LRParseException {
        parse(new StringReader(str));
    }

    public void parse(final InputStream input) throws IOException, LRParseException {
        parse(new InputStreamReader(input));
    }

    public void parse(final Reader reader) throws IOException, LRParseException {
        LRStack stack = new LRStack(new ILRParserEventHandler() {

            @Override
            public void handleShift(final LRInputSymbol symbol) {
                System.out.println(symbol.getSymbol());
            }

            @Override
            public void handleReduce(final LRRuleItem rule, final LRInputSymbol... symbols) {
                System.out.print(rule.getRuleNumber() + ": " + rule.getRuleName() + " ->");
                for (LRInputSymbol symbol : symbols) {
                    System.out.print(" " + symbol.getSymbol());
                }
                System.out.println();
            }

            @Override
            public void handleAccept() {
                System.out.println("!!!");
            }

        });
        int state = stack.getTopState();
        do {
            int input = reader.read();
            if (input < 0) {
                break;
            }
            LRInputSymbol symbol = new LRInputSymbol(String.valueOf((char) input), true, false);
            LRTableRow row = _table.getRowForState(state);
            LRTableItem item = row.getItemForInput(symbol);
            if (item == null) {
                throw new LRParseException("Wrong symbol obtained");
            }
            state = processAction(stack, symbol, item);
        } while (true);
        LRInputSymbol symbol = new LRInputSymbol("$", false, true);
        LRTableRow row = _table.getRowForState(state);
        LRTableItem item = row.getItemForInput(symbol);
        state = processAction(stack, symbol, item);
    }

    private int processAction(final LRStack stack, final LRInputSymbol symbol, final LRTableItem item) throws LRParseException {
        if (item != null && (symbol.isTerminal() || symbol.isEndOfInput())) {
            if (item.isShiftAction()) {
                return processShiftAction(stack, symbol, item);
            } else if (item.isReduceAction()) {
                return processReduceAction(stack, symbol, item);
            } else if (item.isAcceptAction()) {
                return processAcceptAction(stack);
            }
            throw new LRParseException("Wrong symbol");
        } else {
            throw new LRParseException("Wrong symbol");
        }
    }

    private int processShiftAction(final LRStack stack, final LRInputSymbol symbol, final LRTableItem item) throws LRParseException {
        int newState = item.getNubmer();
        stack.shift(symbol, newState);
        return stack.getTopState();
    }

    private int processReduceAction(final LRStack stack, final LRInputSymbol symbol, final LRTableItem item) throws LRParseException {
        stack.reduce(_table, item.getReduceRule());
        LRTableRow row = _table.getRowForState(stack.getTopState());
        LRTableItem newItem = row.getItemForInput(symbol);
        return processAction(stack, symbol, newItem);
    }

    private int processAcceptAction(final LRStack stack) throws LRParseException {
        stack.accept();
        return 0;
    }

}
