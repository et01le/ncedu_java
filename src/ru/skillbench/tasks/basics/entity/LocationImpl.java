package ru.skillbench.tasks.basics.entity;

public class LocationImpl implements Location {

    String name;
    Type type;
    Location parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setParent(Location parent) {
        this.parent = parent;
    }

    public String getParentName() {
        return parent == null ? "--" : parent.getName();
    }

    public Location getTopLocation() {
        return parent == null ? this : parent.getTopLocation();
    }

    public boolean isCorrect() {
        if (parent == null)
            return true;
        if (!parent.isCorrect())
            return false;
        return type.compareTo(parent.getType()) > 0;
    }

    public String getAddress() {
        String address = parent == null ? "" : ", " + parent.getAddress();
        int firstSpace = name.indexOf(' ');
        if (name.substring(0, firstSpace).contains(".") || name.charAt(name.length() - 1) == '.') {
            address = name + address;
        } else {
            address = type.getNameForAddress() + name + address;
        }
        return address;
    }

    public String toString() {
        return name + " (" + type.toString() + ")";
    }

}



















