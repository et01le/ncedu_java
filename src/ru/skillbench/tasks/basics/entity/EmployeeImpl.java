package ru.skillbench.tasks.basics.entity;

public class EmployeeImpl implements Employee {

    String firstName;
    String lastName;
    private int salary = 1000;
    Employee manager;

    public int getSalary() {
        return salary;
    }

    public void increaseSalary(int value) {
        salary += value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getManagerName() {
        if (manager == null)
            return null;
        return manager.getFullName();
    }

    public Employee getTopManager() {
        return manager == null ? this : manager.getTopManager();
    }
}













