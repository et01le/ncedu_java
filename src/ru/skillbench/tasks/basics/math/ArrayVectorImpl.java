package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

public class ArrayVectorImpl implements ArrayVector{

    private double[] array = {0};

    public ArrayVectorImpl() {}

    public ArrayVectorImpl(double... elements) {
        set(elements);
    }

    public void set (double... elements) {
        array = Arrays.copyOf(elements, elements.length);
    }

    public double[] get() {
        return this.array;
    }

    public ArrayVector clone() {
        return new ArrayVectorImpl(get());
    }

    public int getSize() {
        return array.length;
    }

    public void set(int index, double value) {
        if (index < 0) {
            return;
        }
        if (index >= array.length) {
            array = Arrays.copyOf(array, index + 1);
        }
        array[index] = value;
    }

    public double get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= array.length) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            return array[index];
        }
    }

    public double getMax() {
        double max = array[0];
        for (int i = 0; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }
        return max;
    }

    public double getMin() {
        double min = array[0];
        for (int i = 0; i < array.length; i++) {
            min = Math.min(min, array[i]);
        }
        return min;
    }

    public void sortAscending() {
        Arrays.sort(array);
    }

    public void mult(double factor) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= factor;
        }
    }

    public ArrayVector sum(ArrayVector anotherVector) {
        int minLength = Math.min(array.length, anotherVector.getSize());
        for (int i = 0; i < minLength; i++) {
            array[i] += anotherVector.get(i);
        }
        return this;
    }

    public double scalarMult(ArrayVector anotherVector) {
        double sum = 0;
        int minLength = Math.min(array.length, anotherVector.getSize());
        for (int i = 0; i < minLength; i++) {
            sum += array[i] * anotherVector.get(i);
        }
        return sum;
    }

    public double getNorm() {
        return Math.sqrt(scalarMult(this));
    }
}
