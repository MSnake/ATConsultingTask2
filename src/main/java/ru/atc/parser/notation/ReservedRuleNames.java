// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ReservedRuleNames {

    public static final String MAIN_RULE_NAME = "S";

    private static final Set<String> RESERVED_RULE_NAMES;

    static {
        Set<String> set = new HashSet<>();
        set.add(MAIN_RULE_NAME);
        RESERVED_RULE_NAMES = Collections.unmodifiableSet(set);
    }

    private ReservedRuleNames() {
        super();
    }

    public static Set<String> getReserverRuleNames() {
        return RESERVED_RULE_NAMES;
    }

}
