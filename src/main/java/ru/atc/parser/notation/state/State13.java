// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State13 extends AbstractState {

    State13() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isWhitespaceSymbol(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State13.class);
        }
        if (isRuleValuesSeparator(symbol)) {
            stack.ignoreSymbol(symbol, row, column);
            return StateFactory.getState(State03.class);
        }
        if (isRulesSeparator(symbol)) {
            stack.ignoreSymbol(symbol, row, column);
            stack.pushRule();
            return StateFactory.getState(State00.class);
        }
        if (isCommentBound(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State11.class);
        }
        throw new NotationParseException(symbol, row, column);
    }

}
