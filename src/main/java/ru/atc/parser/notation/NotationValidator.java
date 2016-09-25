// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation;

import java.util.HashMap;
import java.util.Map;

import ru.atc.parser.notation.element.RuleValue;
import ru.atc.parser.notation.element.RuleValuePart;
import ru.atc.parser.notation.element.Rule;
import ru.atc.parser.notation.element.RuleSet;

final class NotationValidator {

    private NotationValidator() throws NotationParseException {
        super();
    }

    static void validate(final RuleSet rules) throws NotationParseException {
        Map<String, Rule> rulesDefinitions = validateRuleNames(rules);
        checkValueLinks(rules, rulesDefinitions);
        validateRuleValues(rules);
    }

    private static Map<String, Rule> validateRuleNames(final RuleSet rules) throws NotationParseException {
        Map<String, Rule> rulesDefinition = new HashMap<>();
        for (int i = 0; i < rules.getChildCount(); i++) {
            if (rules.getChildAt(i) instanceof Rule) {
                Rule rule = (Rule) rules.getChildAt(i);
                if (rulesDefinition.put(rule.getImage(), rule) != null) {
                    throw new NotationParseException("Rule " + rule.getImage() + " defined second time at " + rule.getRow() + ":" + rule.getColumn());
                }
                if (ReservedRuleNames.getReserverRuleNames().contains(rule.getImage())) {
                    throw new NotationParseException("Rule name " + rule.getImage() + " at " + rule.getRow() + ":" + rule.getColumn() + " is reserved");
                }
            }
        }
        return rulesDefinition;
    }

    private static void checkValueLinks(final RuleSet rules, final Map<String, Rule> rulesDefinitions) throws NotationParseException {
        for (int i = 0; i < rules.getChildCount(); i++) {
            if (rules.getChildAt(i) instanceof Rule) {
                Rule rule = (Rule) rules.getChildAt(i);
                for (int j = 0; j < rule.getChildCount(); j++) {
                    if (rule.getChildAt(j) instanceof RuleValue) {
                        RuleValue ruleValue = (RuleValue) rule.getChildAt(j);
                        for (int k = 0; k < ruleValue.getChildCount(); k++) {
                            if (ruleValue.getChildAt(k) instanceof RuleValuePart) {
                                RuleValuePart ruleValuePart = (RuleValuePart) ruleValue.getChildAt(k);
                                if (!ruleValuePart.isTerminal() && !rulesDefinitions.containsKey(ruleValuePart.getImage())) {
                                    throw new NotationParseException("Rule value part at " + ruleValuePart.getRow() + ":" + ruleValuePart.getColumn() + " references to unknown rule " + ruleValuePart.getImage());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void validateRuleValues(final RuleSet rules) throws NotationParseException {
        Map<RuleValue, Rule> ruleSubstitutions = new HashMap<>();
        for (int i = 0; i < rules.getChildCount(); i++) {
            if (rules.getChildAt(i) instanceof Rule) {
                Rule rule = (Rule) rules.getChildAt(i);
                for (int j = 0; j < rule.getChildCount(); j++) {
                    if (rule.getChildAt(j) instanceof RuleValue) {
                        RuleValue ruleValue = (RuleValue) rule.getChildAt(j);
                        if (ruleSubstitutions.put(ruleValue, rule) != null) {
                            throw new NotationParseException("Rule value defined second time at " + ruleValue.getRow() + ":" + ruleValue.getColumn());
                        }
                    }
                }
            }
        }
    }

}
