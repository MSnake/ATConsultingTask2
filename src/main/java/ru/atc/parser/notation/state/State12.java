// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State12 extends AbstractState {

    State12() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isNewLine(symbol)) {
            stack.putSymbol(symbol, row, column);
            stack.pushComment();
            return StateFactory.getState(State13.class);
        }
        if (isCommentCorrect(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State12.class);
        }
        throw new NotationParseException(symbol, row, column);
    }

}
