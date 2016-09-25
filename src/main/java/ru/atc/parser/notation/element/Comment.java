// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.element;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

public final class Comment extends AbstractNotationElement {

    private final String _text;

    public Comment(final String text, final int elementRow, final int elementColumn) {
        super(elementRow, elementColumn);
        if (StringUtils.isBlank(text)) {
            throw new NullArgumentException("text");
        }
        _text = text;
    }

    @Override
    public String getImage() {
        return _text;
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
    public String toString() {
        return "c(" + _text + ")";
    }

}
