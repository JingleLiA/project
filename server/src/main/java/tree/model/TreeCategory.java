package tree.model;

/**
 * Created with 凌神.
 * Description: 
 * User: 87179
 * Date: 2019-04-27
 * Time: 20:03
 */
public class TreeCategory {
    private Integer id;

    /**
    * 类别名称
    */
    private String categoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}