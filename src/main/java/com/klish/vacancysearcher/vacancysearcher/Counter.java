package com.klish.vacancysearcher.vacancysearcher;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Counter<String> {
    final Map<String, Integer> counts = new HashMap<>();

    public void add(String t) {
        counts.merge(t, 1, Integer::sum);
    }

    public java.lang.String mostCommon(int count) {
        Map<String, Integer> sorted = sort();
        List<String> keys = new ArrayList<String>(sorted.keySet());
        List<Integer> values = new ArrayList<Integer>(sorted.values());
        if (count < counts.size()) {
            keys = keys.subList(0, count);
            values = values.subList(0, count);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            java.lang.String element = i+1 + ". " + keys.get(i) + " (" + values.get(i) + ")\n";
            sb.append(element);
        }
        return sb.toString();
    }

    public List<String> all() {
        Map<String, Integer> sorted = sort();

        return new ArrayList<String>(sorted.keySet());
    }

    public Map<String, Integer> sort() {
        Map<String, Integer> sorted = counts
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return sorted;
    }
}
