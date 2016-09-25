// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ru.atc.parser.notation.element.INotationElement;
import ru.atc.parser.notation.element.Rule;
import ru.atc.parser.notation.element.RuleValue;
import ru.atc.parser.notation.element.RuleValuePart;
import ru.atc.parser.notation.state.IStack;
import ru.atc.parser.notation.element.Comment;
import ru.atc.parser.notation.element.RuleSet;

final class StackImpl implements IStack {

    private static final int BUFFER_LENGTH = 255;

    private final char[] _buffer;

    private int _top;

    private String _processedRuleName;

    private int _processedRuleRow;

    private int _processedRuleColumn;

    private final List<INotationElement> _processedRuleValueParts;

    private int _processedRuleValueRow;

    private int _processedRuleValueColumn;

    private final List<INotationElement> _processedRuleValues;

    private final List<INotationElement> _processedRules;

    private boolean _isRuleComment;

    private boolean _isRuleValueComment;

    private int _elementRow;

    private int _elementColumn;

    private int _spacedElementRow;

    private int _spacedElementColumn;

    StackImpl() {
        super();
        _buffer = new char[BUFFER_LENGTH];
        _top = 0;
        _processedRuleName = "";
        _processedRuleRow = -1;
        _processedRuleColumn = 1;
        _processedRuleValueParts = new LinkedList<>();
        _processedRuleValueRow = -1;
        _processedRuleValueColumn = -1;
        _processedRuleValues = new LinkedList<>();
        _processedRules = new LinkedList<>();
        _isRuleComment = true;
        _isRuleValueComment = false;
        _elementRow = -1;
        _elementColumn = -1;
        _spacedElementRow = -1;
        _spacedElementColumn = -1;
    }

    @Override
    public void putSymbol(final char symbol, final int row, final int column) throws NotationParseException {
        _buffer[_top] = symbol;
        _top++;
        if (!Symbol.isWhitespaceSymbol(symbol) && _elementRow == -1 && _elementColumn == -1) {
            _elementRow = row;
            _elementColumn = column;
        }
        if (_spacedElementRow == -1 && _spacedElementColumn == -1) {
            _spacedElementRow = row;
            _spacedElementColumn = column;
        }
    }

    @Override
    public void ignoreSymbol(final char symbol, final int row, final int column) throws NotationParseException {
        if (_spacedElementRow == -1 && _spacedElementColumn == -1) {
            _spacedElementRow = row;
            _spacedElementColumn = column;
        }
    }

    @Override
    public void pushRuleName() throws NotationParseException {
        _processedRuleName = getTrimmedValue();
        _processedRuleRow = _elementRow;
        _processedRuleColumn = _elementColumn;
        _top = 0;
        _isRuleComment = false;
        _isRuleValueComment = true;
        _elementRow = -1;
        _elementColumn = -1;
        _spacedElementRow = -1;
        _spacedElementColumn = -1;
    }

    @Override
    public void pushRuleValuePart() throws NotationParseException {
        if (_elementRow == -1 && _elementColumn == -1) {
            _elementRow = _spacedElementRow;
            _elementColumn = _spacedElementColumn;
        }
        RuleValuePart part = new RuleValuePart(getTrimmedValue(), _elementRow, _elementColumn);
        _processedRuleValueParts.add(part);
        if (_processedRuleValueRow == -1 && _processedRuleValueColumn == -1) {
            _processedRuleValueRow = _elementRow;
            _processedRuleValueColumn = _elementColumn;
        }
        _top = 0;
        _isRuleComment = false;
        _isRuleValueComment = false;
        _elementRow = -1;
        _elementColumn = -1;
        _spacedElementRow = -1;
        _spacedElementColumn = -1;
    }

    @Override
    public void pushRuleValue() throws NotationParseException {
        String ruleValueStr = getTrimmedValue();
        if (StringUtils.isNotEmpty(ruleValueStr)) {
            throw new NotationParseException("Wrong rule value: " + ruleValueStr);
        }
        RuleValue ruleValue = new RuleValue(_processedRuleValueRow, _processedRuleValueColumn, _processedRuleValueParts.toArray(new INotationElement[_processedRuleValueParts.size()]));
        _processedRuleValueParts.clear();
        _processedRuleValues.add(ruleValue);
        _top = 0;
        _isRuleComment = false;
        _isRuleValueComment = true;
        _elementRow = -1;
        _elementColumn = -1;
        _spacedElementRow = -1;
        _spacedElementColumn = -1;
        _processedRuleValueRow = -1;
        _processedRuleValueColumn = -1;
    }

    @Override
    public void pushLiteralEscape() throws NotationParseException {
        char cl1 = _buffer[_top - 1];
        _top -= 2;
        putSymbol(cl1, _elementRow, _elementColumn);
    }

    @Override
    public void pushRule() throws NotationParseException {
        String ruleStr = getTrimmedValue();
        if (StringUtils.isNotEmpty(ruleStr)) {
            throw new NotationParseException("Wrong rule: " + ruleStr);
        }
        Rule rule = new Rule(_processedRuleName, _processedRuleRow, _processedRuleColumn, _processedRuleValues.toArray(new INotationElement[_processedRuleValues.size()]));
        _processedRuleName = "";
        _processedRuleValues.clear();
        _processedRules.add(rule);
        _top = 0;
        _isRuleComment = true;
        _isRuleValueComment = false;
        _elementRow = -1;
        _elementColumn = -1;
        _spacedElementRow = -1;
        _spacedElementColumn = -1;
    }

    @Override
    public void pushComment() throws NotationParseException {
        String comment = getTrimmedValue();
        comment = comment.substring(2).trim();
        if (_isRuleComment) {
            _processedRules.add(new Comment(comment, _elementRow, _elementColumn));
        }
        if (_isRuleValueComment) {
            _processedRuleValues.add(new Comment(comment, _elementRow, _elementColumn));
        }
        _top = 0;
        _elementRow = -1;
        _elementColumn = -1;
        _spacedElementRow = -1;
        _spacedElementColumn = -1;
    }

    private String getTrimmedValue() {
        return new String(_buffer, 0, _top).trim();
    }

    RuleSet getRuleSet() {
        return new RuleSet(_processedRules.toArray(new INotationElement[_processedRules.size()]));
    }

}
