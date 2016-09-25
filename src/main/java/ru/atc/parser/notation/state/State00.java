// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State00 extends AbstractState {

    State00() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isWhitespaceSymbol(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State00.class);
        }
        if (isNameFirstSymbol(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State01.class);
        }
        if (isCommentBound(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State09.class);
        }
        throw new NotationParseException(symbol, row, column);
    }

}
