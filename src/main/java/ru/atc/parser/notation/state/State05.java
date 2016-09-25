// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State05 extends AbstractState {

    State05() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isLiteralEscape(symbol)) {
            stack.putSymbol(symbol, row, column);
            stack.pushLiteralEscape();
            return StateFactory.getState(State04.class);
        }
        if (isLiteralBound(symbol)) {
            stack.putSymbol(symbol, row, column);
            stack.pushLiteralEscape();
            return StateFactory.getState(State04.class);
        }
        throw new NotationParseException(symbol, row, column);
    }

}
