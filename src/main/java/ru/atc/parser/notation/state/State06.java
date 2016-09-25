// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import ru.atc.parser.notation.NotationParseException;

final class State06 extends AbstractState {

    State06() {
        super();
    }

    @Override
    public IState processSymbol(final char symbol, final IStack stack, final int row, final int column) throws NotationParseException {
        if (isWhitespaceSymbol(symbol)) {
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State06.class);
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
        if (isLiteralBound(symbol)) {
            stack.pushRuleValuePart();
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State04.class);
        }
        if (isNameFirstSymbol(symbol)) {
            stack.pushRuleValuePart();
            stack.putSymbol(symbol, row, column);
            return StateFactory.getState(State07.class);
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
