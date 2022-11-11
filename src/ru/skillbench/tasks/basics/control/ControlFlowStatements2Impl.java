package ru.skillbench.tasks.basics.control;

public class ControlFlowStatements2Impl implements ControlFlowStatements2{

    public int getFunctionValue(int x) {
        if (x > 2 || x < -2) {
            return 2 * x;
        } else {
            return -3 * x;
        }
    }

    public String decodeMark(int mark) {
        switch (mark) {
            case 1: return "Fail";
            case 2: return "Poor";
            case 3: return "Satisfactory";
            case 4: return "Good";
            case 5: return "Excellent";
            default: return "Error";
        }
    }

    public double[][] initArray() {
        double[][] arr = new double[5][8];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                arr[i][j] = Math.pow(i, 4) - Math.sqrt(j);
            }
        }
        return arr;
    }

    public double getMaxValue(double[][] array) {
        double max = array[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                max = Math.max(max, array[i][j]);
            }
        }
        return max;
    }

    public Sportsman calculateSportsman(float P) {
        float dailyDistance = 10;
        Sportsman sportsman = new Sportsman();
        while (sportsman.getTotalDistance() < 200) {
            sportsman.addDay(dailyDistance);
            dailyDistance *= 1 + P * 0.01;
        }
        return sportsman;
    }
}
