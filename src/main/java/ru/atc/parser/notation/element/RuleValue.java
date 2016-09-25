// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.element;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class RuleValue extends AbstractNotationElement {

    private final INotationElement[] _valueParts;

    public RuleValue(final int elementRow, final int elementColumn, final INotationElement... elements) {
        super(elementRow, elementColumn);
        if (ArrayUtils.isEmpty(elements)) {
            throw new NullArgumentException("valueParts");
        }
        for (INotationElement element : elements) {
            if (!(element instanceof RuleValuePart)) {
                throw new IllegalArgumentException("Wrong element in rule");
            }
        }
        _valueParts = (INotationElement[]) ArrayUtils.clone(elements);
    }

    @Override
    public String getImage() {
        return "";
    }

    @Override
    public int getChildCount() {
        return _valueParts.length;
    }

    @Override
    public INotationElement getChildAt(final int position) {
        return _valueParts[position];
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof RuleValue)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        RuleValue rhs = (RuleValue) obj;
        return new EqualsBuilder().append(_valueParts, rhs._valueParts).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(_valueParts).toHashCode();
    }

    @Override
    public String toString() {
        String result = "v(";
        for (INotationElement valuePart : _valueParts) {
            result += valuePart.toString();
        }
        result += ")";
        return result;
    }

}
