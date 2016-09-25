// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr;

import ru.atc.parser.lr.model.LRRuleItem;
import ru.atc.parser.lr.table.LRInputSymbol;

public interface ILRParserEventHandler {

    void handleShift(LRInputSymbol symbol);

    void handleReduce(LRRuleItem rule, LRInputSymbol... symbols);

    void handleAccept();

}
