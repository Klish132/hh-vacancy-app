package com.klish.vacancysearcher.vacancysearcher;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Counter<String> {
    private Map<String, Integer> counts = new HashMap<>();

    public void add(String t) {
        counts.merge(t, 1, Integer::sum);
    }

    public List<String> getKeys() {

        return new ArrayList<String>(this.counts.keySet());
    }
    public List<Integer> getValues() {

        return new ArrayList<Integer>(this.counts.values());
    }

    public void sort() {
        Map<String, Integer> sorted = counts
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        this.counts = sorted;
    }
}
