package com.diefesson.difcomp.grammar;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FirstSets {

    private final Map<Element, Set<Element>> firstSets;

    private FirstSets(Map<Element, Set<Element>> firstSets) {
        this.firstSets = firstSets;
    }

    public Set<Element> keys() {
        return Collections.unmodifiableSet(firstSets.keySet());
    }

    public Set<Element> get(Element key) {
        return Collections.unmodifiableSet(firstSets.get(key));
    }

    public static FirstSets calculateFirstSets(Grammar grammar) {
        List<Rule> rules = grammar.rules();
        Map<Element, Set<Element>> firstSets = new HashMap<>();
        rules.stream()
                .map((r) -> r.left)
                .distinct()
                .forEach((v) -> firstSets.put(v, new HashSet<>()));
        boolean updated;
        do {
            updated = false;
            for (Rule rule : rules) {
                updated |= compute(firstSets, rule);
            }
        } while (updated);
        return new FirstSets(firstSets);
    }

    private static boolean compute(Map<Element, Set<Element>> firstSets, Rule rule) {
        boolean updated = false;
        Element left = rule.left;
        List<Element> right = rule.right();
        Set<Element> firstSet = firstSets.get(left);
        for (int i = 0; i < right.size(); i++) {
            Element item = right.get(i);
            if (item.type == ElementType.VARIABLE) {
                Set<Element> firsts = new HashSet<>(firstSets.get(item));
                Boolean containsEmpty = firsts.remove(Element.empty());
                updated |= firstSet.addAll(firsts);
                if (!containsEmpty) {
                    break;
                }
            } else {
                updated |= firstSet.add(item);
                break;
            }
            if (i == right.size() - 1) {
                updated |= firstSet.add(Element.empty());
            }
        }
        return updated;
    }

}