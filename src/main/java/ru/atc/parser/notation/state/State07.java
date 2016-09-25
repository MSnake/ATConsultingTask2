// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State07 extends AbstractState {

    State07() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isNameSymbol(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State07.class);
        }
        if (isWhitespaceSymbol(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State08.class);
        }
        if (isRuleValuesSeparator(symbol)) {
            stack.ignoreSymbol(symbol, row, column);
            stack.pushRuleValuePart();
            stack.pushRuleValue();
            return StateFactory.getState(State03.class);
        }
        if (isRulesSeparator(symbol)) {
            stack.ignoreSymbol(symbol, row, column);
            stack.pushRuleValuePart();
            stack.pushRuleValue();
            stack.pushRule();
            return StateFactory.getState(State00.class);
        }
        if (isCommentBound(symbol)) {
            stack.pushRuleValuePart();
            stack.pushRuleValue();
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State11.class);
        }
        throw new NotationParseException(symbol, row, column);
    }

}
