package tree.model;

public class TreeMeasurementDots {
    private Integer id;

    private Integer upx;

    private Integer upy;

    private Integer downx;

    private Integer downy;

    private Integer leftx;

    private Integer lefty;

    private Integer rightx;

    private Integer righty;

    private Double angleL;

    private Double angleR;

    public TreeMeasurementDots(){
    }

    public TreeMeasurementDots(int leftx,int lefty,int rightx,int righty,double anglel,double angler){
        this.leftx = leftx;
        this.lefty = lefty;
        this.rightx = rightx;
        this.righty = righty;
        this.angleL = anglel;
        this.angleR = angler;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUpx() {
        return upx;
    }

    public void setUpx(Integer upx) {
        this.upx = upx;
    }

    public Integer getUpy() {
        return upy;
    }

    public void setUpy(Integer upy) {
        this.upy = upy;
    }

    public Integer getDownx() {
        return downx;
    }

    public void setDownx(Integer downx) {
        this.downx = downx;
    }

    public Integer getDowny() {
        return downy;
    }

    public void setDowny(Integer downy) {
        this.downy = downy;
    }

    public Integer getLeftx() {
        return leftx;
    }

    public void setLeftx(Integer leftx) {
        this.leftx = leftx;
    }

    public Integer getLefty() {
        return lefty;
    }

    public void setLefty(Integer lefty) {
        this.lefty = lefty;
    }

    public Integer getRightx() {
        return rightx;
    }

    public void setRightx(Integer rightx) {
        this.rightx = rightx;
    }

    public Integer getRighty() {
        return righty;
    }

    public void setRighty(Integer righty) {
        this.righty = righty;
    }

    public Double getAnglel() {
        return angleL;
    }

    public void setAnglel(Double anglel) {
        this.angleL = anglel;
    }

    public Double getAngler() {
        return angleR;
    }

    public void setAngler(Double angler) {
        this.angleR = angler;
    }
}