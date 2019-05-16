package tree.vo;

/**
 * Created with 凌神.
 * Description:
 * User: 87179
 * Date: 2019-04-27
 * Time: 20:56
 */
public class TreeCategoryVo {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    public TreeCategoryVo(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
