// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.table;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.atc.parser.lr.model.LRRuleItem;
import ru.atc.parser.lr.model.LRModelBuilder;
import ru.atc.parser.lr.model.LRRuleList;
import ru.atc.parser.lr.model.LRRuleValueItem;
import ru.atc.parser.notation.element.RuleSet;

public final class LRTableBuilder {

    private final LRRuleList _allRules;

    private final Set<LRItemSet> _allRuleSets;

    private final LRTable _resultTable;

    private int _ruleSetNumber;

    public LRTableBuilder(final RuleSet rules) {
        _allRules = LRModelBuilder.createLRModel(rules);
        _allRuleSets = new HashSet<>();
        _resultTable = new LRTable();
        _ruleSetNumber = 0;
        createRuleSets();
    }

    private void createRuleSets() {
        LRItemSet firstSet = createFirstRuleSet();
        _allRuleSets.add(firstSet);
        createNextRuleSets(firstSet);
        _resultTable.addInputSymbol(new LRInputSymbol("$", false, true));
        _resultTable.setReduceAndAcceptActions(_allRuleSets);
    }

    private LRItemSet createFirstRuleSet() {
        Map<LRRuleItem, Integer> positions = new HashMap<>();
        positions.put(_allRules.getValueAt(0), 0);
        LRRuleItem[] ruleItems = new LRRuleItem[]{_allRules.getValueAt(0)};
        return new LRItemSet(getRuleSetNumber(), ruleItems, positions, _allRules);
    }

    private int getRuleSetNumber() {
        int number = _ruleSetNumber;
        _ruleSetNumber++;
        return number;
    }

    private void createNextRuleSets(final LRItemSet ruleSet) {
        List<LRRuleValueItem> nextSymbols = ruleSet.getNextUniqueSymbols();
        if (nextSymbols.isEmpty()) {
            _resultTable.addEmptyRowForState(ruleSet.getItemNumber());
            return;
        }
        for (LRRuleValueItem symbol : nextSymbols) {
            _resultTable.addInputSymbol(createInputSymbol(symbol));
            LRItemSet secondSet = new LRItemSet(getRuleSetNumber(), ruleSet.getRulesForSymbol(symbol), ruleSet.getPositionsForSymbol(symbol), _allRules);
            if (_allRuleSets.contains(secondSet)) {
                _resultTable.addInputSymbolForState(ruleSet.getItemNumber(), createInputSymbol(symbol), getStoredItemSetNumber(secondSet));
            } else {
                _allRuleSets.add(secondSet);
                _resultTable.addInputSymbolForState(ruleSet.getItemNumber(), createInputSymbol(symbol), secondSet.getItemNumber());
                createNextRuleSets(secondSet);
            }
        }
    }

    private LRInputSymbol createInputSymbol(final LRRuleValueItem value) {
        return new LRInputSymbol(value.getValue(), value.isTerminal(), false);
    }

    private int getStoredItemSetNumber(final LRItemSet itemSet) {
        for (LRItemSet set : _allRuleSets) {
            if (set.equals(itemSet)) {
                return set.getItemNumber();
            }
        }
        throw new IllegalArgumentException("There is not matching item set");
    }

    public LRRuleList getLRModelRules() {
        return _allRules;
    }

    public List<LRItemSet> getLRModelItemSets() {
        int maxNumber = 0;
        for (LRItemSet set : _allRuleSets) {
            if (maxNumber < set.getItemNumber()) {
                maxNumber = set.getItemNumber();
            }
        }
        maxNumber++;
        List<LRItemSet> result = new LinkedList<>();
        for (int i = 0; i < maxNumber; i++) {
            for (LRItemSet set : _allRuleSets) {
                if (set.getItemNumber() == i) {
                    result.add(set);
                }
            }
        }
        return result;
    }

    public LRTable getItemSetTable() {
        return _resultTable;
    }

}
