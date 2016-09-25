// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State15 extends AbstractState {

    State15() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isNewLine(symbol)) {
            stack.putSymbol(symbol, row, column);
            stack.pushComment();
            return StateFactory.getState(State03.class);
        }
        if (isCommentCorrect(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State15.class);
        }
        throw new NotationParseException(symbol, row, column);
    }

}
