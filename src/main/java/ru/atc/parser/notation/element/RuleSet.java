// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.element;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;

public final class RuleSet extends AbstractNotationElement {

    private final INotationElement[] _elements;

    public RuleSet(final INotationElement... elements) {
        super(1, 1);
        if (ArrayUtils.isEmpty(elements)) {
            throw new NullArgumentException("rules");
        }
        for (INotationElement element : elements) {
            if (!(element instanceof Rule) && !(element instanceof Comment)) {
                throw new IllegalArgumentException("Wrong element in rule set");
            }
        }
        _elements = (INotationElement[]) ArrayUtils.clone(elements);
    }

    @Override
    public String getImage() {
        return "";
    }

    @Override
    public int getChildCount() {
        return _elements.length;
    }

    @Override
    public INotationElement getChildAt(final int position) {
        return _elements[position];
    }

    @Override
    public String toString() {
        String result = "s(";
        for (INotationElement element : _elements) {
            result += element.toString();
        }
        result += ")";
        return result;
    }

}
