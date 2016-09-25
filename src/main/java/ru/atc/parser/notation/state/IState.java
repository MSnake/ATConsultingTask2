// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

public interface IState {

    IState processSymbol(char symbol, IStack stack, int row, int column) throws NotationParseException;

}
