package tree.vo;

import tree.model.TreeMeasurementDots;

public class CorrectResult {
    private boolean success;

    private double diameter_correct;

    private TreeMeasurementDots dots;

    public TreeMeasurementDots getDots() {
        return this.dots;
    }

    public void setDots(TreeMeasurementDots dots) {
        this.dots = dots;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getDiameter_correct() {
        return this.diameter_correct;
    }

    public void setDiameter_correct(double diameter_correct) {
        this.diameter_correct = diameter_correct;
    }
}
