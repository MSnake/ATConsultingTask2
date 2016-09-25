// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation;

public final class NotationParseException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotationParseException(final char symbol, final int row, final int column) {
        super("Wrong character '" + symbol + "' at " + row + ":" + column);
    }

    public NotationParseException(final String message) {
        super(message);
    }

}
