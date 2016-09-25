// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.element;

abstract class AbstractNotationElement implements INotationElement {

    private final int _elementRow;

    private final int _elementColumn;

    AbstractNotationElement(final int elementRow, final int elementColumn) {
        super();
        _elementRow = elementRow;
        _elementColumn = elementColumn;
    }

    @Override
    public final int getRow() {
        return _elementRow;
    }

    @Override
    public final int getColumn() {
        return _elementColumn;
    }

}
