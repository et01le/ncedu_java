package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

public class ComplexNumberImpl implements ComplexNumber{
    private double re = 0;
    private double im = 0;

    public ComplexNumberImpl() {}

    public ComplexNumberImpl(double re, double im) {
        set(re, im);
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public boolean isReal() {
        return im == 0;
    }

    public void set(double re, double im) {
        setRe(re);
        setIm(im);
    }

    public void set(String value) throws NumberFormatException {
        String floatRegex = "\\d+(\\.\\d+)?";
        String complexRegex = "\\-?((" + floatRegex + "[\\+\\-]" + ")?" + floatRegex + "i|" + floatRegex + ")";
        value = value.replaceAll("\\s", "");
        if (!value.matches(complexRegex)) {
            throw new NumberFormatException();
        }
        int signPos = Math.max(value.lastIndexOf('+'), value.lastIndexOf('-'));
        if (signPos <= 0) { // which means we have single number
            if (value.charAt(value.length() - 1) != 'i') {
                re = Double.parseDouble(value);
                im = 0;
            } else {
                re = 0;
                im = Double.parseDouble(value.substring(0, value.length() - 1));
            }
        } else {
            re = Double.parseDouble(value.substring(0, signPos));
            im = Double.parseDouble(value.substring(signPos, value.length() - 1));
        }
    }

    public boolean equals(Object other) {
        if (!(other instanceof ComplexNumber))
            return false;
        ComplexNumber casted = (ComplexNumber) other;
        return re == casted.getRe() && im == casted.getIm();
    }

    public String toString() {
        if (re == 0 && im == 0) {
            return "0.0";
        }
        if (re == 0) {
            return Double.toString(im) + 'i';
        }
        if (im == 0) {
            return Double.toString(re);
        }
        return Double.toString(re) + (im < 0 ? "" : '+') + Double.toString(im) + 'i';
    }

    public ComplexNumber copy() {
        return new ComplexNumberImpl(re, im);
    }

    public ComplexNumber clone() throws CloneNotSupportedException {
        return copy();
    }

    public int compareTo(ComplexNumber other) {
        double absSqrThis = re * re + im * im;
        double reOther = other.getRe();
        double imOther = other.getIm();
        double absSqrOther = reOther * reOther + imOther * imOther;
        return Double.compare(absSqrThis, absSqrOther);
    }

    public void sort(ComplexNumber[] array) {
        Arrays.sort(array);
    }

    public ComplexNumber negate() {
        re = -re;
        im = -im;
        return this;
    }

    public ComplexNumber add(ComplexNumber arg2) {
        re += arg2.getRe();
        im += arg2.getRe();
        return this;
    }

    public ComplexNumber multiply(ComplexNumber arg2) {
        double otherRe = arg2.getRe();
        double otherIm = arg2.getIm();
        double thisRe = re;
        double thisIm = im;
        re = thisRe * otherRe - thisIm * otherIm;
        im = thisRe * otherIm + otherRe * thisIm;
        return this;
    }
}
