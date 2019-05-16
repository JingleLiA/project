package tree.model;

import tree.util.DataTrans;

import java.util.Date;

public class TreeMeasurement {
    private Integer id;

    private Date time;

    private Double diameterCalculate;

    private Double diameterMeasure;

    private Integer diameterCorrect;

    private Integer dotsCalculate;

    private Integer dotsCorrect;

    private String userCode;

    private Integer timesNoDots;

    private Integer categoryId;

    private Integer timesNoTreeCode;

    public Integer getTimesNoTreeCode() {
        return this.timesNoTreeCode;
    }

    public void setTimesNoTreeCode(Integer timesNoTreeCode) {
        this.timesNoTreeCode = timesNoTreeCode;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getTimesNoDots() {
        return this.timesNoDots;
    }

    public void setTimesNoDots(Integer timesNoDots) {
        this.timesNoDots = timesNoDots;
    }

    public Integer getTimesNoTreecode() {
        return this.timesNoTreecode;
    }

    public void setTimesNoTreecode(Integer timesNoTreecode) {
        this.timesNoTreecode = timesNoTreecode;
    }

    private Integer timesNoTreecode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTime(String time) {
        this.time = DataTrans.stringToDateTime(time);
    }

    public Double getDiameterCalculate() {
        return diameterCalculate;
    }

    public void setDiameterCalculate(Double diameterCalculate) {
        this.diameterCalculate = diameterCalculate;
    }

    public Double getDiameterMeasure() {
        return diameterMeasure;
    }

    public void setDiameterMeasure(Double diameterMeasure) {
        this.diameterMeasure = diameterMeasure;
    }

    public Integer getDiameterCorrect() {
        return diameterCorrect;
    }

    public void setDiameterCorrect(Integer diameterCorrect) {
        this.diameterCorrect = diameterCorrect;
    }

    public Integer getDotsCalculate() {
        return dotsCalculate;
    }

    public void setDotsCalculate(Integer dotsCalculate) {
        this.dotsCalculate = dotsCalculate;
    }

    public Integer getDotsCorrect() {
        return dotsCorrect;
    }

    public void setDotsCorrect(Integer dotsCorrect) {
        this.dotsCorrect = dotsCorrect;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}