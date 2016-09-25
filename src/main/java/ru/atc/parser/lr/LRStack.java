// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NullArgumentException;

import ru.atc.parser.lr.model.LRRuleItem;
import ru.atc.parser.lr.table.LRTable;
import ru.atc.parser.lr.table.LRInputSymbol;

final class LRStack {

    private final ILRParserEventHandler _handler;

    private final List<LRInputSymbol> _symbols;

    private final List<Integer> _states;

    LRStack(final ILRParserEventHandler handler) {
        if (handler == null) {
            throw new NullArgumentException("handler");
        }
        _handler = handler;
        _symbols = new ArrayList<>();
        _symbols.add(null);
        _states = new ArrayList<>();
        _states.add(0);
    }

    void shift(final LRInputSymbol symbol, final int state) throws LRParseException {
        _symbols.add(symbol);
        _states.add(state);
        _handler.handleShift(symbol);
    }

    void reduce(final LRTable table, final LRRuleItem rule) throws LRParseException {
        int count = rule.getValueCount();
        if ((_symbols.size() - 1) < count) {
            throw new LRParseException("wrong rule");
        }
        int shift = _symbols.size() - count;
        LRInputSymbol[] symbols = new LRInputSymbol[count];
        for (int i = 0; i < count; i++) {
            symbols[i] = _symbols.get(i + shift);
        }
        for (int i = 0; i < count; i++) {
            if (symbols[i].isEndOfInput()) {
                throw new LRParseException("wrong symbol");
            }
            if (!rule.getValueAt(i).isMatchesInputSymbol(symbols[i].getSymbol(), symbols[i].isTerminal())) {
                throw new LRParseException("Not matches");
            }
        }
        for (int i = 0; i < count; i++) {
            _symbols.remove(_symbols.size() - 1);
            _states.remove(_states.size() - 1);
        }
        LRInputSymbol ruleSymbol = new LRInputSymbol(rule.getRuleName(), false, false);
        _symbols.add(ruleSymbol);
        int newState = table.getRowForState(getTopState()).getItemForInput(ruleSymbol).getNubmer();
        _states.add(newState);
        _handler.handleReduce(rule, symbols);
    }

    void accept() throws LRParseException {
        _handler.handleAccept();
    }

    int getTopState() throws LRParseException {
        if (_states.isEmpty()) {
            throw new LRParseException("Wrong size of stack");
        }
        return _states.get(_states.size() - 1);
    }

}
