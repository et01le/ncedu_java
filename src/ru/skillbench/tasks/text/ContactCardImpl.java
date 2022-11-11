package ru.skillbench.tasks.text;

import java.time.Period;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class ContactCardImpl implements ContactCard{

    private String fullName = null;
    private String organization = null;
    private Gender gender = Gender.UNDEFINED;
    private Calendar birthday = null;
    private HashMap<String, Long> telephones = new HashMap<String, Long>();

    private enum Prefix {
        FN, ORG, GENDER, BDAY, TEL;
        private Prefix() {}
    }

    private enum Gender {
        UNDEFINED, MALE, FEMALE;
        private Gender() {}
    }

    private ContactCardImpl(
            String fullName, String organization, Gender gender, Calendar birthday, HashMap<String, Long> telephones
    ) {
        this.fullName = fullName;
        this.organization = organization;
        this.gender = gender;
        this.birthday = birthday;
        this.telephones = telephones;
    }

    private static String strip (String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            return "";
        }
        int startIndex = 0;
        int endIndex = str.length() - 1;
        while (str.substring(startIndex, startIndex + 1).matches("\\s")) {
            startIndex++;
            if (startIndex > str.length() - 1) {
                return "";
            }
        }
        while (str.substring(endIndex, endIndex + 1).matches("\\s")) {
            endIndex--;
        }
        return str.substring(startIndex, endIndex + 1);
    }

    public ContactCardImpl() {}

    public ContactCard getInstance(Scanner scanner) {

        String fullName = null;
        String organization = null;
        Gender gender = Gender.UNDEFINED;
        Calendar birthday = null;
        HashMap<String, Long> telephones = new HashMap<String, Long>();

        Prefix[] prefixesRaw = Prefix.values();
        String[] prefixes = new String[prefixesRaw.length];
        for (int i = 0; i < prefixes.length; i++) {
            prefixes[i] = prefixesRaw[i].toString();
        }

        if (scanner.hasNextLine()) {
            String next = strip(scanner.nextLine()); // Had to implement my own strip
            if (!next.matches("BEGIN:VCARD")) {
                throw new InputMismatchException();
            }
        }
        //System.out.println("Sequence start detected");

        while (scanner.hasNextLine()) {
            String next = strip(scanner.nextLine()); // Had to implement my own strip
            boolean prefixFound = false;
            int prefixNumber = -1;
            int prefixLen = 0;
            for (int i = 0; i < prefixes.length; i++) {
                if (next.startsWith(prefixes[i])) {
                    prefixFound = true;
                    prefixNumber = i;
                    prefixLen = prefixes[i].length();
                }
            }
            if (!prefixFound) {
                if (!next.matches("END:VCARD")) {
                    throw new InputMismatchException();
                } else {
                    //System.out.println("Sequence end detected");
                    break;
                }
            }
            if (prefixesRaw[prefixNumber] != Prefix.TEL && next.charAt(prefixLen) != ':') {
                throw new InputMismatchException();
            }
            switch (prefixesRaw[prefixNumber]) {
                case FN:
                    if (fullName == null) {
                        fullName = next.substring(prefixLen + 1);
                        if (fullName.length() == 0) {
                            throw new InputMismatchException();
                        }
                    } else {
                        throw new InputMismatchException();
                    }
                    break;
                case ORG:
                    if (organization == null) {
                        organization = next.substring(prefixLen + 1);
                        if (organization.length() == 0) {
                            throw new InputMismatchException();
                        }
                    } else {
                        throw new InputMismatchException();
                    }
                    break;
                case GENDER:
                    if (gender == Gender.UNDEFINED) {
                        String value = next.substring(prefixLen + 1);
                        if (value.matches("M")) {
                            gender = Gender.MALE;
                        } else if (value.matches("F")) {
                            gender = Gender.FEMALE;
                        } else {
                            throw new InputMismatchException();
                        }
                    }
                    break;
                case BDAY:
                    if (birthday == null) {
                        String[] values = next.substring(prefixLen + 1).split("\\-");
                        if (values.length != 3) {
                            throw new InputMismatchException();
                        }
                        boolean validFormat = values[0].matches("\\d{2}");
                        validFormat &= values[1].matches("\\d{2}");
                        validFormat &= values[2].matches("\\d{4}");
                        if (!validFormat) {
                            throw new InputMismatchException();
                        }
                        int day = Integer.parseInt(values[0]);
                        int month = Integer.parseInt(values[1]);
                        int year = Integer.parseInt(values[2]);
                        birthday = new GregorianCalendar(year, month - 1, day); // TODO: invalid arguments possible
                    } else {
                        throw new InputMismatchException();
                    }
                    break;
                case TEL: // TODO: should have thought about type's format too
                    String typeStr = ";TYPE=";
                    int columnPos = next.indexOf(':');
                    if (columnPos == -1 || !next.substring(prefixLen).startsWith(typeStr)) {
                        throw new InputMismatchException();
                    }
                    String type = next.substring(prefixLen + typeStr.length(), columnPos);
                    if (type.length() == 0 || telephones.containsKey(type)) {
                        throw new InputMismatchException();
                    }
                    String telStr = next.substring(columnPos + 1);
                    if (!telStr.matches("\\d{10}")) {
                        throw new InputMismatchException();
                    }
                    Long tel = Long.valueOf(telStr);
                    telephones.put(type, tel);
                    break;
            }
        }
        if (fullName == null || organization == null) {
            throw new NoSuchElementException();
        }
        return new ContactCardImpl(fullName, organization, gender, birthday, telephones);
    }

    public ContactCard getInstance(String data) {
        Scanner scanner = new Scanner(data);
        return getInstance(scanner);
    }

    public String getFullName() {
        return fullName;
    }

    public String getOrganization() {
        return organization;
    }

    public boolean isWoman() {
        return gender == Gender.FEMALE;
    }

    public Calendar getBirthday() {
        if (birthday == null) {
            throw new NoSuchElementException();
        }
        return birthday;
    }

    public Period getAge() {
        if (birthday == null) {
            throw new NoSuchElementException();
        }
        Calendar age = new GregorianCalendar();
        age.add(Calendar.DATE, -birthday.get(Calendar.DATE));
        age.add(Calendar.MONTH, -birthday.get(Calendar.MONTH));
        age.add(Calendar.YEAR, -birthday.get(Calendar.YEAR));
        return Period.of(age.get(Calendar.YEAR), age.get(Calendar.MONTH), age.get(Calendar.DATE));
    }

    public int getAgeYears() {
        if (birthday == null) {
            throw new NoSuchElementException();
        }
        Calendar age = new GregorianCalendar();
        age.add(Calendar.DATE, -birthday.get(Calendar.DATE));
        age.add(Calendar.MONTH, -birthday.get(Calendar.MONTH));
        age.add(Calendar.YEAR, -birthday.get(Calendar.YEAR));
        return age.get(Calendar.YEAR);
    }

    public String getPhone(String type) {
        Long tel = telephones.get(type);
        if (tel == null) {
            throw new NoSuchElementException();
        }
        String str =  Long.toString(tel);
        return "(" + str.substring(0, 3) + ") " + str.substring(3, 6) + "-" + str.substring(6);
    }
}










