package tree.dao;

import tree.model.TreeUser;

/**
 * Created with 凌神.
 * Description: 
 * User: 87179
 * Date: 2019-04-24
 * Time: 22:09
 */
public interface TreeUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TreeUser record);

    int insertSelective(TreeUser record);

    TreeUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TreeUser record);

    int updateByPrimaryKey(TreeUser record);

    TreeUser selectOne(TreeUser record);
}