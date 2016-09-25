// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;

public final class LRRuleList {

    private final LRRuleItem[] _ruleItems;

    public LRRuleList(final LRRuleItem... ruleList) {
        super();
        if (ArrayUtils.isEmpty(ruleList)) {
            throw new NullArgumentException("ruleList");
        }
        _ruleItems = (LRRuleItem[]) ArrayUtils.clone(ruleList);
    }

    public int getValueCount() {
        return _ruleItems.length;
    }

    public LRRuleItem getValueAt(final int position) {
        return _ruleItems[position];
    }

    public Set<LRRuleItem> getRulesForName(final String ruleName) {
        Set<LRRuleItem> rules = new HashSet<>();
        for (LRRuleItem ruleItem : _ruleItems) {
            if (ruleItem.getRuleName().equals(ruleName)) {
                rules.add(ruleItem);
            }
        }
        return Collections.unmodifiableSet(rules);
    }

    @Override
    public String toString() {
        String result = "l(";
        for (LRRuleItem ruleItem : _ruleItems) {
            result += ruleItem.toString();
        }
        result += ")";
        return result;
    }

}
