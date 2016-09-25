// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.table;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ru.atc.parser.lr.model.LRRuleItem;

public final class LRTable {

    private final Set<LRInputSymbol> _inputSymbols;

    private final Map<Integer, LRTableRow> _table;

    LRTable() {
        _inputSymbols = new TreeSet<>();
        _table = new TreeMap<>();
    }

    void addInputSymbol(final LRInputSymbol symbol) {
        _inputSymbols.add(symbol);
    }

    public Set<LRInputSymbol> getInputSymbols() {
        return Collections.unmodifiableSet(_inputSymbols);
    }

    void addInputSymbolForState(final int stateNum, final LRInputSymbol input, final int toStateNum) {
        LRTableRow row = addEmptyRowForState(stateNum);
        LRTableItem item = new LRTableItem();
        item.setNumber(toStateNum);
        if (input.isTerminal()) {
            item.setShiftAction();
        }
        row.addItemForInput(input, item);
    }

    LRTableRow addEmptyRowForState(final int stateNum) {
        LRTableRow row = _table.get(stateNum);
        if (row == null) {
            row = new LRTableRow();
            _table.put(stateNum, row);
        }
        return row;
    }

    void setReduceAndAcceptActions(final Set<LRItemSet> itemSets) {
        for (final LRItemSet itemSet : itemSets) {
            List<LRRuleItem> fullyDefinedRules = itemSet.getFullyDefinedRules();
            if (!fullyDefinedRules.isEmpty()) {
                LRTableRow row = _table.get(itemSet.getItemNumber());
                row.fillReduceAction(_inputSymbols, fullyDefinedRules);
            }
        }
    }

    public List<Integer> getStates() {
        return Collections.unmodifiableList(new LinkedList<>(_table.keySet()));
    }

    public LRTableRow getRowForState(final Integer state) {
        return _table.get(state);
    }

    @Override
    public String toString() {
        String result = "t(is(";
        result += _inputSymbols;
        result += ")d(";
        result += _table;
        result += ")";
        return result;
    }

}
