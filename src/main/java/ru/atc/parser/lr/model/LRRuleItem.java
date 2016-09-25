// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.model;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class LRRuleItem implements Comparable<LRRuleItem> {

    private final int _ruleNumber;

    private final String _ruleName;

    private final LRRuleValueItem[] _ruleValueItems;

    public LRRuleItem(final int ruleNumber, final String ruleName, final LRRuleValueItem... values) {
        super();
        if (ruleNumber < 0) {
            throw new IllegalArgumentException("Rule number is 0 or greater");
        }
        if (StringUtils.isBlank(ruleName)) {
            throw new NullArgumentException("ruleName");
        }
        if (ArrayUtils.isEmpty(values)) {
            throw new NullArgumentException("values");
        }
        _ruleNumber = ruleNumber;
        _ruleName = ruleName;
        _ruleValueItems = (LRRuleValueItem[]) ArrayUtils.clone(values);
    }

    public int getRuleNumber() {
        return _ruleNumber;
    }

    public String getRuleName() {
        return _ruleName;
    }

    public int getValueCount() {
        return _ruleValueItems.length;
    }

    public LRRuleValueItem getValueAt(final int position) {
        return _ruleValueItems[position];
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LRRuleItem)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        LRRuleItem rhs = (LRRuleItem) obj;
        return new EqualsBuilder().append(_ruleNumber, rhs._ruleNumber).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(_ruleNumber).toHashCode();
    }

    @Override
    public int compareTo(final LRRuleItem rule) {
        if (_ruleNumber > rule._ruleNumber) {
            return 1;
        }
        if (_ruleNumber < rule._ruleNumber) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        String result = "r(" + _ruleName + ";";
        for (LRRuleValueItem ruleValueItem : _ruleValueItems) {
            result += ruleValueItem.toString();
        }
        result += ")";
        return result;
    }

}
