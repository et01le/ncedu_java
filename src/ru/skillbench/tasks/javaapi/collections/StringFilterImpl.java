package ru.skillbench.tasks.javaapi.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Iterator;

public class StringFilterImpl implements StringFilter{

    private HashSet<String> data = new HashSet<String>();

    public void add(String s) {
        data.add(s == null ? null : s.toLowerCase());
    }

    public boolean remove(String s) {
        return data.remove(s == null ? null : s.toLowerCase());
    }

    public void removeAll() {
        data.clear();
    }

    public Collection<String> getCollection() {
        return data;
    }

    public Iterator<String> getStringsContaining(String chars) {
        HashSet<String> filtered = (HashSet<String>) data.clone();
        Iterator<String> iterator = filtered.iterator();
        if (chars == null || chars.equals("")) {
            return iterator;
        }
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (str == null || str.equals("")) {
                iterator.remove();
                continue;
            }
            if (!str.contains(chars)) {
                iterator.remove();
            }
        }
        return filtered.iterator();
    }

    public Iterator<String> getStringsStartingWith(String begin) {
        HashSet<String> filtered = (HashSet<String>) data.clone();
        Iterator<String> iterator = filtered.iterator();
        if (begin == null || begin.equals("")) {
            return iterator;
        }
        begin = begin.toLowerCase();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (str == null || str.equals("")) {
                iterator.remove();
                continue;
            }
            if (!str.startsWith(begin)) {
                iterator.remove();
            }
        }
        return filtered.iterator();
    }

    public Iterator<String> getStringsByNumberFormat(String format) {
        HashSet<String> filtered = (HashSet<String>) data.clone();
        Iterator<String> iterator = filtered.iterator();
        if (format == null || format.equals("")) {
            return iterator;
        }
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (str == null || str.equals("") || format.length() != str.length()) {
                iterator.remove();
                continue;
            }
            for (int i = 0; i < str.length(); i++) {
                if (format.charAt(i) == '#') {
                    if (!str.substring(i, i + 1).matches("\\d")) {
                        iterator.remove();
                        break;
                    }
                } else {
                    if (str.charAt(i) != format.charAt(i)) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
        return filtered.iterator();
    }

    public Iterator<String> getStringsByPattern(String pattern) {
        HashSet<String> filtered = (HashSet<String>) data.clone();
        Iterator<String> iterator = filtered.iterator();
        if (pattern == null || pattern.equals("")) {
            return iterator;
        }
        pattern = pattern.replaceAll("\\*", ".*");
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (str == null || str.equals("")) {
                iterator.remove();
                continue;
            }
            if (!str.matches(pattern)) {
                iterator.remove();
            }
        }
        return filtered.iterator();
    }
}
