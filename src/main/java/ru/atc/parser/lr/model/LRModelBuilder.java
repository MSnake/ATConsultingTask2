// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.lr.model;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.NullArgumentException;

import ru.atc.parser.notation.element.RuleValue;
import ru.atc.parser.notation.element.RuleValuePart;
import ru.atc.parser.notation.ReservedRuleNames;
import ru.atc.parser.notation.element.Rule;
import ru.atc.parser.notation.element.RuleSet;

public final class LRModelBuilder {

    private LRModelBuilder() {
        super();
    }

    public static LRRuleList createLRModel(final RuleSet rules) {
        if (rules == null) {
            throw new NullArgumentException("rules");
        }
        Rule firstUnreferencedRule = findFirstUnreferencedRule(rules);
        if (firstUnreferencedRule == null) {
            throw new IllegalArgumentException("Rule set must have at least one unreferenced rule");
        }
        List<LRRuleItem> items = new LinkedList<>();
        items.add(createFirstRule(firstUnreferencedRule));

        int ruleNumber = 1;
        for (int i = 0; i < rules.getChildCount(); i++) {
            if (rules.getChildAt(i) instanceof Rule) {
                Rule rule = (Rule) rules.getChildAt(i);
                for (int j = 0; j < rule.getChildCount(); j++) {
                    if (rule.getChildAt(j) instanceof RuleValue) {
                        RuleValue value = (RuleValue) rule.getChildAt(j);
                        items.add(createLRRuleForValue(ruleNumber, rule.getImage(), value));
                        ruleNumber++;
                    }
                }
            }
        }
        return new LRRuleList(items.toArray(new LRRuleItem[items.size()]));
    }

    private static Rule findFirstUnreferencedRule(final RuleSet rules) {
        for (int i = 0; i < rules.getChildCount(); i++) {
            if (!(rules.getChildAt(i) instanceof Rule)) {
                continue;
            }
            Rule rule = (Rule) rules.getChildAt(i);
            if (!hasReferences(rules, i)) {
                return rule;
            }
        }
        return null;
    }

    private static boolean hasReferences(final RuleSet rules, final int testRuleIdx) {
        Rule rule = (Rule) rules.getChildAt(testRuleIdx);
        String name = rule.getImage();

        for (int i = 0; i < rules.getChildCount(); i++) {
            if (testRuleIdx == i) {
                continue;
            }
            if (!(rules.getChildAt(i) instanceof Rule)) {
                continue;
            }

            Rule testRule = (Rule) rules.getChildAt(i);
            for (int j = 0; j < testRule.getChildCount(); j++) {
                if (!(testRule.getChildAt(j) instanceof RuleValue)) {
                    continue;
                }
                RuleValue ruleValue = (RuleValue) testRule.getChildAt(j);
                for (int k = 0; k < ruleValue.getChildCount(); k++) {
                    if (!(ruleValue.getChildAt(k) instanceof RuleValuePart)) {
                        continue;
                    }
                    RuleValuePart ruleValuePart = (RuleValuePart) ruleValue.getChildAt(k);
                    if (!ruleValuePart.isTerminal() && ruleValuePart.getImage().equals(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static LRRuleItem createFirstRule(final Rule firstRule) {
        LRRuleValueItem value = new LRRuleValueItem(firstRule.getImage(), false);
        return new LRRuleItem(0, ReservedRuleNames.MAIN_RULE_NAME, value);
    }

    private static LRRuleItem createLRRuleForValue(final int ruleNumber, final String ruleName, final RuleValue value) {
        List<LRRuleValueItem> values = new LinkedList<>();
        for (int i = 0; i < value.getChildCount(); i++) {
            if (value.getChildAt(i) instanceof RuleValuePart) {
                RuleValuePart part = (RuleValuePart) value.getChildAt(i);
                if (part.isTerminal()) {
                    String terminalStr = part.getImage();
                    for (int k = 0; k < terminalStr.length(); k++) {
                        values.add(new LRRuleValueItem(String.valueOf(terminalStr.charAt(k)), true));
                    }
                } else {
                    values.add(new LRRuleValueItem(part.getImage(), false));
                }
            }
        }
        return new LRRuleItem(ruleNumber, ruleName, values.toArray(new LRRuleValueItem[values.size()]));
    }

}
