package ru.skillbench.tasks.text;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class WordCounterImpl implements WordCounter {

    private String text = null;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public Map<String, Long> getWordCounts() {
        Map<String, Long> map = new HashMap<String, Long>();
        if (text == null) {
            throw new IllegalStateException();
        }
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (word.equals("")) {
                continue;
            }
            if (word.charAt(0) == '<' && word.charAt(word.length() - 1) == '>') {
                continue;
            }
            word = word.toLowerCase();
            Long amount = map.get(word);
            map.put(word, amount == null ? 1 : amount + 1);
        }
        return map;
    }

    private static Comparator<Map.Entry<String, Long>> Comp = new Comparator<Map.Entry<String, Long>>() {
        public int compare(Map.Entry<String, Long> first, Map.Entry<String, Long> second) {
            return -Long.compare(first.getValue(), second.getValue());
        }
    };

    public List<Map.Entry<String, Long>> getWordCountsSorted() {
        Map<String, Long> map = getWordCounts();
        return sort(map, Comp);
    }

    public <K extends Comparable<K>, V extends Comparable<V>> List<Map.Entry<K, V>> sort(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>> (map.entrySet());
        list.sort(comparator);
        return list;
    }

    public <K, V> void print(List<Map.Entry<K, V>> entries, PrintStream ps) {
        for (Map.Entry<K, V> entry : entries) {
            String line = entry.getKey().toString() + ' ' + entry.getValue().toString();
            ps.println(line);
        }
    }




}
