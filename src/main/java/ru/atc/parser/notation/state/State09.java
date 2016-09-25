// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State09 extends AbstractState {

    State09() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isCommentBound(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State10.class);
        }
        throw new NotationParseException(symbol, row, column);
    }

}
