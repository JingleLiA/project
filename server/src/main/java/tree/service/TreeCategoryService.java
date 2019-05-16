package tree.service;

import tree.dao.TreeCategoryMapper;
import tree.model.TreeCategory;
import tree.vo.TreeCategoryVo;

import java.util.List;

/**
 * Created with 凌神.
 * Description: 
 * User: 87179
 * Date: 2019-04-27
 * Time: 20:03
 */
public interface TreeCategoryService{


    int deleteByPrimaryKey(Integer id);

    int insert(TreeCategory record);

    int insertSelective(TreeCategory record);

    TreeCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TreeCategory record);

    int updateByPrimaryKey(TreeCategory record);

    List<TreeCategoryVo> selectAllInfo();

}
