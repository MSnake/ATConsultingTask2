// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.element;

public interface INotationElement {

    String getImage();

    int getChildCount();

    INotationElement getChildAt(int position);

    int getRow();

    int getColumn();

}
