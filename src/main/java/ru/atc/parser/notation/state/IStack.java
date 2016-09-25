// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

public interface IStack {

    void putSymbol(char symbol, int row, int column) throws NotationParseException;

    void ignoreSymbol(char symbol, int row, int column) throws NotationParseException;

    void pushRuleName() throws NotationParseException;

    void pushRuleValuePart() throws NotationParseException;

    void pushRuleValue() throws NotationParseException;

    void pushLiteralEscape() throws NotationParseException;

    void pushRule() throws NotationParseException;

    void pushComment() throws NotationParseException;

}
