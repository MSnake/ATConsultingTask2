// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.table;

import ru.atc.parser.lr.model.LRRuleItem;

public final class LRTableItem {

    private int _number;

    private String _type;

    private LRRuleItem _reduceRule;

    LRTableItem() {
        _number = -1;
        _type = null;
        _reduceRule = null;
    }

    public int getNubmer() {
        return _number;
    }

    void setNumber(final int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be 0 or greater");
        }
        _number = number;
    }

    public boolean isShiftAction() {
        if (_type == null) {
            throw new IllegalStateException("Action not defined");
        }
        return "S".equals(_type);
    }

    void setShiftAction() {
        _type = "S";
    }

    public boolean isReduceAction() {
        if (_type == null) {
            throw new IllegalStateException("Action not defined");
        }
        return "R".equals(_type);
    }

    void setReduceAction() {
        _type = "R";
    }

    public boolean isAcceptAction() {
        if (_type == null) {
            throw new IllegalStateException("Action not defined");
        }
        return "A".equals(_type);
    }

    void setAcceptAction() {
        _type = "A";
    }

    public LRRuleItem getReduceRule() {
        if (isReduceAction()) {
            return _reduceRule;
        }
        throw new IllegalStateException("Not Reduce item");
    }

    void setReduceRule(final LRRuleItem reduceRule) {
        if (isReduceAction()) {
            _reduceRule = reduceRule;
        }
    }

    @Override
    public String toString() {
        String result = "ti(";
        result += _number + ",";
        result += _type;
        if (_type != null && isReduceAction()) {
            result += "," + _reduceRule;
        }
        result += ")";
        return result;
    }

}
