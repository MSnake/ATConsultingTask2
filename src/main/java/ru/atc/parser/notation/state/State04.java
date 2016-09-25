// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State04 extends AbstractState {

    State04() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isLiteralEscape(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State05.class);
        }
        if (isLiteralBound(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State06.class);
        }
        if (isLiteralCorrect(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State04.class);
        }
        throw new NotationParseException(symbol, row, column);
    }

}
