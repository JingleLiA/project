package tree.vo;

import tree.model.TreeMeasurementDots;
import tree.util.DataTrans;

import java.util.Date;

public class TreeMeasurementVO {

    private Integer id;

    private String time;

    private String pic;

    private String treeCode;

    private Double diameterCalculate;

    private Double diameterMeasure;

    private Double diameterCorrect;

    private TreeMeasurementDots dotsCalculate;

    private TreeMeasurementDots dotsCorrect;

    private String type;

    private String categoryName;

    private String userCode;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = DataTrans.dateTimeToString(time);
    }

    public Double getDiameterCalculate() {
        return this.diameterCalculate;
    }

    public void setDiameterCalculate(Double diameterCalculate) {
        this.diameterCalculate = diameterCalculate;
    }

    public Double getDiameterMeasure() {
        return this.diameterMeasure;
    }

    public void setDiameterMeasure(Double diameterMeasure) {
        this.diameterMeasure = diameterMeasure;
    }

//    public TreeMeasurementDots getDiameterCorrect() {
//        return this.diameterCorrect;
//    }
//
//    public void setDiameterCorrect(TreeMeasurementDots diameterCorrect) {
//        this.diameterCorrect = diameterCorrect;
//    }

    public TreeMeasurementDots getDotsCalculate() {
        return this.dotsCalculate;
    }

    public void setDotsCalculate(TreeMeasurementDots dotsCalculate) {
        this.dotsCalculate = dotsCalculate;
    }

    public TreeMeasurementDots getDotsCorrect() {
        return this.dotsCorrect;
    }

    public void setDotsCorrect(TreeMeasurementDots dotsCorrect) {
        this.dotsCorrect = dotsCorrect;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = "/uploads/"+pic;
    }

    public String getTreeCode() {
        return this.treeCode;
    }

    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode;
    }

    public Double getDiameterCorrect() {
        return this.diameterCorrect;
    }

    public void setDiameterCorrect(Double diameterCorrect) {
        this.diameterCorrect = diameterCorrect;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getType(){
        if(this.diameterMeasure != null){
            return "手动";
        }else if(this.diameterCorrect != null){
            return "半自动";
        }else{
            return "自动";
        }
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
