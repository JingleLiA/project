package tree.vo;

import tree.model.TreeMeasurementDots;

/**
 * Created by christine on 2018/10/22.
 */
public class MeasurementResult {
    private boolean success;
    private String msg;
    private double diameterCalculate;
    private TreeMeasurementDots dots;

    public double getDiameterCalculate() {
        return diameterCalculate;
    }

    public void setDiameterCalculate(double diameterCalculate) {
        this.diameterCalculate = diameterCalculate;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TreeMeasurementDots getDots() {
        return dots;
    }

    public void setDots(TreeMeasurementDots dots) {
        this.dots = dots;
    }
}
