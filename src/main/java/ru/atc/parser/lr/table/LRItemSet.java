// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import ru.atc.parser.lr.model.LRRuleItem;
import ru.atc.parser.lr.model.LRRuleList;
import ru.atc.parser.lr.model.LRRuleValueItem;

public final class LRItemSet {

    private final int _itemNumber;

    private final Set<LRRuleItem> _kernelRules;

    private final Set<LRRuleItem> _rules;

    private final Map<LRRuleItem, Integer> _positions;

    LRItemSet(final int itemNumber, final LRRuleItem[] kernelRules, final Map<LRRuleItem, Integer> positions, final LRRuleList allRules) {
        if (itemNumber < 0) {
            throw new IllegalArgumentException("Set number must be 0 or greater");
        }
        _itemNumber = itemNumber;
        if (ArrayUtils.isEmpty(kernelRules)) {
            throw new NullArgumentException("kernelRules");
        }
        _kernelRules = getKernelRules(kernelRules);
        if (positions == null) {
            throw new NullArgumentException("position");
        }
        for (LRRuleItem rule : _kernelRules) {
            if (!positions.containsKey(rule)) {
                throw new IllegalArgumentException("There is no position for a rule");
            }
        }
        if (allRules == null || allRules.getValueCount() <= 0) {
            throw new NullArgumentException("allRules");
        }
        _rules = getAllRules(positions, allRules);
        _positions = getPositionMap(positions);
    }

    private Set<LRRuleItem> getKernelRules(final LRRuleItem[] kernelRules) {
        Set<LRRuleItem> tempKernelRules = new HashSet<>();
        tempKernelRules.addAll(Arrays.asList(kernelRules));
        return Collections.unmodifiableSet(tempKernelRules);
    }

    public Set<LRRuleItem> getKernelRules() {
        return _kernelRules;
    }

    private Set<LRRuleItem> getAllRules(final Map<LRRuleItem, Integer> positions, final LRRuleList allRules) {
        Set<LRRuleItem> rules = new HashSet<>(_kernelRules);
        for (LRRuleItem rule : _kernelRules) {
            int pos = positions.get(rule);
            if (pos < rule.getValueCount()) {
                LRRuleValueItem value = rule.getValueAt(pos);
                if (!value.isTerminal()) {
                    rules.addAll(getClosure(value.getValue(), allRules));
                }
            }
        }
        return Collections.unmodifiableSet(rules);
    }

    private Set<LRRuleItem> getClosure(final String ruleName, final LRRuleList allRules) {
        Set<LRRuleItem> paths = new HashSet<>();
        paths.addAll(allRules.getRulesForName(ruleName));
        do {
            Set<LRRuleItem> newPaths = new HashSet<>(paths);
            for (LRRuleItem rule : paths) {
                for (int i = 0; i < rule.getValueCount(); i++) {
                    LRRuleValueItem value = rule.getValueAt(i);
                    if (value.isTerminal()) {
                        continue;
                    }
                    newPaths.addAll(allRules.getRulesForName(value.getValue()));
                }
            }
            if (paths.containsAll(newPaths) && newPaths.containsAll(paths)) {
                break;
            }
            paths.addAll(newPaths);
        } while (true);
        return paths;
    }

    private Map<LRRuleItem, Integer> getPositionMap(final Map<LRRuleItem, Integer> defaultPositions) {
        Map<LRRuleItem, Integer> positions = new HashMap<>();
        for (LRRuleItem rule : _kernelRules) {
            positions.put(rule, defaultPositions.get(rule));
        }
        for (LRRuleItem rule : _rules) {
            if (!positions.containsKey(rule)) {
                positions.put(rule, 0);
            }
        }
        return Collections.unmodifiableMap(positions);
    }

    public int getItemNumber() {
        return _itemNumber;
    }

    public int getPosition(final LRRuleItem rule) {
        return _positions.get(rule);
    }

    public Set<LRRuleItem> getRules() {
        return _rules;
    }

    public List<LRRuleValueItem> getNextUniqueSymbols() {
        List<LRRuleValueItem> nextSymbols = new LinkedList<>();
        for (LRRuleItem rule : new TreeSet<>(_rules)) {
            int position = _positions.get(rule);
            if (position < rule.getValueCount()) {
                LRRuleValueItem value = rule.getValueAt(position);
                if (!nextSymbols.contains(value)) {
                    nextSymbols.add(value);
                }
            }
        }
        return nextSymbols;
    }

    LRRuleItem[] getRulesForSymbol(final LRRuleValueItem symbol) {
        List<LRRuleItem> rules = new LinkedList<>();
        for (LRRuleItem rule : new TreeSet<>(_rules)) {
            int postion = _positions.get(rule);
            if (postion < rule.getValueCount()) {
                LRRuleValueItem value = rule.getValueAt(postion);
                if (value.equals(symbol)) {
                    rules.add(rule);
                }
            }
        }
        return rules.toArray(new LRRuleItem[rules.size()]);
    }

    Map<LRRuleItem, Integer> getPositionsForSymbol(final LRRuleValueItem symbol) {
        Map<LRRuleItem, Integer> positions = new HashMap<>();
        for (LRRuleItem rule : _rules) {
            int postion = _positions.get(rule);
            if (postion < rule.getValueCount()) {
                LRRuleValueItem value = rule.getValueAt(postion);
                if (value.equals(symbol)) {
                    positions.put(rule, _positions.get(rule) + 1);
                }
            }
        }
        return positions;
    }

    public List<LRRuleItem> getFullyDefinedRules() {
        List<LRRuleItem> result = new ArrayList<>();
        for (LRRuleItem rule : _kernelRules) {
            int ruleValuesSize = rule.getValueCount();
            int currentPosition = _positions.get(rule);
            if (ruleValuesSize == currentPosition) {
                result.add(rule);
            }
        }
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LRItemSet)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        LRItemSet rhs = (LRItemSet) obj;
        return new EqualsBuilder().append(_rules, rhs._rules).append(_positions, rhs._positions).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(_rules).append(_positions).toHashCode();
    }

    @Override
    public String toString() {
        String result = "l(";
        result += _rules;
        result += ")";
        return result;
    }

}
