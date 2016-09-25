// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.NullArgumentException;

import ru.atc.parser.lr.model.LRRuleItem;
import ru.atc.parser.lr.model.LRRuleValueItem;
import ru.atc.parser.notation.ReservedRuleNames;

public final class LRTableRow {

    private final Map<LRInputSymbol, LRTableItem> _row;

    LRTableRow() {
        _row = new HashMap<>();
    }

    void addItemForInput(final LRInputSymbol symbol, final LRTableItem item) {
        if (symbol == null) {
            throw new NullArgumentException("symbol");
        }
        if (item == null) {
            throw new NullArgumentException("item");
        }
        if (_row.containsKey(symbol)) {
            throw new IllegalArgumentException("Row already contains input for symbol " + symbol);
        }
        _row.put(symbol, item);
    }

    private boolean hasItemForInput(final LRInputSymbol symbol) {
        return symbol != null && _row.containsKey(symbol);
    }

    public LRTableItem getItemForInput(final LRInputSymbol symbol) {
        if (symbol == null) {
            throw new NullArgumentException("symbol");
        }
        return _row.get(symbol);
    }

    void fillReduceAction(final Set<LRInputSymbol> inputSymbols, final List<LRRuleItem> fullyDefinedRules) {
        LRRuleItem fullyDefinedRule = getUnreferencedFullyDefinedRule(fullyDefinedRules);
        for (LRInputSymbol symbol : inputSymbols) {
            if (fullyDefinedRule.getRuleName().equals(ReservedRuleNames.MAIN_RULE_NAME) && !symbol.isEndOfInput()) {
                continue;
            }
            if (!symbol.isTerminal() && !symbol.isEndOfInput()) {
                continue;
            }
            if (!hasItemForInput(symbol)) {
                if (fullyDefinedRule.getRuleName().equals(ReservedRuleNames.MAIN_RULE_NAME)) {
                    LRTableItem item = new LRTableItem();
                    item.setNumber(fullyDefinedRule.getRuleNumber());
                    item.setAcceptAction();
                    addItemForInput(symbol, item);
                } else {
                    LRTableItem item = new LRTableItem();
                    item.setNumber(fullyDefinedRule.getRuleNumber());
                    item.setReduceAction();
                    item.setReduceRule(fullyDefinedRule);
                    addItemForInput(symbol, item);
                }
            }
        }
    }

    private LRRuleItem getUnreferencedFullyDefinedRule(final List<LRRuleItem> fullyDefinedRules) {
        if (fullyDefinedRules == null || fullyDefinedRules.isEmpty()) {
            return null;
        }
        if (fullyDefinedRules.size() == 1) {
            return fullyDefinedRules.get(0);
        }
        for (LRRuleItem ruleItem : fullyDefinedRules) {
            if (!hasReferencedRules(fullyDefinedRules, ruleItem)) {
                return ruleItem;
            }
        }
        return null;
    }

    private boolean hasReferencedRules(final List<LRRuleItem> fullyDefinedRules, final LRRuleItem testRuleItem) {
        String testRuleItemName = testRuleItem.getRuleName();
        for (LRRuleItem ruleItem : fullyDefinedRules) {
            if (ruleItem.equals(testRuleItem)) {
                continue;
            }
            for (int i = 0; i < ruleItem.getValueCount(); i++) {
                LRRuleValueItem ruleValueItem = ruleItem.getValueAt(i);
                if (ruleValueItem.isTerminal()) {
                    continue;
                }
                if (ruleValueItem.getValue().equals(testRuleItemName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String result = "tr(";
        result += _row;
        result += ")";
        return result;
    }

}
