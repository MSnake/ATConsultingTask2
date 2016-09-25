// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.element;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class RuleValuePart extends AbstractNotationElement {

    private final String _valuePart;

    private final boolean _isTerminal;

    public RuleValuePart(final String valuePart, final int elementRow, final int elementColumn) {
        super(elementRow, elementColumn);
        if (StringUtils.isBlank(valuePart)) {
            throw new NullArgumentException("valuePart");
        }
        String trimmedValuePart = valuePart.trim();
        int lastSymbol = trimmedValuePart.length() - 1;
        if (trimmedValuePart.charAt(0) == '\'' && trimmedValuePart.charAt(lastSymbol) == '\'') {
            _valuePart = trimmedValuePart.substring(1, lastSymbol);
            _isTerminal = true;
        } else if (trimmedValuePart.charAt(0) == '\'' || trimmedValuePart.charAt(lastSymbol) == '\'') {
            throw new IllegalArgumentException("valuePart is wrong: " + trimmedValuePart);
        } else {
            _valuePart = trimmedValuePart;
            _isTerminal = false;
        }
    }

    @Override
    public String getImage() {
        return _valuePart;
    }

    public boolean isTerminal() {
        return _isTerminal;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public INotationElement getChildAt(final int position) {
        return null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof RuleValuePart)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        RuleValuePart rhs = (RuleValuePart) obj;
        return new EqualsBuilder().append(_valuePart, rhs._valuePart).append(_isTerminal, rhs._isTerminal).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(_valuePart).append(_isTerminal).toHashCode();
    }

    @Override
    public String toString() {
        if (_isTerminal) {
            return "p('" + _valuePart + "')";
        } else {
            return "p(" + _valuePart + ")";
        }
    }

}
