// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;
import ru.atc.parser.notation.Symbol;

abstract class AbstractState implements IState {

    AbstractState() {
        super();
    }

    @Override
    public abstract IState processSymbol(char symbol, IStack stack, int row, int column) throws NotationParseException;

    final boolean isWhitespaceSymbol(final char symbol) {
        return Symbol.isWhitespaceSymbol(symbol);
    }

    final boolean isNameFirstSymbol(final char symbol) {
        return Symbol.isNameFirstSymbol(symbol);
    }

    final boolean isNameSymbol(final char symbol) {
        return Symbol.isNameSymbol(symbol);
    }

    final boolean isRuleNameAndValuesSeparator(final char symbol) {
        return Symbol.isRuleNameAndValuesSeparator(symbol);
    }

    final boolean isLiteralBound(final char symbol) {
        return Symbol.isLiteralBound(symbol);
    }

    final boolean isLiteralEscape(final char symbol) {
        return Symbol.isLiteralEscape(symbol);
    }

    final boolean isLiteralCorrect(final char symbol) {
        return Symbol.isLiteralCorrect(symbol);
    }

    final boolean isRuleValuesSeparator(final char symbol) {
        return Symbol.isRuleValuesSeparator(symbol);
    }

    final boolean isRulesSeparator(final char symbol) {
        return Symbol.isRulesSeparator(symbol);
    }

    final boolean isCommentBound(final char symbol) {
        return Symbol.isCommentBound(symbol);
    }

    final boolean isCommentCorrect(final char symbol) {
        return Symbol.isCommentCorrect(symbol);
    }

    final boolean isNewLine(final char symbol) {
        return Symbol.isNewLine(symbol);
    }

}
