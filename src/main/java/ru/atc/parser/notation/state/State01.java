// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State01 extends AbstractState {

    State01() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isNameSymbol(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State01.class);
        }
        if (isWhitespaceSymbol(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State02.class);
        }
        if (isRuleNameAndValuesSeparator(symbol)) {
            stack.ignoreSymbol(symbol, row, column);
            stack.pushRuleName();
            return StateFactory.getState(State03.class);
        }
        throw new NotationParseException(symbol, row, column);
    }

}
