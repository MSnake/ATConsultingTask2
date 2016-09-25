// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.element;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

public final class Rule extends AbstractNotationElement {

    private final String _name;

    private final INotationElement[] _values;

    public Rule(final String name, final int elementRow, final int elementColumn, final INotationElement... elements) {
        super(elementRow, elementColumn);
        if (StringUtils.isBlank(name)) {
            throw new NullArgumentException("name");
        }
        if (ArrayUtils.isEmpty(elements)) {
            throw new NullArgumentException("values");
        }
        for (INotationElement element : elements) {
            if (!(element instanceof RuleValue) && !(element instanceof Comment)) {
                throw new IllegalArgumentException("Wrong element in rule");
            }
        }
        _name = name.trim();
        _values = (INotationElement[]) ArrayUtils.clone(elements);
    }

    @Override
    public String getImage() {
        return _name;
    }

    @Override
    public int getChildCount() {
        return _values.length;
    }

    @Override
    public INotationElement getChildAt(final int position) {
        return _values[position];
    }

    @Override
    public String toString() {
        String result = "r(" + _name + ";";
        for (INotationElement value : _values) {
            result += value.toString();
        }
        result += ")";
        return result;
    }

}
