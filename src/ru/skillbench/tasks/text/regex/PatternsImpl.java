package ru.skillbench.tasks.text.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternsImpl implements Patterns{

    public Pattern getSQLIdentifierPattern() {
        return Pattern.compile("[a-zA-Z]\\w{0,29}");
    }

    public Pattern getEmailPattern() {
        String account = "[a-zA-Z0-9]([\\w\\.\\-]{0,20}[a-zA-Z0-9])?";
        String mainDomain = "(ru|com|net|org)";
        String domain = "[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9]";
        return Pattern.compile(account + "@(" + domain +"\\.)+" + mainDomain);
    }

    public Pattern getHrefTagPattern() {
        String href = "<a\\s+href\\s*\\=\\s*(\".*\"|\\S*)\\s*>";
        return Pattern.compile(href, Pattern.CASE_INSENSITIVE);
    }

    public List<String> findAll(String input, Pattern pattern) {
        List<String> words = new ArrayList<String>();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return words;
    }

    public int countMatches(String input, String regex) {
        List<String> words = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }
}
