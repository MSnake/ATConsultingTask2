// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.model;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class LRRuleValueItem implements Comparable<LRRuleValueItem> {

    private final String _value;

    private final boolean _isTerminal;

    public LRRuleValueItem(final String value, final boolean isTerminal) {
        super();
        if (StringUtils.isBlank(value)) {
            throw new NullArgumentException("value");
        }
        _value = value;
        _isTerminal = isTerminal;
    }

    public String getValue() {
        return _value;
    }

    public boolean isTerminal() {
        return _isTerminal;
    }

    public boolean isMatchesInputSymbol(final String value, final boolean isTerminal) {
        return _value.equals(value) && _isTerminal == isTerminal;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LRRuleValueItem)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        LRRuleValueItem rhs = (LRRuleValueItem) obj;
        return new EqualsBuilder().append(_value, rhs._value).append(_isTerminal, rhs._isTerminal).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(_value).append(_isTerminal).toHashCode();
    }

    @Override
    public int compareTo(final LRRuleValueItem obj) {
        if (_isTerminal && !obj._isTerminal) {
            return -1;
        }
        if (!_isTerminal && obj._isTerminal) {
            return 1;
        }
        return _value.compareTo(obj._value);
    }

    @Override
    public String toString() {
        if (_isTerminal) {
            return "v('" + _value + "')";
        } else {
            return "v(" + _value + ")";
        }
    }

}
