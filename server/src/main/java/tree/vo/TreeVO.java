package tree.vo;

import tree.util.DataTrans;

public class TreeVO {
    private String treeCode;
    private String treeType;
    private double grow;
    private int measureTimes;

    public String getTreeCode() {
        return this.treeCode;
    }

    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode;
    }

    public String getTreeType() {
        return this.treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public double getGrow() {
        return this.grow;
    }

    public void setGrow(double grow) {
        this.grow = DataTrans.getTwoDigitDouble(grow);
    }

    public int getMeasureTimes() {
        return this.measureTimes;
    }

    public void setMeasureTimes(int measureTimes) {
        this.measureTimes = measureTimes;
    }
}
