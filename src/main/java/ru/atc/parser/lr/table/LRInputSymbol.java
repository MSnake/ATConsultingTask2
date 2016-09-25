// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.table;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class LRInputSymbol implements Comparable<LRInputSymbol> {

    private static final int TERMINAL = 1;

    private static final int END_OF_INPUT = 2;

    private static final int RULE = 3;

    private final String _symbol;

    private final boolean _isTerminal;

    private final boolean _isEndOfInput;

    public LRInputSymbol(final String symbol, final boolean isTerminal, final boolean isEndOfInput) {
        if (StringUtils.isBlank(symbol)) {
            throw new NullArgumentException("symbol");
        }
        if (isTerminal && isEndOfInput) {
            throw new IllegalArgumentException("Can not be both terminal symbol and end o input");
        }
        _symbol = symbol;
        _isTerminal = isTerminal;
        _isEndOfInput = isEndOfInput;
    }

    public String getSymbol() {
        return _symbol;
    }

    public boolean isTerminal() {
        return _isTerminal;
    }

    public boolean isEndOfInput() {
        return _isEndOfInput;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LRInputSymbol)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        LRInputSymbol rhs = (LRInputSymbol) obj;
        return new EqualsBuilder().append(_symbol, rhs._symbol).append(_isTerminal, rhs._isTerminal).append(_isEndOfInput, rhs._isEndOfInput).isEquals();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(17,37,this);
    }

    @Override
    public int compareTo(final LRInputSymbol symbol) {
        int thisType = getType();
        int otherType = symbol.getType();
        if (thisType == TERMINAL && otherType == TERMINAL || thisType == RULE && otherType == RULE) {
            return _symbol.compareTo(symbol._symbol);
        }
        if (thisType < otherType) {
            return -1;
        }
        if (thisType > otherType) {
            return 1;
        }
        return 0;
    }

    private int getType() {
        if (_isTerminal) {
            return TERMINAL;
        }
        if (_isEndOfInput) {
            return END_OF_INPUT;
        }
        return RULE;
    }

    @Override
    public String toString() {
        String result = "is(";
        if (_isEndOfInput) {
            result += "$";
        } else {
            if (_isTerminal) {
                result += "'" + _symbol + "'";
            } else {
                result += _symbol;
            }
        }
        result += ")";
        return result;
    }

}
