package tree.service;

import tree.model.TreeUser;
import tree.dao.TreeUserMapper;
    /**
 * Created with 凌神.
 * Description: 
 * User: 87179
 * Date: 2019-04-24
 * Time: 22:09
 */
public interface TreeUserService{


    int deleteByPrimaryKey(Integer id);

    int insert(TreeUser record);

    int insertSelective(TreeUser record);

    TreeUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TreeUser record);

    int updateByPrimaryKey(TreeUser record);

    TreeUser findForLogin(TreeUser record);

}
