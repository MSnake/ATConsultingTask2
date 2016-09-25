// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation;

public final class Symbol {

    private static final int MIN_LATIN = 65;

    private static final int MAX_LATIN = 90;

    private Symbol() {
        super();
    }

    public static boolean isWhitespaceSymbol(final char symbol) {
        return Character.isWhitespace(symbol) || Character.isSpaceChar(symbol);
    }

    public static boolean isNameFirstSymbol(final char symbol) {
        return Character.isUpperCase(symbol) && symbol >= MIN_LATIN && symbol <= MAX_LATIN;
    }

    public static boolean isNameSymbol(final char symbol) {
        return isNameFirstSymbol(symbol) || Character.isDigit(symbol) || symbol == '$' || symbol == '_';
    }

    public static boolean isRuleNameAndValuesSeparator(final char symbol) {
        return symbol == ':';
    }

    public static boolean isLiteralBound(final char symbol) {
        return symbol == '\'';
    }

    public static boolean isLiteralEscape(final char symbol) {
        return symbol == '\\';
    }

    public static boolean isLiteralCorrect(final char symbol) {
        return symbol > 0;
    }

    public static boolean isRuleValuesSeparator(final char symbol) {
        return symbol == '|';
    }

    public static boolean isRulesSeparator(final char symbol) {
        return symbol == ';';
    }

    public static boolean isCommentBound(final char symbol) {
        return symbol == '/';
    }

    public static boolean isCommentCorrect(final char symbol) {
        return symbol > 0;
    }

    public static boolean isNewLine(final char symbol) {
        return symbol == '\n';
    }

}
